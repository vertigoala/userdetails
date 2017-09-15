package au.org.ala.userdetails

import groovy.transform.CompileStatic
import org.apache.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired

@CompileStatic
class UserDetailsWebServicesInterceptor {

    @Autowired
    AuthorisedSystemService authorisedSystemService

    UserDetailsWebServicesInterceptor() {
        match(controller: 'userDetails')
    }

    boolean before() {
        if (!authorisedSystemService.isAuthorisedSystem(request)) {
            log.warn("Denying access to $actionName from remote addr: ${request.remoteAddr}, remote host: ${request.remoteHost}")
            response.sendError(HttpStatus.SC_UNAUTHORIZED)

            return false
        }
        return true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
