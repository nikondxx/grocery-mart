FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/grocery-mart-backend-0.0.1-SNAPSHOT.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]
