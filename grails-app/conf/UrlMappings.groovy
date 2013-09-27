class UrlMappings {

  static mappings = {

    "/my-profile/" {
       controller = 'profile'
    }
    "/my-profile" {
       controller = 'profile'
    }
    "/myprofile/" {
       controller = 'profile'
    }
    "/myprofile" {
       controller = 'profile'
    }
    "/profile/$action?" {
       controller = 'profile'
    }

    "/external/flickr"(controller: 'externalSite', action: 'flickr')

    "/registration/$action?/$id?" {
       controller = 'registration'
       constraints {
           controller:'registration'
       }
    }

    "/registration/activateAccount/$userId/$authKey"(controller:'registration', action: 'activateAccount')
    "/registration/resetPassword/$userId/$authKey"(controller:'registration', action: 'passwordReset')

    "/registration/forgottenPassword"(controller:'registration', action: 'forgottenPassword')

    "/userDetails/getUserDetails"(controller:'userDetails', action: 'getUserDetails')
    "/userDetails/getUserList"(controller:'userDetails', action: 'getUserList')
    "/userDetails/getUserListFull"(controller:'userDetails', action: 'getUserListFull')
    "/userDetails/getUserListWithIds"(controller:'userDetails', action: 'getUserListWithIds')

//    "/userdetails/getUserDetails"(controller:'userDetails', action: 'getUserDetails')
//    "/userdetails/getUserList"(controller:'userDetails', action: 'getUserList')
//    "/userdetails/getUserListFull"(controller:'userDetails', action: 'getUserListFull')
//    "/userdetails/getUserListWithIds"(controller:'userDetails', action: 'getUserListWithIds')

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

    "/logout/logout"(controller: "logout", action: 'logout')
    "/"(view:"/index")
    "500"(view:'/error')
  }
}
