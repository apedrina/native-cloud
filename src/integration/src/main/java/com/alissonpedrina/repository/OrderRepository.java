package com.alissonpedrina.repository;

import com.alissonpedrina.model.Order;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class OrderRepository {

  Logger log = LoggerFactory.getLogger(OrderRepository.class.getName());

  @PersistenceContext
  private EntityManager em;

  public List<Order> search(String startDate, String endDate, String name, String phone, String eMail) {

    StringBuilder sql = new StringBuilder("select o from Order o where o.id != null ");

    boolean isName = false;
    boolean isDate = false;
    boolean isPhone = false;
    boolean isEmail = false;

    if ((!"".equals(startDate)) && startDate != null) {
      sql.append(" AND o.createdAt between :d1 AND :d2");
      isDate = true;
    }

    if ((!"".equals(name)) && name != null) {
      sql.append(" AND o.client.name like :nameParam");
      isName = true;
    }

    if ((!"".equals(eMail)) && eMail != null) {
      sql.append(" AND o.client.email like :eMailParam");
      isEmail = true;
    }

    if ((!"".equals(phone)) && phone != null) {
      sql.append(" AND o.client.phone like :phoneParam");
      isPhone = true;
    }

    Query q = em.createQuery(sql.toString());
    if (isName) {
      q.setParameter("nameParam", name);
    }
    if (isPhone) {
      q.setParameter("phoneParam", phone);
    }
    if (isEmail) {
      q.setParameter("eMailParam", eMail);
    }
    if (isDate) {
      q.setParameter("d1", validateDate(startDate));
      q.setParameter("d2", validateDate(endDate));
    }

    return q.getResultList();
  }

  private Date validateDate(String startDate) {
    String date = "";
    SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
    try {
      return sdf.parse(startDate);
    } catch (ParseException pe) {
      log.debug("StartDate inválid {}", startDate);
    }
    return null;
  }


  public void create(Order order) {
    em.persist(order);
  }


  public void update(Order order) {
    em.merge(order);
  }


  public Order getOrderById(long id) {
    return em.find(Order.class, id);
  }


  public void delete(long id) {
    Order order = getOrderById(id);
    if (order != null) {
      em.remove(order);
    }
  }

}
