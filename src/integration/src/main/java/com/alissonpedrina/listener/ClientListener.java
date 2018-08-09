package com.alissonpedrina.listener;

import com.alissonpedrina.model.Client;
import com.alissonpedrina.repository.ClientRepository;
import com.alissonpedrina.rest.bean.ClientJson;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClientListener {

  private Logger logger = LoggerFactory.getLogger(ClientListener.class);

  @Autowired
  private ClientRepository clientRepository;

  @Transactional
  @RabbitListener(queues = "${client.queue.name}")
  public void receiveMessage(String client) {
    try {
      Gson gson = new Gson();
      ClientJson cl = gson.fromJson(client, ClientJson.class);

      Client c = clientRepository.getClientById(cl.getId());

      if (c != null) {

        clientRepository.update(c);

        logger.info("Client updated");

      } else {

        Client ce = new Client();

        ce.setId(cl.getId());
        ce.setEmail(cl.getEmail());
        ce.setName(cl.getName());

        clientRepository.create(ce);

        logger.info("Client add");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
