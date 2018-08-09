#docker build -t admin .
FROM java:8-jre AS integration

ADD ./target/integration.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-Xmx200m", "-jar", "/app/integration.jar"]

EXPOSE 8999