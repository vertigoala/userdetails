grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolver = "maven" // or ivy

grails.project.fork = [
        test: false,
        run: false,
        war: false,
        console: false
]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false

    repositories {
        mavenLocal()
        mavenRepo("http://nexus.ala.org.au/content/groups/public/") {
            updatePolicy 'always'
        }
    }

    dependencies {
        runtime 'mysql:mysql-connector-java:5.1.34'
        build 'org.apache.httpcomponents:httpcore:4.1.2'
        build 'org.apache.httpcomponents:httpclient:4.1.2'
        build 'org.apache.httpcomponents:httpmime:4.1.2'
        build 'org.apache.commons:commons-lang3:3.1'
    }

    plugins {
        runtime ":hibernate:3.6.10.16"

        runtime ":ala-bootstrap2:2.1"
        runtime (":ala-auth:1.2") {
            exclude "servlet-api"
        }
        compile ":csv:0.3.1"
        build ":tomcat:7.0.54"

        compile ":lesscss-resources:1.3.3"
        compile ":mail:1.0.4"
        compile ":oauth:2.1.0"
        compile ":simple-captcha:0.9.9"
        build ':release:3.0.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}
