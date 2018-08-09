#docker build -t admin .
FROM java:8-jre AS gateway

ADD ./target/gateway.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-Xmx200m", "-jar", "/app/gateway.jar"]

EXPOSE 8080