FROM openjdk:27-ea-oracle
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "app.jar"]
