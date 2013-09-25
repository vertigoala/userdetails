package au.org.ala.userdetails

class UserService {

    def serviceMethod() {}

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
}
