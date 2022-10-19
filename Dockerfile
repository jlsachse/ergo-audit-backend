FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/

RUN ["mvn", "install", "-Dmaven.test.skip=true"]
FROM openjdk:11-slim

WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/ergoaudit-*.jar /app/ergoaudit.jar
ENTRYPOINT ["java", "-jar", "ergoaudit.jar"]
