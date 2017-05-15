package au.org.ala.auth

import grails.validation.Validateable

/**
 * Data binding and validation for the password update action.
 */
@Validateable
class UpdatePasswordCommand {

    Long userId
    String password
    String reenteredPassword
    String authKey

    static constraints = {
        password minSize: 6, blank: false
        reenteredPassword validator: {val, cmd -> val == cmd.password}
        userId nullable: false
        authKey blank: false
    }


}
