//import com.github.scribejava.apis.FlickrApi

// these placeholder values are overridden at runtime using the external configuration properties file
oauth {
    providers {
        flickr {
            api = 'com.github.scribejava.apis.FlickrApi'
            key = "xxxxxxxxxxxxxxxxxxxxx"
            secret = "xxxxxxxxxxxxxxxxxxxxx"
            successUri = '/profile/flickrSuccess'
            failureUri = '/profile/flickrFail'
            callback = "xxxxxxxxxxxxx/yyyyyyyyyy/profile/flickrCallback"
        }
    }
//   debug = true
}
