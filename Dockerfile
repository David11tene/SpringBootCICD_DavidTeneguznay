# ===== ETAPA 1: BUILD =====
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Dar permisos al wrapper
RUN chmod +x gradlew

# Compilar proyecto
RUN ./gradlew build -x test

# ===== ETAPA 2: RUN =====
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiar el jar generado
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto usado por Spring Boot
EXPOSE 8080

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]