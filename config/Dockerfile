FROM openjdk:13 AS config

ADD ./target/config.jar /app/

CMD ["java", "-jar", "/app/config.jar"]

#EXPOSE 8888