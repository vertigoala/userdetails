class UrlMappings {

  static mappings = {

    "/registration/$action?/$id?" {
       controller = 'registration'
       constraints {
           controller:'registration'
       }
    }
    "/registration/activateAccount/$userId/$authKey"(controller:'registration', action: 'activateAccount')
    "/registration/resetPassword/$userId/$authKey"(controller:'registration', action: 'passwordReset')

    "/registration/forgottenPassword"(controller:'registration', action: 'forgottenPassword')
    "/userdetails/getUserDetails"(controller:'userDetails', action: 'getUserDetails')
    "/userdetails/getUserList"(controller:'userDetails', action: 'getUserList')
    "/userdetails/getUserListFull"(controller:'userDetails', action: 'getUserListFull')
    "/userdetails/getUserListWithIds"(controller:'userDetails', action: 'getUserListWithIds')
    "/myprofile"(controller:'profile', action: 'index')
    "/simpleCaptcha/captcha"(controller:'simpleCaptcha', action:'captcha')
    "/simpleCaptcha/*"(controller:'simpleCaptcha')

    "/admin"(controller:'admin')
    "/admin/"(controller:'admin')

    "/admin/$controller/$action?/$id?" {
     constraints {}
    }

    "/admin/$controller/$action\\?" {
     constraints {}
    }

    "/"(view:"/index")
    "500"(view:'/error')
  }
}
