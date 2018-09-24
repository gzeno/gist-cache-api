FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/gistCacheApi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo:27017/gistCacheApi","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
