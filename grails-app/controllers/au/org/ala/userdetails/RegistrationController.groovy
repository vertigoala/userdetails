package au.org.ala.userdetails

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
    def passwordService
    def userService

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
        if(!user){
            render(view:'accountError')
        } else if(user.tempAuthKey == params.authKey){
            //keys match, so lets reset password
            render(view:'passwordReset', model:[user:user, authKey:params.authKey])
        } else {
            render(view:'authKeyExpired')
        }
    }

    def updatePassword = {
        User user = User.get(params.userId.toLong())
        if(params.password == params.reenteredPassword){
           //check the authKey for the user
           if(user.tempAuthKey == params.authKey){
               //update the password
               def success = passwordService.resetPassword(user, params.password)
               if(success){
                   redirect(controller: 'registration', action:'passwordResetSuccess')
               } else {
                   render(view:'accountError')
               }
           } else {
               render(view:'accountError')
           }
           log.info("Password successfully reset for user: " + params.userId)
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
            log.info("Starting password reset for email address: " + params.email)
            def user = User.findByEmail(params.email)
            if(user){
                try {
                    userService.resetAndSendTemporaryPassword(user, null, null, null)
                } catch (Exception e){
                    log.error("Problem starting password reset for email address: " + params.email)
                    log.error(e.getMessage(), e)
                    render(view:'accountError')
                }
            } else {
                //send password reset link
                render(view:'forgottenPassword', model:[email: params.email, captchaInvalid: false, invalidEmail:true])
            }
        }
    }

    def update = {
        def user = User.get(authService.getUserId().toLong())
        def success = userService.updateUser(user, params)
        if(success){
            redirect(controller: 'profile')
        } else {
            render(view:"accountError")
        }
    }

    def register = {

        //create user account...
        if(!params.email || userService.isEmailRegistered(params.email)){
            render(view:'createAccount', model:[edit:false, user:params, props:params, alreadyRegistered: true])
        } else {

            try {
                //does a user with the supplied email address exist
                def user = userService.registerUser(params)

                //store the password
                def success = passwordService.resetPassword(user, params.password)
                if(success){
                    //store the password
                    emailService.sendAccountActivation(user, user.tempAuthKey)
                    redirect(action:'accountCreated', id: user.id)
                } else {
                    render(view:"accountError")
                }
            } catch(Exception e) {
                log.error(e.getMessage(), e)
                render(view:"accountError")
            }
        }
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
            userService.activateAccount(user)
            redirect(url: grailsApplication.config.security.cas.loginUrl + "?email=" + user.email + "&service=" + grailsApplication.config.redirectAfterFirstLogin)
        } else {
            log.error('Auth keys did not match for user : ' + params.userId + ", supplied: " + params.authKey + ", stored: " + user.tempAuthKey)
            render(view:"accountError")
        }
    }
}
