# Etapa 1: usa a imagem do JDK para rodar o app
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o .jar gerado do projeto para dentro do container
COPY target/*.jar app.jar

# Expõe a porta que o Spring Boot vai usar (padrão é 8080)
EXPOSE 8080

# Comando para iniciar o app
ENTRYPOINT ["java", "-jar", "app.jar"]
