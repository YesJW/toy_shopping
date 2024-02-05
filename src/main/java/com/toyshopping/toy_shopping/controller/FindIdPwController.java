package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.UsersDto;
import com.toyshopping.toy_shopping.service.FindIdPwServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FindIdPwController {

    private FindIdPwServiceImpl findIdPwService;
    private final Logger LOGGER = LoggerFactory.getLogger(FindIdPwController.class);

    public FindIdPwController(FindIdPwServiceImpl findIdPwService) {
        this.findIdPwService = findIdPwService;
    }

    @GetMapping("/findId")
    @ResponseBody
    public String findId(@RequestParam String name, @RequestParam String phone) {
        LOGGER.info("[findId] findId Get메서드 호출됨.");
        String id = findIdPwService.findUserId(name, phone);
        return id;
    }

    @GetMapping("/getFindIdPage")
    public String findIdPage() {
        LOGGER.info("[getFindIdPageMapping] getFindIdPage 메서드 호출됨.");
        return "/findId";
    }

    @GetMapping("/getPwPage")
    public String findPwPage() {
        LOGGER.info("[getPwPage] getPwPage 메서드 호출됨.");
        return "/passwordPage";
    }

    @GetMapping("/getResetPwPage")
    public String getResetPwPage() {
        return "/resetPassword";
    }

    @GetMapping("/search_user_pw")
    @ResponseBody
    public ResponseEntity<UsersDto> findIdForReset(@RequestParam String name, @RequestParam String id, @RequestParam String phone) {
        UsersDto userDto = findIdPwService.search_User_Pw(id, name, phone);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("/reset_password")
    @ResponseBody
    public ResponseEntity<UsersDto> resetPW(@RequestParam String id, @RequestParam String phone, @RequestParam String password) {
        UsersDto userDto = findIdPwService.changeUserPw(id, phone, password);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
