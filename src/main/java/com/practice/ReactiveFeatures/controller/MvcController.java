package com.practice.ReactiveFeatures.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MvcController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello..";
    }

    @GetMapping("/date")
    public String getCurrentDate() {
        return java.time.LocalDateTime.now().toString();
    }
}
