# 1. Använd en Java-bild som bas för byggfasen (Java 21)
FROM openjdk:21-jdk-slim AS build

# 2. Installera Maven
RUN apt-get update && apt-get install -y maven

# 3. Sätt arbetskatalogen
WORKDIR /app

# 4. Kopiera pom.xml och ladda ner beroenden
COPY pom.xml .
RUN mvn dependency:go-offline

# 5. Kopiera källkoden och bygg applikationen
COPY src ./src
RUN mvn clean package -DskipTests

# 6. Skapa en ny bild för körfasen med samma Java-version
FROM openjdk:21-jdk-slim

# 7. Sätt arbetskatalogen för körning
WORKDIR /app

# 8. Kopiera den byggda JAR-filen från byggfasen
COPY --from=build /app/target/Authentication-0.0.1-SNAPSHOT.jar app.jar

# 9. Exponera port 8080 för Spring Boot-applikationen
EXPOSE 8081

# 10. Vänta på att databasen ska vara tillgänglig och kör sedan Spring Boot-applikationen
CMD ["java", "-jar", "app.jar"]
