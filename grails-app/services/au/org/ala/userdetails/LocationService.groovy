package au.org.ala.userdetails

import grails.converters.JSON
import grails.plugin.cache.Cacheable
import grails.transaction.NotTransactional
import org.grails.web.json.JSONObject

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader

class LocationService {

    @Value('${attributes.states.path}')
    Resource states

    @NotTransactional
    @Cacheable("states")
    JSONObject getStatesAndCountries() {
        return states.inputStream.withReader('UTF-8') { reader ->
            (JSONObject) JSON.parse(reader)
        }
    }
}
