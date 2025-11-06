FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM tomcat:latest
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
ENTRYPOINT [ "catalina.sh","run"]