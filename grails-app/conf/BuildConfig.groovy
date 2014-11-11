grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo "http://maven.ala.org.au/repository/"
        mavenRepo "http://repo1.maven.org/maven2/"
        mavenRepo "http://oss.sonatype.org/content/repositories/releases/"
        mavenRepo "https://repository.jboss.org/nexus/content/groups/public/"
        mavenRepo "http://developer.jasig.org/repo/content/groups/m2-legacy/"
    }
    dependencies {
//        compile "com.github.inspektr:inspektr-audit:1.0.7.GA"
//        runtime 'postgresql:postgresql:9.1-901.jdbc4'
        runtime 'org.postgresql:postgresql:9.3-1100-jdbc41'
  //      runtime 'mysql:mysql-connector-java:5.1.18'
        build 'org.apache.httpcomponents:httpcore:4.1.2'
        build 'org.apache.httpcomponents:httpclient:4.1.2'
        build 'org.apache.httpcomponents:httpmime:4.1.2'
//        runtime 'org.apache.httpcomponents:httpcore:4.1.2'
//        runtime 'org.apache.httpcomponents:httpclient:4.1.2'
//        runtime 'org.apache.httpcomponents:httpmime:4.1.2'
        build 'org.apache.commons:commons-lang3:3.1'
//        runtime 'org.apache.commons:commons-lang3:3.1'
//        compile 'org.jasig.cas:cas-server-support-generic:3.4.2'
//        compile 'org.jasig.cas:cas-server-core:3.4.2'
//        compile 'org.jasig.cas:cas-server-support-jdbc:3.4.2'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
//        runtime ":jquery:1.7.1"
        runtime ":resources:1.2.1"

        runtime ":ala-web-theme:0.8.1"
        compile ":csv:0.3.1"
//        test ":spock:0.7"
        build ":tomcat:$grailsVersion"

        compile ":lesscss-resources:1.3.3"
        compile ":mail:1.0.4"
        compile ":oauth:2.1.0"
        compile ":simple-captcha:0.9.9"
        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}
