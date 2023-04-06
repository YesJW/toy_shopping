package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.entity.User;
import org.dom4j.rule.Mode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserDetailController {
    @GetMapping("/user-detail")
    @ResponseBody
    private String getUserDetail(Principal principal) {
        return principal.getName();
    }
}
