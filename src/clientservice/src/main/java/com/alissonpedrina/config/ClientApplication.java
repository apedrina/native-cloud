package com.alissonpedrina.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@ComponentScan("com.alissonpedrina")
@SpringBootApplication
public class ClientApplication {

	public static void main(String... args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}
