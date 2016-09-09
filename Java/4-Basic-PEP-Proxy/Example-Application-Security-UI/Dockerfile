FROM tomcat:8-jre8

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/

# ENV ORIGINAL_WAR_FILE Example-Application-Security-UI-0.0.1-SNAPSHOT.war
ENV ORIGINAL_WAR_FILE Example-Application-Security-UI.war
ENV FINAL_WAR_FILE Example-Application-Security-UI.war

WORKDIR ~

ADD ./target/$ORIGINAL_WAR_FILE .
# RUN mv $ORIGINAL_WAR_FILE $FINAL_WAR_FILE

# Upload the application to Tomcat so it will run
RUN cp $FINAL_WAR_FILE /usr/local/tomcat/webapps