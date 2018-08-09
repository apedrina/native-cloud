package com.alissonpedrina.order;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, ObjectId> {

	Collection<Order> findByClientId(UUID clientId);
	
	Collection<Order> findByRestaurantId(UUID restaurantId);
	
	Collection<Order> findByCreatedAtBetween(Date start, Date end);

}