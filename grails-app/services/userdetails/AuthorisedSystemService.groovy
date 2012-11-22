package userdetails

import javax.servlet.http.HttpServletRequest

class AuthorisedSystemService {

    def serviceMethod() {}

    def isAuthorisedSystem(HttpServletRequest request){
        def host = request.getHeader("x-forwarded-for")
        if(host == null){
          host = request.getRemoteHost()
        }
        println("host: " + request.getRemoteHost())
        println("host: " + request.getRemoteAddr())
        println("host using: " + host)
        if(host != null && AuthorisedSystem.findByHost(host))
          true
        else
          false
    }
}
