package au.org.ala.userdetails

class EmailService {

    def grailsApplication

    def serviceMethod() {}

    def sendPasswordReset(user, authKey){
        sendMail {
          from grailsApplication.config.emailSenderTitle+"<" + grailsApplication.config.emailSender + ">"
          subject "Reset you password"
          to user.email
          body (view: '/email/resetPassword',
                plugin:"email-confirmation",
                model:[link:getServerUrl() + "resetPassword/" +  user.id +  "/"  + authKey ]
          )
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
