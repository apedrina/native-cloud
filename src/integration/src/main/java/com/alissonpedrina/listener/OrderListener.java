package com.alissonpedrina.listener;


import com.alissonpedrina.repository.OrderRepository;
import com.google.gson.Gson;
import com.alissonpedrina.model.Client;
import com.alissonpedrina.model.Item;
import com.alissonpedrina.model.Order;
import com.alissonpedrina.repository.ClientRepository;
import com.alissonpedrina.rest.bean.OrderJson;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderListener {

  private Logger logger = LoggerFactory.getLogger(OrderListener.class);

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Transactional
  @RabbitListener(queues = "${order.queue.name}")
  public void receiveMessage(String order) {

    try {
      Gson gson = new Gson();
      OrderJson os = gson.fromJson(order, OrderJson.class);

      Client c = clientRepository.getClientById(os.getClientId());

      if (c == null) {

        logger.info("Error on add Order, Client not exist");

      } else {
        Order oe = new Order();
        oe.setId(os.getId());
        oe.setConfirmedAt(os.getConfirmedAt());
        oe.setClient(c);

        List<Item> itens = new ArrayList<>();

        for (OrderJson.Item item : os.getItems()) {
          Item i = new Item();
          i.set_id(item.getId());
          i.setDescription(item.getDescription());
          i.setPrice(item.getPrice());
          i.setQuantity(item.getQuantity());
          itens.add(i);
        }
        oe.setItems(itens);
        oe.setCreatedAt(os.getCreatedAt());

        orderRepository.create(oe);

        logger.info("Order add");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}