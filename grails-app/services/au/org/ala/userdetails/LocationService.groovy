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


    @Autowired
    ResourceLoader resourceLoader

    @Value('${attributes.states.path}')
    String statesPath = 'classpath:states.json'

    @Value('${attributes.states.path}')
    Resource states

    @NotTransactional
    @Cacheable("states")
    JSONObject getStatesAndCountries() {
        def resource = resourceLoader.getResource(statesPath)
        return resource.inputStream.withReader('UTF-8') { reader ->
            (JSONObject) JSON.parse(reader)
        }
    }
}
