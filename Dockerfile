FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app.war
ENTRYPOINT ["java","-Dspring.profiles.active=default","-jar","/app.war"]