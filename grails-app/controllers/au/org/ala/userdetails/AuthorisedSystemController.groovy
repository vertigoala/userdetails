package au.org.ala.userdetails

import au.org.ala.auth.PreAuthorise
import org.springframework.dao.DataIntegrityViolationException

@PreAuthorise
class AuthorisedSystemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [authorisedSystemInstanceList: AuthorisedSystem.list(params), authorisedSystemInstanceTotal: AuthorisedSystem.count()]
    }

    def create() {
        [authorisedSystemInstance: new AuthorisedSystem(params)]
    }

    def save() {
        def authorisedSystemInstance = new AuthorisedSystem(params)
        if (!authorisedSystemInstance.save(flush: true)) {
            render(view: "create", model: [authorisedSystemInstance: authorisedSystemInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem'), authorisedSystemInstance.id])
        redirect(action: "show", id: authorisedSystemInstance.id)
    }

    def show(Long id) {
        def authorisedSystemInstance = AuthorisedSystem.get(id)
        if (!authorisedSystemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem'), id])
            redirect(action: "list")
            return
        }

        [authorisedSystemInstance: authorisedSystemInstance]
    }

    def edit(Long id) {
        def authorisedSystemInstance = AuthorisedSystem.get(id)
        if (!authorisedSystemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem'), id])
            redirect(action: "list")
            return
        }

        [authorisedSystemInstance: authorisedSystemInstance]
    }

    def update(Long id, Long version) {
        def authorisedSystemInstance = AuthorisedSystem.get(id)
        if (!authorisedSystemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (authorisedSystemInstance.version > version) {
                authorisedSystemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem')] as Object[],
                          "Another user has updated this AuthorisedSystem while you were editing")
                render(view: "edit", model: [authorisedSystemInstance: authorisedSystemInstance])
                return
            }
        }

        authorisedSystemInstance.properties = params

        if (!authorisedSystemInstance.save(flush: true)) {
            render(view: "edit", model: [authorisedSystemInstance: authorisedSystemInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem'), authorisedSystemInstance.id])
        redirect(action: "show", id: authorisedSystemInstance.id)
    }

    def delete(Long id) {
        def authorisedSystemInstance = AuthorisedSystem.get(id)
        if (!authorisedSystemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem'), id])
            redirect(action: "list")
            return
        }

        try {
            authorisedSystemInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'authorisedSystem.label', default: 'AuthorisedSystem'), id])
            redirect(action: "show", id: id)
        }
    }
}
