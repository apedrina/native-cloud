# Akumos Native-Cloud

Here we've a simple example of a Native-Cloud solution implemented using Spring-boot NetFlix and Spring-boot Cloud.<br>
This project is used on Akumo's Platform Tutorial, see here.
 
# Features

- API Gateway
- Service Registry
- Config Server
- Circuit Break
- Microservice's log solution
- Log centralize analyse 
- Monitoring services 
- Containerized (docker)
- Oauth

## Running and test

We recommend you follow the order of execution as it is presented bellow;
<p>
All commands should be execute from <i><project-root-directory></i>
</p>


### Config


````bash
cd /config
mvn clean install -DskipTests
java -jar ./target/config.jar
````
### Registry

````bash
cd /registry
mvn clean install -DskipTests
java -jar -Dspring.profiles.active=dev ./target/registry.jar
````
### Gateway

````bash
cd /gateway
mvn clean install -DskipTests
java -jar -Dspring.profiles.active=dev ./target/gateway.jar
````

### Auth 

````bash
cd /auth
mvn clean install -DskipTests
java -jar -Dspring.profiles.active=dev ./target/authorization-server.jar
````
### Customer mock

````bash
cd /customer
mvn clean install -DskipTests
java -jar -Dspring.profiles.active=dev ./target/customer.jar
````

### Testing with CURL

Getting the token:

````bash
curl -X POST -d '{"username":"javainuse","password":"password"}' http://localhost:8089/api/auth/token --header "Content-Type:application/json"
````

Mocking the get customers:

````bash
curl -X GET http://localhost:8089/api/customer/v1 -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE1OTE3NDc2MDUsImlhdCI6MTU5MTcyOTYwNX0.I_-1UMIA57hBp_dxfQeY3o1OeZARnvum30lg_SY4lkvYC7t-wAYNRg3MtSAWWPb5dnvuBZ1DLkuzbM4r2s0g4g"
````
