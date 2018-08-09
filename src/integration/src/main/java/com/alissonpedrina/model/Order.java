package com.alissonpedrina.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "tableorder")
public class Order implements Serializable {

  @Id
  public String id;

  @OneToOne
  public Client client;
  public String restaurantId;
  public Date createdAt;
  public Date confirmedAt;

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @OneToMany(cascade = CascadeType.ALL)
  public List<Item> items;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getConfirmedAt() {
    return confirmedAt;
  }

  public void setConfirmedAt(Date confirmedAt) {
    this.confirmedAt = confirmedAt;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }


}
