package au.org.ala.userdetails

import au.org.ala.auth.PreAuthorise
import org.springframework.dao.DataIntegrityViolationException

@PreAuthorise
class UserRoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
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
}
