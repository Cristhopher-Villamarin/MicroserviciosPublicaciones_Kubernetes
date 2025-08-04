
# Usa una imagen base ligera de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR ejecutable desde el directorio target
COPY target/ms-api-gateway-0.0.1-SNAPSHOT.jar /app/app.jar

# Expone el puerto (8761 para Eureka, cámbialo según el microservicio)
EXPOSE 8088

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]