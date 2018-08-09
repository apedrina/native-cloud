#docker build -t admin .
FROM java:8-jre AS clientservice

ADD ./target/clientservice.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-Xmx200m", "-jar", "/app/clientservice.jar"]

EXPOSE 8082