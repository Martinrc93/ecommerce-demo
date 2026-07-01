FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY src src

RUN chmod +x ./mvnw && ./mvnw -B -DskipTests package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=build /workspace/target/*.jar /app/app.jar

RUN chown spring:spring /app/app.jar

USER spring

EXPOSE 10000
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 CMD ["sh", "-c", "wget -qO- http://127.0.0.1:${PORT:-10000}/actuator/health | grep -q '\"status\":\"UP\"' || exit 1"]
CMD ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar /app/app.jar"]
