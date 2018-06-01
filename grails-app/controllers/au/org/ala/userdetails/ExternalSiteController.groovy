package au.org.ala.userdetails

import grails.converters.JSON

class ExternalSiteController {

    def userService

    def index() {}

    def flickr() {

        def flickrIds = UserProperty.findAllByProperty("flickrId")
        render(contentType: "application/json") {
            flickrUsers(flickrIds) { UserProperty flickrId ->
                id flickrId.user.id.toString()
                externalId flickrId.value
                externalUsername flickrId.user.propsAsMap().flickrUsername
                externalUrl 'http://www.flickr.com/photos/' + flickrId.value
            }
        }
    }

    def getUserStats() {
        def stats = userService.getUsersCounts(request.locale)
        render(stats as JSON, contentType: "application/json")  // getUsersCounts is cached
    }
}
