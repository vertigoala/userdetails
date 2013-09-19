package au.org.ala.userdetails

import javax.servlet.http.HttpServletRequest

class AuthorisedSystemService {

    def serviceMethod() {}

    def isAuthorisedSystem(HttpServletRequest request){
        def host = request.getHeader("x-forwarded-for")
        if(host == null){
          host = request.getRemoteHost()
        }
        log.debug("RemoteHost: " + request.getRemoteHost())
        log.debug("RemoteAddr: " + request.getRemoteAddr())
        log.debug("host using: " + host)

        return host != null && AuthorisedSystem.findByHost(host)
    }
}
