#docker build -t admin .
FROM java:8-jre AS registry

ADD ./target/registry.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-Xmx200m", "-jar", "/app/registry.jar"]

EXPOSE 8761