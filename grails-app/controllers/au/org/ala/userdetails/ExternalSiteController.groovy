package au.org.ala.userdetails

class ExternalSiteController {

    def index() {}

    def flickr(){

        def flickrIds = UserProperty.findAllByProperty("flickrId")
        render(contentType: "text/json") {
          array {
                flickrIds.each { flickrId ->
                  user (
                      id: flickrId.user.id.toString(),
                      externalId: flickrId.value,
                      externalUsername: flickrId.user.propsAsMap().flickrUsername,
                      externalUrl: 'http://www.flickr.com/photos/' + flickrId.value
                  )
             }
          }
        }
    }
}
