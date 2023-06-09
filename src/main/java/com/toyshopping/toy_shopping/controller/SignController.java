package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.SignInResultDto;
import com.toyshopping.toy_shopping.data.dto.SignUpResultDto;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.UserRepository;
import com.toyshopping.toy_shopping.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(
            @ApiParam(value = "ID", required = true) @RequestParam String id,
            @ApiParam(value = "Password", required = true) @RequestParam String password,
            HttpServletResponse response) throws RuntimeException {
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", id);
        SignInResultDto signInResultDto = signService.signIn(id, password);
        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[signIn] 정상적으로 로그인이 되었습니다. id : {}, token : {}", id, signInResultDto.getToken());
        }

        response.setHeader("X-AUTH-TOKEN", signInResultDto.getToken());
        return signInResultDto;
    }

    @PostMapping(value = "/sign-up")
    public ModelAndView signUp(
            @ApiParam(value = "ID", required = true) @RequestParam("email") String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam("password") String password,
            @ApiParam(value = "이름", required = true) @RequestParam("name") String name,
            @ApiParam(value = "전화번호", required = true) @RequestParam("phone") String phone,
            @ApiParam(value = "권한", required = true) @RequestParam("auth") String role) {
        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", id, name, role);
        SignUpResultDto signUpResultDto = signService.signUp(id, password, name, phone, role);

        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", id);
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }


    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @GetMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("register");
        return mav;
    }

    @GetMapping(value = "/login")
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }


}
