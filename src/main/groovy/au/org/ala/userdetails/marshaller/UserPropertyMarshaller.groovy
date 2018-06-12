/**
 * Created by Temi Varghese on 8/09/15.
 */
package au.org.ala.userdetails.marshaller

import grails.converters.JSON
import au.org.ala.userdetails.*

class UserPropertyMarshaller {
    void register(){

        JSON.registerObjectMarshaller(UserProperty){ UserProperty prop ->
            return [
                    property: prop.name,
                    value: prop.value
            ]
        }
    }
}