FROM openjdk:21-jdk

ARG WAR_FILE=target/iam-service.war

COPY ${WAR_FILE} iam-service.war

ENTRYPOINT ["java", "-jar", "/iam-service.war"]

EXPOSE 9001