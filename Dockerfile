#
# userdetails dockerized
#
FROM tomcat:8.5-jre8-alpine

RUN mkdir -p /data/userdetails/config

ARG ARTIFACT_URL=https://nexus.ala.org.au/service/local/repositories/releases/content/au/org/ala/userdetails/2.2.2/userdetails-2.2.2.war
ARG WAR_NAME=userdetails

ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR.UTF-8
ENV LC_ALL pt_BR.UTF-8

#RUN apk add --update fontconfig ttf-dejavu tini zip
RUN wget $ARTIFACT_URL -q -O /tmp/$WAR_NAME && \
    apk add --update tini && \
    mkdir -p $CATALINA_HOME/webapps/$WAR_NAME && \
    unzip /tmp/$WAR_NAME -d $CATALINA_HOME/webapps/$WAR_NAME && \
    rm /tmp/$WAR_NAME

EXPOSE 8080

# capcha needs those
RUN apk add --update fontconfig ttf-dejavu && \
    rm $CATALINA_HOME/webapps/$WAR_NAME/WEB-INF/classes/logback.groovy

# custom configs
COPY ./tomcat-conf/* /usr/local/tomcat/conf/
COPY ./config/* /data/userdetails/config/
COPY ./scripts/* /opt/

# NON-ROOT
RUN addgroup -g 101 tomcat && \
    adduser -G tomcat -u 101 -S tomcat && \
    chown -R tomcat:tomcat /usr/local/tomcat && \
    chown -R tomcat:tomcat /data && \
    chmod +x /opt/*.sh

USER tomcat

ENV CATALINA_OPTS='-Dgrails.env=production' \
    CREATE_ADMIN='true'

ENTRYPOINT ["tini", "--", "/opt/entrypoint.sh"]
CMD ["catalina.sh", "run"]
