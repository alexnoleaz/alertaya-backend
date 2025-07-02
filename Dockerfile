FROM gradle:8.4-jdk21 AS builder
COPY . /home/app
WORKDIR /home/app

RUN gradle build -x test

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=builder /home/app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
