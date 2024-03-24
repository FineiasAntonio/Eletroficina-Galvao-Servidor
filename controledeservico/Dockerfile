FROM amazoncorretto:17-alpine-jdk

RUN apk add --no-cache maven

WORKDIR /

COPY . .

RUN mvn clean install

CMD ["java", "-jar", "target/controledeservico-1.0.jar"]

EXPOSE 8080