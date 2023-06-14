package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.service.FindIdPwServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FindIdPwController {

    private FindIdPwServiceImpl findIdPwService;
    private final Logger LOGGER = LoggerFactory.getLogger(FindIdPwController.class);

    public FindIdPwController(FindIdPwServiceImpl findIdPwService) {
        this.findIdPwService = findIdPwService;
    }

    @GetMapping("/findId")
    public String findId(@RequestParam String name, @RequestParam String phone) {
        LOGGER.info("[findId] findId Get메서드 호출됨.");
        String id = findIdPwService.findUserId(name, phone);
        return id;
    }

    @GetMapping("/getFindIdPage")
    public ModelAndView findIdPage() {
        LOGGER.info("[getFindIdPageMapping] getFindIdPage 메서드 호출됨.");
        ModelAndView mav = new ModelAndView("findId");
        return mav;
    }
}
