# BUILD
FROM gradle:8.7-jdk21 AS build
WORKDIR /app
# Copiar proyecto
COPY . .
# Permisos
RUN chmod +x gradlew
# Compilar
RUN ./gradlew clean build -x test --no-daemon
#  RUN
FROM eclipse-temurin:21-jdk
WORKDIR /app
# Copiar jar generado
COPY --from=build /app/build/libs/*.jar app.jar
# Puerto Spring Boot
EXPOSE 8080
# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]