package au.org.ala.userdetails

import org.springframework.dao.DataIntegrityViolationException

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def addRole = {
        def user = User.get(params.userId)
        [user:user, roles:Role.all]
    }

    def list(Integer max) {
        if(params.q){
            def q = "%"+ params.q + "%"
            def userList = User.findAllByEmailLikeOrLastNameLikeOrFirstNameLike(q,q,q)
            [userInstanceList: userList, userInstanceTotal: userList.size(), q:params.q]
        } else {
            params.max = Math.min(max ?: 20, 5000)
            [userInstanceList: User.list(params), userInstanceTotal: User.count()]
        }
    }

    def create() {
        [userInstance: new User(params)]
    }

    private void updateProperties(user, params) {
        (new UserProperty(user: user, property: 'city', value: params.city ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'organisation', value: params.organisation ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'telephone', value: params.telephone ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'state', value: params.state ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'primaryUserType', value: params.primaryUserType ?: '')).save(flush: true)
        (new UserProperty(user: user, property: 'secondaryUserType', value: params.secondaryUserType ?: '')).save(flush: true)
    }

    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

        updateProperties(userInstance, params)

        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }
        [userInstance: userInstance, props:userInstance.propsAsMap()]
    }

    def update(Long id, Long version) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        updateProperties(userInstance, params)

        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "show", id: id)
        }
    }
}
