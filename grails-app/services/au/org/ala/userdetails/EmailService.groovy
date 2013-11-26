package au.org.ala.userdetails

import au.org.ala.auth.PasswordResetFailedException

class EmailService {

    def grailsApplication

    static transactional = false

    def sendPasswordReset(user, authKey) throws PasswordResetFailedException {

        try {
            sendMail {
              from grailsApplication.config.emailSenderTitle+"<" + grailsApplication.config.emailSender + ">"
              subject "Reset your password"
              to user.email
              body (view: '/email/resetPassword',
                    plugin:"email-confirmation",
                    model:[link:getServerUrl() + "resetPassword/" +  user.id +  "/"  + authKey ]
              )
            }
        } catch (Exception ex) {
            throw new PasswordResetFailedException(ex)
        }
    }

    def sendAccountActivation(user, authKey){
        sendMail {
          from grailsApplication.config.emailSenderTitle+"<" + grailsApplication.config.emailSender + ">"
          subject "Activate your account"
          to user.email
          body (view: '/email/activateAccount',
                plugin:"email-confirmation",
                model:[link:getServerUrl() + "activateAccount/" + user.id + "/"  + authKey ]
          )
        }
    }

    def sendGeneratedPassword(user, generatedPassword){
        sendMail {
          from grailsApplication.config.emailSenderTitle+"<" + grailsApplication.config.emailSender + ">"
          subject "Accessing your account"
          to user.email
          body (view: '/email/accessAccount',
                plugin:"email-confirmation",
                model:[link:getLoginUrl(user.email), generatedPassword: generatedPassword]
          )
        }
    }

    def getLoginUrl(email){
            grailsApplication.config.security.cas.loginUrl  +
                    "?email=" + email +
                    "&service=" + URLEncoder.encode(getMyProfileUrl(),"UTF-8")
    }

    def getMyProfileUrl(){
            grailsApplication.config.security.cas.appServerName  +
                    grailsApplication.config.security.cas.contextPath +
                    "/myprofile/"

    }

    def getServerUrl(){
            grailsApplication.config.security.cas.appServerName  +
                    grailsApplication.config.security.cas.contextPath +
                    "/registration/"
    }
}
