package au.org.ala.userdetails

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.grails.web.servlet.mvc.SynchronizerTokensHolder

//@Mock([User, Role, UserRole, UserProperty])
class RegistrationControllerSpec extends UserDetailsSpec implements ControllerUnitTest<RegistrationController>, DataTest {

    def passwordService = Mock(PasswordService)
    def userService = Mock(UserService)
    void setup() {
        controller.passwordService = passwordService
        controller.userService = userService
    }

    void setupSpec() {
        mockDomains(User, Role, UserRole, UserProperty)
    }

    void "A new password must be supplied"() {
        setup:
        request.method = 'POST'
        params.userId = Long.toString(1)
        params.authKey = "test"

        when:
        params.password = ""
        params.reenterPassword = ""
        controller.updatePassword()

        then:
        model.errors.getFieldError("password") != null
        view == '/registration/passwordReset'
    }

    void "The new password must be at least 6 characters long"() {
        setup:
        request.method = 'POST'
        params.userId = Long.toString(1)
        params.authKey = "test"

        when:
        params.password = "12345"
        controller.updatePassword()

        then:
        model.errors.getFieldError("password") != null
        view == '/registration/passwordReset'
    }

    void "The new re-entered password must be the same as the original"() {
        setup:
        request.method = 'POST'
        params.userId = Long.toString(1)
        params.authKey = "test"

        when:
        params.password = "123456"
        params.reenteredPassword = "12345"
        controller.updatePassword()

        then:
        model.errors.getFieldError("reenteredPassword") != null
        view == '/registration/passwordReset'
    }

    void "Duplicate submits of the password form are directed to a page explaining what has happened"() {
        setup:
        request.method = 'POST'
        params.userId = Long.toString(1)
        params.authKey = "test"

        when:
        params.password = "123456"
        params.reenteredPassword = "123456"

        // Note that duplicate submit error is the default behaviour.
        controller.updatePassword()

        then:
        response.redirectedUrl == '/registration/duplicateSubmit'
    }

    void "A successful submission will result in the users password being reset"() {
        setup:
        String authKey = "test"
        String password = "password"
        User user = createUser(authKey)
        request.method = 'POST'
        params.userId = Long.toString(1)
        params.authKey = authKey

        // This is to allow the submitted token to pass validation.  Failure to do this will result in the invalidToken block being used.
        def tokenHolder = SynchronizerTokensHolder.store(session)

        params[SynchronizerTokensHolder.TOKEN_URI] = '/controller/handleForm'
        params[SynchronizerTokensHolder.TOKEN_KEY] = tokenHolder.generateToken(params[SynchronizerTokensHolder.TOKEN_URI])

        when:
        params.password = password
        params.reenteredPassword = password

        // Note that duplicate submit error is the default behaviour.
        controller.updatePassword()

        then:
        1 * passwordService.resetPassword(user, password)
        1 * userService.clearTempAuthKey(user)
        response.redirectedUrl == '/registration/passwordResetSuccess'
    }
}
