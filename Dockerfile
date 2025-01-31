# Usar una imagen base de OpenJDK con Java 17
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado en el contenedor
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Exponer el puerto 8080 (el puerto del API Gateway)
EXPOSE 8080

# Configurar el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
