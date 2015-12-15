package au.org.ala.userdetails

import grails.converters.JSON

class PropertyController extends BaseController {

    def profileService
    def authorisedSystemService

    static allowedMethods = [getProperty: "GET", saveProperty: "POST"]

    def index() {}

    /**
     * get a property value for a user
     * @return
     */
    def getProperty() {
        if (authorisedSystemService.isAuthorisedSystem(request)) {
            String name = params.name;
            Long alaId = Long.parseLong(params.alaId);
            if (!name || !alaId) {
                badRequest "name and alaId must be provided";
            } else {
                User user = User.findById(alaId);
                List props
                if (user) {
                    props = profileService.getUserProperty(user, name);
                    render text: props as JSON, contentType: 'application/json'
                } else {
                    notFound "Could not find user for id: ${alaId}";
                }
            }
        } else {
            response.sendError(403)
        }
    }

    /**
     * save a property value to a user
     * @return
     */
    def saveProperty(){
        if (authorisedSystemService.isAuthorisedSystem(request)) {
            String name = params.name;
            String value = params.value;
            Long alaId = Long.parseLong(params.alaId);
            if (!name || !alaId) {
                badRequest "name and alaId must be provided";
            } else {
                User user = User.findById(alaId);
                UserProperty property
                if (user) {
                    property = profileService.saveUserProperty(user, name, value);
                    if (property.hasErrors()) {
                        saveFailed()
                    } else {
                        render text: property as JSON, contentType: 'application/json'
                    }
                } else {
                    notFound "Could not find user for id: ${alaId}";
                }
            }
        } else {
            response.sendError(403)
        }
    }
}
