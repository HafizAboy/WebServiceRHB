FROM openjdk:8
ADD target/rhb-service-app-api.jar rhb-service-app-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "rhb-service-app-api.jar"]