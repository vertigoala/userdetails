package au.org.ala.userdetails.marshaller
/**
 * Created by Temi Varghese on 8/09/15.
 */
class CustomObjectMarshallers {

    List marshallers = []

    def register() {
        marshallers.each{ it.register() }
    }
}