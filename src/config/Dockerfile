FROM java:8-jre AS config

ADD ./target/config.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/config.jar"]

EXPOSE 8888