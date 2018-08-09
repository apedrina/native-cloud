#docker build -t admin .
FROM java:8-jre AS admin

ADD ./target/admin.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-Xmx200m", "-jar", "/app/admin.jar"]

EXPOSE 1111