# Använd en Java-bild som bas
FROM openjdk:22-jdk-slim

# Sätt arbetskatalogen
WORKDIR /app

# Kopiera den färdiga JAR-filen till containern
COPY target/Backend-0.0.1-SNAPSHOT.jar app.jar

# Kopiera wait-for-it.sh för att vänta på databasen
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Exponera port 8080 för Spring Boot-applikationen
EXPOSE 8080

# Vänta på att databasen ska vara tillgänglig, och kör sedan Spring Boot-applikationen
CMD ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]
