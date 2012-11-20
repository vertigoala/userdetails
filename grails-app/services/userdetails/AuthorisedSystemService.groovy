package userdetails

import javax.servlet.http.HttpServletRequest

class AuthorisedSystemService {

    def serviceMethod() {}

    def isAuthorisedSystem(HttpServletRequest request){
        def host = request.getHeader("x-forwarded-for")
        def hostMatched = AuthorisedSystem.findByHost(host)
        if(hostMatched)
            true
        else
            false
    }
}
