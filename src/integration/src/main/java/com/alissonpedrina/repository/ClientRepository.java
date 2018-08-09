package com.alissonpedrina.repository;


import com.alissonpedrina.model.Client;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ClientRepository {

  @PersistenceContext
  private EntityManager em;


  public void create(Client client) {
    em.persist(client);
  }

  public void update(Client client) {
    em.merge(client);
  }


  public Client getClientById(String id) {
    return em.find(Client.class, id);
  }


  public void delete(String id) {
    Client client = getClientById(id);
    if (client != null) {
      em.remove(client);
    }
  }

}
