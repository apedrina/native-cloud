package com.alissonpedrina.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;

@Configuration
public class RabbitMQConfiguration {

  @Value("${spring.rabbitmq.host}")
  private String hostsName;

  @Value("${spring.rabbitmq.user}")
  private String user;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @Value("${client.queue.name}")
  private String queueName;

  @Autowired
  private Environment env;

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public ExponentialBackOffPolicy backOffPolicy() {
    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(env.getProperty("rabbitmq.backoffpolicy.interval.initial", Integer.class, 2000));
    backOffPolicy.setMaxInterval(env.getProperty("rabbitmq.backoffpolicy.interval.max", Integer.class, 60000));
    return backOffPolicy;
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostsName);
    connectionFactory.setUsername(user);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }

  @Bean
  public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);

    return container;
  }

}
