# базовый образ, содержащий Java 17
FROM openjdk:17-oracle

# Директория приложения внутри контейнера
WORKDIR /app

# Копирование JAR-файла приложения в контейнер
COPY /target/TestTaskNotes.jar app.jar
#
## Определение переменной среды
ENV SOCKET_SERVER_HOST=localhost
ENV SOCKET_SERVER_PORT=8080
#
## Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]