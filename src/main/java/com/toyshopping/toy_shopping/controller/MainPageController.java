package com.toyshopping.toy_shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping()
public class MainPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(MainPageController.class);
    @GetMapping(value = "/main")
    public ModelAndView mainPage() {
        LOGGER.info("[mainPageGetMapping] mainpageController get 메서드 호출됨.");
        ModelAndView mav = new ModelAndView("mainPage");

        return mav;
    }
    @GetMapping(value = "/getProductPage")
    public ModelAndView productPage(){
        LOGGER.info("[getProductGetMapping] getProductPage 메서드 호출됨.");
        ModelAndView mav = new ModelAndView("productPage");
        return mav;
    }

}
