FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/localhost.p12 /app/certs/localhost.p12
COPY src/main/resources/truststore.p12 /app/certs/truststore.p12
ENTRYPOINT ["java","-jar","/app.jar"]
