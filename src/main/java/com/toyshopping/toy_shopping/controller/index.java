package com.toyshopping.toy_shopping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class index {
    @RequestMapping("/index")
    public String hello(){
        return "Hello";
    }


}
