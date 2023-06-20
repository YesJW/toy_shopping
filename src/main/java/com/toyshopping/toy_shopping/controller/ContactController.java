package com.toyshopping.toy_shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ContactController {

    private static Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @GetMapping(value = "/contactPage")
    public ModelAndView getContactPage() {
        LOGGER.info("[ContactController] getContactPage 호출");
        ModelAndView mav = new ModelAndView("contactPage");
        return mav;
    }

}
