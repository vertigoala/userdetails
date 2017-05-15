package au.org.ala.userdetails

import au.org.ala.auth.PreAuthorise
import org.apache.http.HttpStatus
import org.springframework.web.context.request.RequestContextHolder

class RoleBasedFilters {

    def authorisedSystemService

    def filters = {
        all(controller: '*', action: '*') {
            before = {

                // If the method or controller has the PreAuthorise annotation:
                // 1) For html we validate the user is the in role specified by the annotation
                // 2) For json requests we check the calling system is in the authorized systems list.
                def controller = grailsApplication.getArtefactByLogicalPropertyName("Controller", controllerName)
                Class controllerClass = controller?.clazz
                def method = controllerClass?.getMethod(actionName ?: "index", [] as Class[])

                if (method && (controllerClass.isAnnotationPresent(PreAuthorise) || method.isAnnotationPresent(PreAuthorise))) {
                    response.withFormat {
                        html {
                            PreAuthorise pa = method.getAnnotation(PreAuthorise) ?: controllerClass.getAnnotation(PreAuthorise)
                            def requiredRole = pa.requiredRole()
                            def inRole = RequestContextHolder.currentRequestAttributes()?.isUserInRole(requiredRole)

                            if (!inRole) {
                                flash.message = "Access denied: User does not have required permission."
                                redirect(url: grailsApplication.config.security.cas.appServerName +
                                        grailsApplication.config.security.cas.contextPath)
                            }
                        }

                        json {
                            if (!authorisedSystemService.isAuthorisedSystem(request)) {
                                response.sendError(HttpStatus.SC_UNAUTHORIZED)

                                return false
                            }
                        }
                    }
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }

        userDetailsWebServices(controller:"userDetails", action:"*") {
            before = {
                if (!authorisedSystemService.isAuthorisedSystem(request)) {
                    response.sendError(HttpStatus.SC_UNAUTHORIZED)

                    return false
                }
            }
            after = {Map model -> }
            afterView = {Exception e -> }
        }
    }
}
