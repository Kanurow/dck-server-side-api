#FROM maven:3.8.5-openjdk-17 AS build
#COPY . .
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17.0.1-jdk-slim
#COPY --from=build /target/dck-0.0.1-SNAPSHOT.jar dck.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "dck.jar"]
#

#Using eclipse-temurin img
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /dck
COPY . .
RUN ./mvnw package

FROM eclipse-temurin:17-jdk-alpine AS runner

WORKDIR /dck
COPY --from=builder /dck/target/*.jar dck.jar

CMD ["java", "-jar", "dck.jar"]