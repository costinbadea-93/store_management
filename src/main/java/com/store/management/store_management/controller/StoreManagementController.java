package com.store.management.store_management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreManagementController {


    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
