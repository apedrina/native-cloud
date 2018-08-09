package com.alissonpedrina.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {

  Logger log = LoggerFactory.getLogger(AppController.class.getName());
/*
  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public String home(Principal principal, Model model) {
    model.addAttribute("serverTime", new SimpleDateFormat().format(new Date()));
    model.addAttribute("username", principal.getName());

    return "index";
  }
  */

  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public String home(Model model) {
    model.addAttribute("serverTime", new SimpleDateFormat().format(new Date()));

    return "index";
  }

}
