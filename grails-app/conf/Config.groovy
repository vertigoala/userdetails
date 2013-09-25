import org.scribe.builder.api.FlickrApi

/******************************************************************************\
 *  CONFIG MANAGEMENT
\******************************************************************************/
def appName = 'userdetails'
def ENV_NAME = "${appName.toUpperCase()}_CONFIG"
def default_config = "/data/${appName}/config/${appName}-config.properties"
if(!grails.config.locations || !(grails.config.locations instanceof List)) {
    grails.config.locations = []
}

if(System.getenv(ENV_NAME) && new File(System.getenv(ENV_NAME)).exists()) {
    println "[${appName}] Including configuration file specified in environment: " + System.getenv(ENV_NAME);
    grails.config.locations.add "file:" + System.getenv(ENV_NAME)
} else if(System.getProperty(ENV_NAME) && new File(System.getProperty(ENV_NAME)).exists()) {
    println "[${appName}] Including configuration file specified on command line: " + System.getProperty(ENV_NAME);
    grails.config.locations.add "file:" + System.getProperty(ENV_NAME)
} else if(new File(default_config).exists()) {
    println "[${appName}] Including default configuration file: " + default_config;
    grails.config.locations.add "file:" + default_config
} else {
    println "[${appName}] No external configuration file defined."
}

println "[${appName}] (*) grails.config.locations = ${grails.config.locations}"

/******************************************************************************\
 *  SECURITY
\******************************************************************************/
if (!security.cas.uriFilterPattern) {
    security.cas.uriFilterPattern = "/admin/.*,/registration/editAccount, /my-profile, /my-profile/, /myprofile/, /myprofile, /profile/.*, /admin/, /admin, /registration/update, /registration/update/.*"
}
if (!security.cas.loginUrl) {
    security.cas.loginUrl = "https://auth.ala.org.au/cas/login"
}
if (!security.cas.logoutUrl) {
    security.cas.logoutUrl = "https://auth.ala.org.au/cas/logout"
}
if (!security.apikey.serviceUrl) {
    security.apikey.serviceUrl = "http://auth.ala.org.au/apikey/ws/check?apikey="
}
if(!security.cas.appServerName){
    security.cas.appServerName = "http://devt.ala.org.au:8080"
}
if(!security.cas.contextPath ){
    security.cas.contextPath = "/${appName}"
}
if(!security.cas.casServerName){
    security.cas.casServerName = "https://auth.ala.org.au"
}
if(!security.cas.uriExclusionFilterPattern){
    security.cas.uriExclusionFilterPattern = '/images.*,/css.*,/js.*,/less.*'
}
if(!security.cas.authenticateOnlyIfLoggedInPattern){
    security.cas.authenticateOnlyIfLoggedInPattern = "" // pattern for pages that can optionally display info about the logged-in user
}
if(!security.cas.casServerUrlPrefix){
    security.cas.casServerUrlPrefix = 'https://auth.ala.org.au/cas'
}
if(!security.cas.bypass){
    security.cas.bypass = false
}
if(!supportEmail){
    supportEmail = 'support@ala.org.au'
}
if(!homeUrl){
    homeUrl = 'http://www.ala.org.au'
}
if(!mainTitle){
    mainTitle = 'Atlas of Living Australia'
}
if(!emailSenderTitle){
    emailSenderTitle = 'Atlas of Living Australia'
}
if(!emailSender){
    emailSender = 'support@ala.org.au'
}
if(!adminRoles){
    adminRoles = ['ROLE_ADMIN']
}
if(!encoding.algorithm){
    encoding.algorithm = "xxxxxxxxxxxxxxxxxxxxx"
}
if(!encoding.salt){
    encoding.salt = "xxxxxxxxxxxxxxxxxxxxx"
}
if(!redirectAfterFirstLogin){
    redirectAfterFirstLogin = "http://www.ala.org.au/my-profile"
}

/******************************************************************************/
grails.project.groupId = 'au.org.ala.userdetails' // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.serverURL = "http://devt.ala.org.au:8080/userdetails"
        grails.logging.jul.usebridge = true
        grails {
          mail {
            host = "localhost"
            port = 1025
            username = postie.emailSender
          }
        }
    }
    production {
        grails.serverURL = "http://auth.ala.org.au/userdetails"
        security.cas.appServerName = "http://auth.ala.org.au"
        grails.logging.jul.usebridge = false
        grails {
          mail {
            host = "localhost"
            port = 25
            username = postie.emailSender
          }
        }
    }
}

log4j = {
    appenders {
        environments {
            production {
                rollingFile name: "${appName}-prod",
                    maxFileSize: 104857600,
                    file: "/var/log/tomcat6/${appName}.log",
                    threshold: org.apache.log4j.Level.INFO,
                    layout: pattern(conversionPattern: "%d [%c{1}]  %m%n")
                rollingFile name: "stacktrace", maxFileSize: 1024, file: "/var/log/tomcat6/${appName}-stacktrace.log"
            }
            development{
                console name: "stdout", layout: pattern(conversionPattern: "%d [%c{1}]  %m%n"), threshold: org.apache.log4j.Level.DEBUG
            }
        }
    }

    root {
        debug  '${appName}-prod'
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate',
            'org.codehaus.groovy.grails.plugins.orm.auditable',
            'org.mortbay.log', 'org.springframework.webflow',
            'grails.app',
            'org.apache',
            'org',
            'com',
            'au',
            'grails.app',
            'net',
            'grails.util.GrailsUtil',
            'grails.app.service.org.grails.plugin.resource',
            'grails.app.service.org.grails.plugin.resource.ResourceTagLib',
            'grails.app',
            'grails.plugin.springcache',
            'au.org.ala.cas.client',
            'grails.spring.BeanBuilder',
            'grails.plugin.webxml',
            'grails.plugin.cache.web.filter'
    debug  'ala'
}

oauth {
    providers {
        flickr {
            api = FlickrApi
            key = "xxxxxxxxxxxxxxxxxxxxx"
            secret = "xxxxxxxxxxxxxxxxxxxxx"
            successUri = '/profile/flickrSuccess'
            failureUri = '/profile/flickrFail'
            callback = security.cas.appServerName + security.cas.contextPath + "/profile/flickrCallback"
        }
    }
//   debug = true
}