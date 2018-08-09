# Native cloud application

Here you will find an example of a Native Cloud Application using: SpringCloud, SpringEureka, SpringZuul, SpringConfig, SpringBoot, MongoDB, Docker, etc.

# Architecture

![alt text](https://raw.githubusercontent.com/apedrina/example-cloud-app/master/doc/diagram.jpeg)

# How to

First run the /config module with the bellow command:

```bash
mvn spring-boot:run

```
This module is responsible for store and supply access to the config files to all other modules system.

The second module to run is /registry execute:

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```
Now we can access the eureka admin panel to do this access:

```
http://localhost:8761/
```
![alt text](https://raw.githubusercontent.com/apedrina/example-cloud-app/master/doc/eureka.png)

Ok, /admin module is the next module to start:

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```
![alt text](https://raw.githubusercontent.com/apedrina/example-cloud-app/master/doc/spring-admin.png)

There isn't obligate order to start the others modules. To run all of these other modules use the same param used above: 
``
-Dspring.profiles.active=dev. 
``

# Run with docker

To run applciation first use this command:

```bash
sudo docker-compose build
```
And after

```bash
sudo docker-compose up
```

# View

To access app-view:

```bash
http://localhost:8081/home
```

# Add order's and client's

To add order's and client's use the Swagger-ui endpoint's:

Ex:

localhost:8083/swagger-ui.html#!/order-rest/saveUsingPUT

localhost:8082/swagger-ui.html#!/client-rest/saveUsingPUT
