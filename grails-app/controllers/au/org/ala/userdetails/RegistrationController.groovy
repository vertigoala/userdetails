package au.org.ala.userdetails

import au.org.ala.cas.encoding.MyPasswordEncoder

/**
 * Controller that handles the interactions with general public.
 * Supports:
 *
 * 1. Account creation
 * 2. Account editing
 * 3. Password reset
 * 4. Account activation
 */
class RegistrationController {

    def simpleCaptchaService
    def emailService
    def authService

    def index() {
        redirect(action:'createAccount')
    }

    def createAccount = {}

    def editAccount = {
        def userId = authService.getUserId()
        def user = User.get(userId)
        user.getUserProperties()
        render(view:'createAccount', model: [edit:true, user:user, props:user.propsAsMap()])
    }

    def passwordReset = {
        User user = User.get(params.userId?.toLong())
        if(user.tempAuthKey != params.authKey){
            render(view:'accountError')
        } else {
            render(view:'passwordReset', model:[user:user, authKey:params.authKey])
        }
    }

    def updatePassword = {
        User user = User.get(params.userId.toLong())
        if(params.password == params.reenteredPassword){
           //check the authKey for the user
           if(user.tempAuthKey == params.authKey){
               //update the password
               //store the password
               def password = new Password()
               password.user = user
               def encoder = new MyPasswordEncoder()
               encoder.setAlgorithm("MD5")
               encoder.setSalt("nXg798dr60987Hgb")
               encoder.setBase64Encoding(true)
               password.password = encoder.encode(params.password)
               password.save(flush:true)
               redirect(controller: 'registration', action:'passwordResetSuccess')
           } else {
               render(view:'accountError')
           }
        } else {
           //back to the original form
           render(view:'passwordReset', model: [user:user, authKey:params.authKey, passwordMatchFail:true])
        }
    }

    def passwordResetSuccess(){
        [serverUrl:grailsApplication.config.security.cas.appServerName + grailsApplication.config.security.cas.contextPath + '/myprofile']
    }

    def startPasswordReset = {
        //check for human
        boolean captchaValid = simpleCaptchaService.validateCaptcha(params.captcha)
        if(!captchaValid){
            //send password reset link
            render(view:'forgottenPassword', model:[email: params.email, captchaInvalid: true])
        } else {

            def user = User.findByEmail(params.email)
            if(user){
                //set the temp auth key
                user.tempAuthKey = UUID.randomUUID().toString()
                user.save(flush: true)
                //send the email
                emailService.sendPasswordReset(user, user.tempAuthKey)
            } else {
                //send password reset link
                render(view:'forgottenPassword', model:[email: params.email, captchaInvalid: false, invalidEmail:true])
            }
        }
    }

    def update = {
        def userId = authService.getUserId()
        def user = User.get(userId)
        user.setProperties(params)
        user.userName = params.email
        user.activated = true
        user.locked = false
        user.save(flush:true)
        updateProperties(user, params)
        redirect(controller: 'profile')
    }

    def register = {
        //create user account...
        if(isEmailRegistered(params.email)){
            render(view:'createAccount', model:[edit:false, user:params, props:params, alreadyRegistered: true])
        } else {

            try {
                //does a user with the supplied email address exist
                def user = new User(params)
                user.userName = params.email
                user.activated = false
                user.locked = false
                user.created = new Date().toTimestamp()
                user.tempAuthKey = UUID.randomUUID().toString()
                def createdUser = user.save(flush: true)
                updateProperties(createdUser, params)
                println("Newly created user: " + createdUser.id)

                //store the password
                def password = new Password()
                password.user = user
                def encoder = new MyPasswordEncoder()
                encoder.setAlgorithm("MD5")
                encoder.setSalt("nXg798dr60987Hgb")
                encoder.setBase64Encoding(true)
                password.password = encoder.encode(params.password)
                password.save(flush:true)

                //store the password
                emailService.sendAccountActivation(createdUser, user.tempAuthKey)
                redirect(action:'accountCreated', id: createdUser.id)
            } catch(Exception e) {
                log.error(e.getMessage(), e)
                render(view:"accountError")
            }
        }
    }

    private void updateProperties(user, params) {
        (new UserProperty(user: user, property: 'city', value: params.city ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'organisation', value: params.organisation ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'telephone', value: params.telephone ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'state', value: params.state ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'primaryUserType', value: params.primaryUserType ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'secondaryUserType', value: params.secondaryUserType ?: '')).save(flush: true)
    }

    def accountCreated = {
        def user = User.get(params.id)
        render(view:'accountCreated', model:[user:user])
    }

    def forgottenPassword = {}

    def activateAccount = {
        def user = User.get(params.userId)
        //check the activation key
        if(user.tempAuthKey == params.authKey){
            user.activated = true
            user.save(flush:true)
            //TODO create alerts...
            //TODO log the user in
            redirect(url: grailsApplication.config.security.cas.loginUrl + "?email=" + user.email)
        } else {
            log.error(e.getMessage(), e)
            render(view:"accountError")
        }
    }

    private def isEmailRegistered(email){
        def user = User.findByEmail(email.toLowerCase())
        if(user){
            true
        } else {
            false
        }
    }
}
