
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /dck
COPY . .
RUN ./mvnw package

FROM eclipse-temurin:17-jdk-alpine AS runner

WORKDIR /dck
COPY --from=builder /dck/target/*.jar dck.jar

CMD ["java", "-jar", "dck.jar"]