package au.org.ala.userdetails

import au.org.ala.auth.BulkUserLoadResults
import au.org.ala.auth.PasswordResetFailedException

class UserService {

    def emailService

    def updateUser(user, params){
        try {
            user.setProperties(params)
            user.activated = true
            user.locked = false
            user.save(flush:true)
            updateProperties(user, params)
            true
        } catch (Exception e){
            log.error(e.getMessage(), e)
            false
        }
    }

    def isEmailRegistered(email){
        def user = User.findByEmail(email.toLowerCase())
        if(user){
            true
        } else {
            false
        }
    }

    def activateAccount(user){
        //TODO create alerts using a webservice......
        user.activated = true
        user.save(flush:true)
    }

    public BulkUserLoadResults bulkRegisterUsersFromFile(InputStream stream, Boolean firstRowContainsFieldNames, String primaryUsage, String emailSubject, String emailTitle, String emailBody) {

        def results = new BulkUserLoadResults()

        if (!stream) {
            results.message = "No data specified!"
            return results
        }

        int lineNumber = 0
        int expectedColumns = 4

        def roleUser = Role.findByRole("ROLE_USER")

        stream.eachCsvLine { tokens ->
            // email_address,first_name,surname,roles
            if (++lineNumber == 1 && firstRowContainsFieldNames) {
                // ignore...
            } else {

                if (tokens.size() == 1 && !tokens[0]?.trim()?.length()) {
                    return // skip empty lines
                }

                if (tokens.size() != expectedColumns) {
                    results.failedRecords << [lineNumber: lineNumber, tokens: tokens, reason: "Incorrect number of columns - expected ${expectedColumns}, got ${tokens.size()}"]
                    return
                }

                def emailAddress = tokens[0]
                // Check to see if this email address is already in use...
                def userInstance = User.findByEmail(emailAddress)
                def isNewUser = true

                def existingRoles = []
                if (userInstance) {
                    isNewUser = false
                    // keep track of their current roles
                    existingRoles.addAll(UserRole.findAllByUser(userInstance)*.role)
                } else {
                    userInstance = new User(email: emailAddress, userName: emailAddress, firstName: tokens[1], lastName: tokens[2])
                    userInstance.activated = true
                    userInstance.locked = false
                    userInstance.created = new Date().toTimestamp()
                }

                // Now add roles
                def roles = []
                if (!existingRoles.contains(roleUser)) {
                    roles << roleUser
                }
                if (tokens[3]?.trim()) {
                    def roleArray = tokens[3].trim()?.split(" ")
                    for (String roleName : roleArray) {
                        def role = Role.findByRole(roleName)
                        if (!role) {
                            results.failedRecords << [lineNumber: lineNumber, tokens: tokens, reason: "Specified role '${roleName} does not exist"]
                            return
                        } else {
                            if (!roles.contains(role) && !existingRoles.contains(role)) {
                                roles << role
                            }
                        }
                    }
                }

                if (isNewUser) {
                    userInstance.save(failOnError: true)
                    results.userAccountsCreated++
                }

                roles?.each { role ->
                    def userRole = new UserRole(user: userInstance, role: role)
                    userRole.save(failOnError: true)
                }

                if (!isNewUser) {
                    results.warnings << [lineNumber: lineNumber, tokens: tokens, reason: "Email address already exists in database. Added roles ${roles}"]
                } else {
                    // User Properties
                    def userProps = [:]

                    userProps['primaryUserType'] = primaryUsage ?: 'Not specified'
                    userProps['secondaryUserType'] = 'Not specified'
                    userProps['bulkCreatedOn'] = new Date().format("yyyy-MM-dd HH:mm:ss")
                    setUserPropertiesFromMap(userInstance, userProps)

                    // Now send a temporary password to the user...
                    try {
                        resetAndSendTemporaryPassword(userInstance, emailSubject, emailTitle, emailBody)
                    } catch (PasswordResetFailedException ex) {
                        // Catching the checked exception should prevent the transaction from failing
                        log.error("Failed to send temporary password via email!", ex)
                        results.warnings << [lineNumber: lineNumber, tokens: tokens, reason: "Failed to send password reset email. Check mail configuration"]
                    }
                }


            }
        }

        results.success = true

        return results
    }

    private setUserPropertiesFromMap(User user, Map properties) {

        properties.keySet().each { String propName ->
            def propValue = properties[propName] ?: ''
            def existingProperty = UserProperty.findByUserAndProperty(user, propName)
            if (existingProperty) {
                existingProperty.value = propValue
                existingProperty.save()
            } else {
                def newProperty = new UserProperty(user: user, property: propName, value: propValue)
                newProperty.save(failOnError: true)
            }
        }

    }

    def registerUser(params){

        //does a user with the supplied email address exist
        def user = new User(params)
        user.userName = params.email
        user.activated = false
        user.locked = false
        user.created = new Date().toTimestamp()
        user.tempAuthKey = UUID.randomUUID().toString()
        def createdUser = user.save(flush: true)
        updateProperties(createdUser, params)

        //add a role of user
        def roleUser = Role.findByRole("ROLE_USER")
        new UserRole(user:user, role:roleUser).save(flush:true)

        log.info("Newly created user: " + createdUser.id)
        createdUser
    }

    def updateProperties(user, params) {
        (new UserProperty(user: user, property: 'city', value: params.city ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'organisation', value: params.organisation ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'telephone', value: params.telephone ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'state', value: params.state ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'primaryUserType', value: params.primaryUserType ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'secondaryUserType', value: params.secondaryUserType ?: '')).save(flush: true)
    }

    def deleteUser(User user) {

        if (user) {
            // First need to delete any user properties
            def userProperties = UserProperty.findAllByUser(user)
            userProperties.each { userProp ->
                userProp.delete()
            }
            // Then delete any roles
            def userRoles = UserRole.findAllByUser(user)
            userRoles.each { userRole ->
                userRole.delete()
            }

            // Delete password
            def passwords = Password.findAllByUser(user)
            passwords.each { password ->
                password.delete()
            }

            // and finally delete the use object
            user.delete()
        }

    }

    def resetAndSendTemporaryPassword(User user, String emailSubject, String emailTitle, String emailBody) throws PasswordResetFailedException {
        if (user) {
            //set the temp auth key
            user.tempAuthKey = UUID.randomUUID().toString()
            user.save(flush: true)
            //send the email
            emailService.sendPasswordReset(user, user.tempAuthKey, emailSubject, emailTitle, emailBody)
        }
    }
}
