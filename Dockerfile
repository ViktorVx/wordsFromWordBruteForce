FROM maven:3.8.1-openjdk-11-slim
COPY pom.xml /tmp/
COPY /src /tmp/src/
WORKDIR /tmp/
RUN mvn clean package

FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.6_10
COPY --from=0 /tmp/target/WordsFromWordBruteForce-0.0.1-SNAPSHOT-spring-boot.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080