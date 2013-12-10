package au.org.ala.userdetails

import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.FlickrApi
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.model.Verifier
import org.scribe.oauth.OAuthService
import org.springframework.web.context.request.RequestContextHolder

class ProfileController {

    def authService
    def oauthService
    def emailService

    def index() {
        def userId = authService.getUserId()
        User user = null
        if(userId.toString().isLong()){
             user = User.get(userId.toLong())
        } else {
             user = User.findByEmail(userId)
        }
        if(user){
            def props = user.propsAsMap()
            def isAdmin = RequestContextHolder.currentRequestAttributes()?.isUserInRole("ROLE_ADMIN")
            render(view:"myprofile", model:[user:user, props:props, isAdmin:isAdmin])
        } else {
            def loginUrl = grailsApplication.config.security.cas.loginUrl  +
                    "&service=" + URLEncoder.encode(emailService.getMyProfileUrl(),"UTF-8")
            redirect(url: loginUrl)
        }
    }

    def flickrCallback(){

        Token token = session.getAt("flickr:oasRequestToken")
        OAuthService service = new ServiceBuilder().
                 provider(FlickrApi.class).
                 apiKey(grailsApplication.config.oauth.providers.flickr.key).
                 apiSecret(grailsApplication.config.oauth.providers.flickr.secret).build()

        Verifier verifer = new Verifier(params.oauth_verifier)
        def accessToken = service.getAccessToken(token, verifer)

        // Now let's go and ask for a protected resource!
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://www.flickr.com/services/oauth/access_token");
        service.signRequest(accessToken, request);
        Response response = request.send();
        def model = [:]
        response.getBody().split("&").each{
            def property = it.substring(0, it.indexOf("="))
            def value = it.substring(it.indexOf("=")+1)
            model.put(property, value)
        }

        //store the user's flickr ID.
        User user = User.get(authService.getUserId().toLong())

        //store flickrID & flickrUsername
        UserProperty.addOrUpdateProperty(user, 'flickrId', URLDecoder.decode(model.get("user_nsid"), "UTF-8"))
        UserProperty.addOrUpdateProperty(user, 'flickrUsername', model.get("username"))

        redirect(controller:'profile')
    }

    def flickrSuccess(){
        //println "Flickr success called"
        String sessionKey = oauthService.findSessionKeyForAccessToken('twitter')
        //println "Session key: " + request.getSession().getAttribute(sessionKey)
    }

    def flickrFail(){}

    def removeFlickrLink(){
        User user = User.get(authService.getUserId().toLong())
        UserProperty.findByUserAndProperty(user, 'flickrUsername').delete(flush:true)
        UserProperty.findByUserAndProperty(user, 'flickrId').delete(flush:true)
        redirect(controller:'profile')
    }
}
