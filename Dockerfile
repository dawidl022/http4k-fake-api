FROM gradle:7-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle shadowJar --no-daemon


FROM openjdk:11

EXPOSE 8080:8080

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/http4k-fake-api.jar

RUN mkdir /data
COPY src/main/kotlin/io/github/dawidl022/http4kfakeapi/data /data

ENTRYPOINT ["java","-jar","/app/http4k-fake-api.jar"]
