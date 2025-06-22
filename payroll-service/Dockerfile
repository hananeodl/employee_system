FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/*.jar
# Créer le dossier pour les certificats
RUN mkdir -p /app/certs

# Copier les certificats depuis src/main/resources
COPY src/main/resources/localhost.p12 /app/certs/
COPY src/main/resources/truststore.p12 /app/certs/
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
