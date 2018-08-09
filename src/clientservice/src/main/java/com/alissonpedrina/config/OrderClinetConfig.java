package com.alissonpedrina.config;

import org.springframework.context.annotation.Bean;

import com.alissonpedrina.client.OrderClient;
import com.alissonpedrina.filter.BasicAuthRequestInterceptor;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

public class OrderClinetConfig {

	@Bean
	public OrderClient config(BasicAuthRequestInterceptor requestInteceptor) {
		OrderClient orderClient = Feign.builder().client(new OkHttpClient())
				.encoder(new GsonEncoder())
				.decoder(new GsonDecoder())
				.logger(new Slf4jLogger(OrderClient.class))
				.logLevel(Logger.Level.FULL)
				.requestInterceptor(requestInteceptor)
				.target(OrderClient.class, "http://localhost:8081/api/books");
		
		return orderClient;
	}

}
