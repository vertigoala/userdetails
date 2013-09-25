package au.org.ala.userdetails

import au.org.ala.auth.PreAuthorise
import org.springframework.web.context.request.RequestContextHolder

class RoleBasedFilters {

    def authService

    def filters = {
        all(controller: '*', action: '*') {
            before = {

                def controller = grailsApplication.getArtefactByLogicalPropertyName("Controller", controllerName)
                Class controllerClass = controller?.clazz
                def method = controllerClass?.getMethod(actionName?:"index", [] as Class[])

                if (method && (controllerClass.isAnnotationPresent(PreAuthorise) || method.isAnnotationPresent(PreAuthorise))) {
                    PreAuthorise pa = method.getAnnotation(PreAuthorise)?:controllerClass.getAnnotation(PreAuthorise)

                    def requiredRole = pa.requiredRole()

                    def inRole = RequestContextHolder.currentRequestAttributes()?.isUserInRole(requiredRole)

                    def errorMsg
                    if(!inRole){
                       errorMsg = "Access denied: User does not have required permission."
                    }

                    if (errorMsg) {
                        flash.message = errorMsg
                        redirect(url:grailsApplication.config.security.cas.appServerName  +
                             grailsApplication.config.security.cas.contextPath )
                    }
                }

            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
