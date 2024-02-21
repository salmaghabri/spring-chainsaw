FROM openjdk:17-alpine

WORKDIR /app

COPY target/CAT.jar /app

EXPOSE 8080
# entrypoint khir
ENTRYPOINT ["java", "-jar", "CAT.jar"]