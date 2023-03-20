package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.entity.Login;
import com.toyshopping.toy_shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController{

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "exception", required = false) String exception) {
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "login";
    }
}
