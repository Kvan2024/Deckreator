FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package

CMD ["java", "-jar", "target/module-03-final-project-1.0.jar"]