package com.alissonpedrina.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class OrderQueueConfig {

  @Value("${order.queue.name}")
  private String queueName;

  @Value("${order.exchange.name}")
  private String exchangeName;

  @Autowired
  private Environment env;

  @Bean(name = "orderQueue")
  public Queue newOrderQueue() {
    return new Queue(queueName);
  }

  @Bean(name = "orderExchange")
  public DirectExchange exchange() {
    return new DirectExchange(exchangeName);
  }

  @Bean
  Binding binding(@Qualifier("orderQueue") Queue queue,
                  @Qualifier("orderExchange") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(queueName);
  }

  @Bean
  public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);

    return container;
  }

  @Bean(name = "orderTemplate")
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setRoutingKey(queueName);
    template.setQueue(queueName);
    return template;
  }

  @Bean(name = "orderRetryInterceptor")
  public RetryOperationsInterceptor retryInterceptor(BackOffPolicy backOffPolicy,
                                                     @Qualifier("orderTemplate") RabbitTemplate rabbitTemplate) {
    return org.springframework.amqp.rabbit.config.RetryInterceptorBuilder.stateless()
        .backOffPolicy(backOffPolicy)
        .recoverer(new RepublishMessageRecoverer(rabbitTemplate, exchangeName, ".*"))
        .build();
  }


  @Bean(name = "orderContainerFactory")
  public SimpleRabbitListenerContainerFactory adapterOPListenerContainerFactory(
      ConnectionFactory connectionFactory,
      @Qualifier("orderRetryInterceptor") RetryOperationsInterceptor interceptor) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setAdviceChain(interceptor);
    factory.setConcurrentConsumers(env.getProperty("queue.consumers.min", Integer.class, 1));
    factory.setMaxConcurrentConsumers(env.getProperty("queue.consumers.max", Integer.class, 1));
    return factory;
  }

}
