# Använd OpenJDK 17-bild som bas
FROM openjdk:21-jdk-slim as build

# Installera Maven för att bygga applikationen
RUN apt-get update && apt-get install -y maven

# Ställ in arbetskatalogen
WORKDIR /workspace

# Kopiera över projektfilerna
COPY . .

# Bygg projektet (kompilerar och skapar ett fat JAR- eller native-image)
RUN mvn clean package -DskipTests

# Nu skapar vi den slutliga bilden för produktion
FROM openjdk:21

# Ställ in arbetskatalogen
WORKDIR /app

# Kopiera över den byggda applikationen från build-steget
COPY --from=build /workspace/target/*-runner.jar /app/quarkus-run.jar

# Exponera porten som applikationen lyssnar på
EXPOSE 8083

# Sätt JAVA_OPTS för att köra applikationen
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0"

# Kör applikationen
ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]
