package au.org.ala.userdetails

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.web.InterceptorUnitTestMixin
import org.apache.http.HttpStatus
import org.grails.web.util.GrailsApplicationAttributes

/**
 * Tests the UserDetailsController and the filtering behaviour associated with it.
 */
@TestFor(UserDetailsController)
@TestMixin(InterceptorUnitTestMixin)
@Mock([UserDetailsWebServicesInterceptor, User, Role, UserRole, UserProperty])
class UserDetailsControllerSpec extends UserDetailsSpec {

    static doWithSpring = {
        authorisedSystemService(UserDetailsSpec.Authorised)
    }

    private User user

    void setup() {
        user = createUser()
    }

    void "Authorised systems should be able to use the UserDetailsController web services"() {
        setup:
        registerMarshallers()

        when:
        request.method = 'POST'
        params.userName = Long.toString(user.id)
        request.setAttribute(GrailsApplicationAttributes.CONTROLLER_NAME_ATTRIBUTE, 'userDetails')
        request.setAttribute(GrailsApplicationAttributes.ACTION_NAME_ATTRIBUTE, 'getUserDetails')
        withInterceptors(controller: 'userDetails', action:'getUserDetails') {
            controller.getUserDetails()
        }
        then:
        response.status == HttpStatus.SC_OK

        when:
        response.reset()
        request.method = 'POST'
        request.setAttribute(GrailsApplicationAttributes.CONTROLLER_NAME_ATTRIBUTE, 'userDetails')
        request.setAttribute(GrailsApplicationAttributes.ACTION_NAME_ATTRIBUTE, 'getUserList')
        withInterceptors(controller: 'userDetails', action:'getUserList') {
            controller.getUserList()
        }
        then:
        response.status == HttpStatus.SC_OK

        when:
        response.reset()
        request.method = 'POST'
        request.setAttribute(GrailsApplicationAttributes.CONTROLLER_NAME_ATTRIBUTE, 'userDetails')
        request.setAttribute(GrailsApplicationAttributes.ACTION_NAME_ATTRIBUTE, 'getUserListWithIds')
        withInterceptors(controller: 'userDetails', action:'getUserListWithIds') {
            controller.getUserListWithIds()
        }
        then:
        response.status == HttpStatus.SC_OK

        when:
        response.reset()
        request.method = 'POST'
        request.setAttribute(GrailsApplicationAttributes.CONTROLLER_NAME_ATTRIBUTE, 'userDetails')
        request.setAttribute(GrailsApplicationAttributes.ACTION_NAME_ATTRIBUTE, 'getUserListFull')
        withInterceptors(controller: 'userDetails', action:'getUserListFull') {
            controller.getUserListFull()
        }
        then:
        response.status == HttpStatus.SC_OK

        when:
        response.reset()
        request.method = 'POST'
        request.setAttribute(GrailsApplicationAttributes.CONTROLLER_NAME_ATTRIBUTE, 'userDetails')
        request.setAttribute(GrailsApplicationAttributes.ACTION_NAME_ATTRIBUTE, 'getUserDetailsFromIdList')
        withInterceptors(controller: 'userDetails', action:'getUserDetailsFromIdList') {
            controller.getUserDetailsFromIdList()
        }
        then:
        response.status == HttpStatus.SC_OK
    }

    void "A user can be found by user id"() {
        when:
        request.method = 'POST'
        params.userName = Long.toString(user.id)
        controller.getUserDetails()

        then:
        response.contentType == 'application/json;charset=UTF-8'

        Map deserializedJson = JSON.parse(response.text)
        deserializedJson.userId == Long.toString(user.id)
        deserializedJson.userName == user.userName

        deserializedJson.firstName == user.firstName
        deserializedJson.lastName == user.lastName
        deserializedJson.email == user.email
        deserializedJson.roles == ['ROLE_USER']
        deserializedJson.props == null

    }

    void "A user can be found by user id and the user properties can be returned"() {
        when:
        request.method = 'POST'
        params.userName = Long.toString(user.id)
        params.includeProps = true
        controller.getUserDetails()

        then:
        response.contentType == 'application/json;charset=UTF-8'

        Map deserializedJson = JSON.parse(response.text)
        deserializedJson.userId == Long.toString(user.id)
        deserializedJson.userName == user.userName

        deserializedJson.firstName == user.firstName
        deserializedJson.lastName == user.lastName
        deserializedJson.email == user.email
        deserializedJson.roles == ['ROLE_USER']
        deserializedJson.props == [prop1:'prop1', prop2:'prop2']
    }

    void "Details of a list of users can be returned"() {

        setup:
        User user2 = createUser()

        when:
        request.method = 'POST'
        request.JSON = [userIds:[Long.toString(user.id), Long.toString(user2.id)]] as JSON
        controller.getUserDetailsFromIdList()

        then:
        response.contentType == 'application/json;charset=UTF-8'

        Map deserializedJson = JSON.parse(response.text)
        deserializedJson.users.size() == 2

        Map user1 = deserializedJson.users[Long.toString(user.id)]
        user1.userId == Long.toString(user.id)
        user1.userName == user.userName
        user1.firstName == user.firstName
        user1.lastName == user.lastName
        user1.email == user.email
        user1.roles == ['ROLE_USER']

        Map user2Map = deserializedJson.users[Long.toString(user2.id)]
        user2Map.userId == Long.toString(user2.id)
        user2Map.userName == user2.userName
        user2Map.firstName == user2.firstName
        user2Map.lastName == user2.lastName
        user2Map.email == user2.email
        user2Map.roles == ['ROLE_USER']

    }


}
