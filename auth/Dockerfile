FROM openjdk:13 AS oauth-server

ADD ./target/authorization-server.jar /app/

CMD ["java", "-jar", "/app/authorization-server.jar"]

#EXPOSE 8888