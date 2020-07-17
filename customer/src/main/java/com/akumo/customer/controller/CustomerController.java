package com.akumo.customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1")
public class CustomerController {

    @PostMapping
    public ResponseEntity<String> add(@RequestBody String body) {
        System.out.println("adding");
        System.out.println(body);
        return ResponseEntity.ok("mock... added Customer");
    }

    @GetMapping
    public ResponseEntity<String> get() {
        System.out.println("getting");
        return ResponseEntity.ok("mock... getting Customers\nName: Ihub");
    }

}
