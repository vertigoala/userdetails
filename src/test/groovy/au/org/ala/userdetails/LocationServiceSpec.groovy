package au.org.ala.userdetails

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class LocationServiceSpec extends Specification implements ServiceUnitTest<LocationService> {

    void "test resource loads"() {
        when:
            def result = service.getStatesAndCountries()
        then:
            result['countries']*.isoCode.contains('AU')
            result['states']['AU']*.isoCode.contains('ACT')
    }
}
