package com.alissonpedrina.rest;

import com.alissonpedrina.order.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.alissonpedrina.order.Order;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/v1/order/")
public class OrderRest {

  Logger log = LoggerFactory.getLogger(OrderRest.class.getName());

  @Autowired
  @Qualifier("orderTemplate")
  private RabbitTemplate rabbitTemplate;

  @Value(value = "${client.url}")
  private String urlClient;

  @Autowired
  private OrderRepository orderRepository;

  @ResponseBody
  @GetMapping(value = "/", produces = "application/json")
  public List<Order> search(
      @RequestParam String startDate,
      @RequestParam String endDate
  ) throws JsonProcessingException {

    List<Order> orders = (ArrayList<Order>) orderRepository.findAll();

    return orders;
  }

  @PutMapping(value = "/", produces = "application/json")
  public void save(@RequestBody Order order) {
    orderRepository.save(order);
    Gson gson = new Gson();
    rabbitTemplate.convertAndSend(gson.toJson(order));

  }


}
