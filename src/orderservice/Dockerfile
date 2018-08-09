#docker build -t admin .
FROM java:8-jre AS orderservice

ADD ./target/orderservice.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-Xmx200m", "-jar", "/app/orderservice.jar"]

EXPOSE 8083