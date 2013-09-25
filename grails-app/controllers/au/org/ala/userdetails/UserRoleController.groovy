package au.org.ala.userdetails

import au.org.ala.auth.PreAuthorise
import org.springframework.dao.DataIntegrityViolationException

@PreAuthorise
class UserRoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {

        if(params.role){
            def role = Role.findByRole(params.role)
            if(role){
                params.max = Math.min(max ?: 100, 1000)
                def list = UserRole.findAllByRole(role,params)
                [userRoleInstanceList: list, userRoleInstanceTotal: UserRole.findAllByRole(role).size()]
            } else {
                [userRoleInstanceList: [], userRoleInstanceTotal: 0]
            }
        } else {
            params.max = Math.min(max ?: 100, 1000)
            [userRoleInstanceList: UserRole.list(params), userRoleInstanceTotal: UserRole.count()]
        }
    }

    def create() {
        User user = User.get(params['user.id'].toLong())
        def roles = au.org.ala.userdetails.Role.list()

        //remove existing roles this user has
        def usersRoles = user.getUserRoles()

        def acquiredRoles = []
        usersRoles.each { acquiredRoles << it.role}

        roles.removeAll(acquiredRoles)

        [user: user, roles:roles]
    }

    def addRole() {

        log.debug(params.userId.toLong() + " - " + params.role.id)

        def user = User.get(params.userId.toLong())
        def role = Role.findByRole(params.role.id)

        UserRole ur = new UserRole()
        ur.user = user
        ur.role = role
        ur.save(flush:true)

        redirect(action: "show", controller: 'user', id: user.id)
    }

    def deleteRole() {

        def user = User.get(params.userId.toLong())
        def role = Role.get(params.role)

        def userRoleInstance = UserRole.findByUserAndRole(user, role)
        if (!userRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), role.role])
            redirect(controller:"user", action: "edit", id:user.id)
            return
        }

        try {
            userRoleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'userRole.label', default: 'UserRole'), role.role])
            redirect(controller:"user", action: "edit", id:user.id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'userRole.label', default: 'UserRole'), role.role])
            redirect(action: "show", id: id)
        }
    }

    def show(Long id) {
        def userRoleInstance = UserRole.get(id)
        if (!userRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), id])
            redirect(action: "list")
            return
        }

        [userRoleInstance: userRoleInstance]
    }

    def edit(Long id) {
        def userRoleInstance = UserRole.get(id)
        if (!userRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), id])
            redirect(action: "list")
            return
        }

        [userRoleInstance: userRoleInstance]
    }

    def update(Long id, Long version) {
        def userRoleInstance = UserRole.get(id)
        if (!userRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userRoleInstance.version > version) {
                userRoleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'userRole.label', default: 'UserRole')] as Object[],
                        "Another user has updated this UserRole while you were editing")
                render(view: "edit", model: [userRoleInstance: userRoleInstance])
                return
            }
        }

        userRoleInstance.properties = params

        if (!userRoleInstance.save(flush: true)) {
            render(view: "edit", model: [userRoleInstance: userRoleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'userRole.label', default: 'UserRole'), userRoleInstance.id])
        redirect(action: "show", id: userRoleInstance.id)
    }

//    def delete(Long id) {
//        def userRoleInstance = UserRole.get(id)
//        if (!userRoleInstance) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), id])
//            redirect(action: "list")
//            return
//        }
//
//        try {
//            userRoleInstance.delete(flush: true)
//            flash.message = message(code: 'default.deleted.message', args: [message(code: 'userRole.label', default: 'UserRole'), id])
//            redirect(action: "list")
//        }
//        catch (DataIntegrityViolationException e) {
//            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'userRole.label', default: 'UserRole'), id])
//            redirect(action: "show", id: id)
//        }
//    }
}
