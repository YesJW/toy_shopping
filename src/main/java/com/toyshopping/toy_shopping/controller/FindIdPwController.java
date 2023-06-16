package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.service.FindIdPwServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/getPwPage")
    public ModelAndView findPwPage() {
        LOGGER.info("[getPwPage] getPwPage 메서드 호출됨.");
        ModelAndView mav = new ModelAndView("passwordPage");
        return mav;
    }

    @GetMapping("/getResetPwPage")
    public ModelAndView getResetPwPage() {
        LOGGER.info("[getResetPwPage] getResetPwPage 메서드 호출됨.");
        ModelAndView mav = new ModelAndView("resetPassword");
        return mav;
    }

    @GetMapping("/search_user_pw")
    public ResponseEntity<UserDto> findIdForReset(@RequestParam String name, @RequestParam String id, @RequestParam String phone) {
        UserDto userDto = findIdPwService.search_User_Pw(id, name, phone);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("/reset_password")
    public ResponseEntity<UserDto> resetPW(@RequestParam String id, @RequestParam String phone, @RequestParam String password) {
        UserDto userDto = findIdPwService.changeUserPw(id, phone, password);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
