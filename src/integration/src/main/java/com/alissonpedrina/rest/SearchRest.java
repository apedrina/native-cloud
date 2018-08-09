package com.alissonpedrina.rest;

import com.alissonpedrina.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.alissonpedrina.model.Order;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping//(value = "/v1/search/")
public class SearchRest {

  Logger log = LoggerFactory.getLogger(SearchRest.class.getName());

  @Autowired
  private OrderRepository service;

  @CrossOrigin
  @ResponseBody
  @GetMapping(value = "/orders", produces = "application/json")
  public List<Order> search(
      @RequestParam String startDate,
      @RequestParam String endDate,
      @RequestParam String phone,
      @RequestParam String eMail,
      @RequestParam String clientName
  ) throws JsonProcessingException {

    return service.search(startDate, endDate, clientName, phone, eMail);

  }


}
