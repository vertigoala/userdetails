#!/bin/sh

echo "USERDETAILS ENTRYPOINT: Waiting for MySQL database..." # TODO: change hard coded name
/opt/wait-for-it.sh -h "mysqldbcas" -p 3306 -t 0 --strict -- echo "USERDETAILS ENTRYPOINT: MySQL database is up"

# run groovy script
if [ "$CREATE_ADMIN" == "true" ]; then
    java -classpath "/usr/local/tomcat/webapps/userdetails/WEB-INF/lib/groovy-all-2.4.17.jar" \
    -Dgroovy.starter.conf=/opt/groovy-starter.conf \
    -Dgroovy.home=/usr/local/tomcat/webapps/userdetails/WEB-INF \
    org.codehaus.groovy.tools.GroovyStarter --main groovy.ui.GroovyMain \
    --conf /opt/groovy-starter.conf \
    -classpath /usr/local/tomcat/webapps/userdetails/WEB-INF/lib/mysql-connector-java-5.1.42.jar \
    /opt/create-admins.groovy
fi

exec "$@"
