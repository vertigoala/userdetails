package au.org.ala.userdetails

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(LocationService)
class LocationServiceSpec extends Specification {

    void "test resource loads"() {
        when:
            def result = service.getStatesAndCountries()
        then:
            result['countries']*.isoCode.contains('AU')
            result['states']['AU']*.isoCode.contains('ACT')
    }
}
