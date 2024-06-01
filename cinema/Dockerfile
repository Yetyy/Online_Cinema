
FROM openjdk:21-jdk-slim

ENV APP_HOME /app

RUN mkdir $APP_HOME
WORKDIR $APP_HOME

COPY ./src/main/resources/application.properties /app/src/main/resources/
COPY target/cinema-0.0.1-SNAPSHOT.jar $APP_HOME/cinema.jar

CMD ["java", "-jar", "cinema.jar"]