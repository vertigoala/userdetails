package au.org.ala.userdetails

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.http.HttpStatus

/**
 * Specification for the UserRoleController
 */
@TestFor(UserRoleController)
@Mock([RoleBasedFilters, AuthorisedSystemService, User, Role, UserRole, UserProperty])
class UserRoleControllerSpec extends UserDetailsSpec {

    private User user

    void setup() {
        user = createUser()
    }

    void "Unauthorised users should not be able to access the user role UI"() {

        when:
        withFilters(action: 'list') {
            controller.list()
        }
        then:
        response.status == HttpStatus.SC_MOVED_TEMPORARILY // Redirect to CAS
    }

    void "ALA_ADMIN users should be able to access the user role UI"() {

        setup:
        request.addUserRole("ROLE_ADMIN")

        when:
        def model
        withFilters(action: 'list') {
            model = controller.list()
        }
        then:
        response.status == HttpStatus.SC_OK
        model.userRoleInstanceTotal == 1
    }

    void "Unauthorized systems should not be able to access the user role web service"() {

        setup:
        defineBeans {
            authorisedSystemService(UnAuthorised)
        }
        request.format = 'json'

        when:
        withFilters(action: 'list') {
            controller.list()
        }
        then:
        response.status == HttpStatus.SC_UNAUTHORIZED
    }

    void "Authorized systems should be able to access the user role web service"() {

        setup:
        registerMarshallers()
        defineBeans {
            authorisedSystemService(Authorised)
        }
        request.format = 'json'
        response.format = 'json'

        when:
        withFilters(action: 'list') {
            controller.list()
        }
        then:
        response.status == HttpStatus.SC_OK
        Map deserializedJson = JSON.parse(response.text)
        deserializedJson.count == 1
        deserializedJson.users[0].userId == Long.toString(user.id)
    }
}