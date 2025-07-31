# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo .jar generado desde el directorio target al contenedor
COPY target/ms-catalogo-*.jar /app/app.jar

# Expone el puerto que usa Eureka (por defecto 8761)
EXPOSE 8085

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]