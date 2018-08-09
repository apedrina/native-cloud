package com.alissonpedrina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@ComponentScan(basePackages = "com.alissonpedrina")
@SpringBootApplication
public class IntegrationApplication {

  public static void main(String[] args) {
    SpringApplication.run(IntegrationApplication.class, args);
  }

}
