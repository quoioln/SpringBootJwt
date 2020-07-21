package com.vpquoi.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello world!!!!!!!!");
    }
}
