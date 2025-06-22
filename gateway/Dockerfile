FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/*.jar
RUN mkdir -p /app/certs
COPY src/main/resources/localhost.p12 /app/certs/
COPY src/main/resources/truststore.p12 /app/certs/
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
