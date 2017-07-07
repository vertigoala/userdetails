grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
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
        //The repo below and commons-beanutils dependency are a workaround required to make export plugin work
        // More details at http://stackoverflow.com/questions/24411420/grails-export-plugin-errors
        mavenRepo "http://repo.grails.org/grails/core"
    }

    dependencies {
        runtime 'mysql:mysql-connector-java:5.1.42'
        build 'org.apache.httpcomponents:httpcore:4.3.3'
        build 'org.apache.httpcomponents:httpclient:4.3.3'
        build 'org.apache.httpcomponents:httpmime:4.3.3'
        build 'org.apache.commons:commons-lang3:3.1'
        compile 'commons-beanutils:commons-beanutils:1.8.3'
        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"
    }

    plugins {
        runtime ":hibernate4:4.3.10"

        runtime ":ala-bootstrap2:2.5.0"
        runtime ":ala-ws-plugin:1.7"
        runtime ":ala-auth:2.1.3"
        runtime ":ala-admin-plugin:1.2"
        compile ":csv:0.3.1"
        build ":tomcat:7.0.70"

        compile ":lesscss-resources:1.3.3"
        compile ":mail:1.0.7"
        compile ":oauth:2.6.1"
        compile ":simple-captcha:1.0.0"
        compile ":export:1.6"
        compile ':cache:1.1.8'
        compile ":cache-ehcache:1.0.5"

        build ':release:3.0.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}
