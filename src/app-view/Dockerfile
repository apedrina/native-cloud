#docker build -t admin .
FROM java:8-jre AS app

ADD ./target/app-view.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-Xmx200m", "-jar", "/app/app-view.jar"]

EXPOSE 8081