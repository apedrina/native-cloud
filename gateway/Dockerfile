#docker build -t admin .
FROM openjdk:13 AS gateway

ADD ./target/gateway.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app/gateway.jar"]

#EXPOSE 8080