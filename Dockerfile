FROM maven:3.8-openjdk-17 as maven
LABEL authors="ensaremirerol"
WORKDIR /app
COPY ./pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn package -DskipTests=true


FROM ibm-semeru-runtimes:open-17-jre-jammy
ARG DEPENDENCY=/app/target/
COPY --from=maven ${DEPENDENCY}shacl_api.jar /app/app.jar
CMD java -jar /app/app.jar

EXPOSE 8080
