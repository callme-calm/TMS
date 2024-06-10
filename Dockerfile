# FROM maven:3.8.5-openjdk-17 AS build
# COPY . .
# RUN mvn clean package -DskipTests
#
# FROM openjdk:17.0.1-jdk-slim
# COPY --from=build target/tms-0.0.1-SNAPSHOT.jar tms.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","tms.jar"]


# Use Maven to build the project
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Use JDK to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/tms-0.0.1-SNAPSHOT.jar tms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tms.jar"]
