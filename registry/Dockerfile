#docker build -t admin .
FROM openjdk:13 AS registry

ADD ./target/registry.jar /app/
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app/registry.jar"]

#EXPOSE 8761