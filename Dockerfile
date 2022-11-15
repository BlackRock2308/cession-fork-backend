FROM maven:3.8-jdk-11 as builder
WORKDIR /opt/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:11
WORKDIR /app
COPY --from=builder /opt/app/target/wootoo-back.jar /app/wootoo-back.jar
ENTRYPOINT ["java", "-jar", "wootoo-back.jar"]