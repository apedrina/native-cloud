package com.alissonpedrina.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order implements Serializable {

  public String id;
  public UUID clientId;
  public UUID restaurantId;
  public Date createdAt;
  public Date confirmedAt;
  public List<Item> items;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UUID getClientId() {
    return clientId;
  }

  public void setClientId(UUID clientId) {
    this.clientId = clientId;
  }

  public UUID getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(UUID restaurantId) {
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

  public static class Item {

    public String id;
    public String description;
    public Integer quantity;
    public Double price;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }

    public Double getPrice() {
      return price;
    }

    public void setPrice(Double price) {
      this.price = price;
    }
  }
}