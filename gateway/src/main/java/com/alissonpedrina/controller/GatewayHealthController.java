package com.alissonpedrina.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j

@Controller
public class GatewayHealthController {

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    public String home() {
        return "index";
    }

}
