package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.config.security.JwtTokenProvider;
import com.toyshopping.toy_shopping.config.security.SecurityUtil;
import com.toyshopping.toy_shopping.data.dto.*;
import com.toyshopping.toy_shopping.service.SignService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sign-api")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);

    @Autowired
    private SignService signService;

    @PostMapping(value = "/sign-in")
    @ResponseBody
    public ResponseEntity<JwtTokenDto> signIn(@RequestBody SignInDto signInDto){
        String uid = signInDto.getUid();
        String password = signInDto.getPassword();
        JwtTokenDto jwtTokenDto = signService.signIn(uid, password);
        LOGGER.info("request username = {}, password = {}", uid, password);
        LOGGER.info("jwtToken accessToken = {}, refreshToken = {}", jwtTokenDto.getAccessToken(), jwtTokenDto.getRefreshToken());

        return ResponseEntity.ok().body(jwtTokenDto);

    }


    @PostMapping(value = "/sign-up")
    @ResponseBody
    public ResponseEntity<UsersDto> signUp(@RequestBody @Valid SignUpDto signUpDto) {

        UsersDto usersDto = signService.signUp(signUpDto);
        return ResponseEntity.ok(usersDto);
    }


    @GetMapping(value = "/exception")
    @ResponseBody
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
        map.put("message", "로그인 실패");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @GetMapping(value = "/register")
    public String register(HttpServletRequest request, Model model) {
        model.addAttribute("token", "dasdsa");
        return "/register";
    }

    @GetMapping(value = "/login")
    public String login(HttpServletRequest request, Model model){
        model.addAttribute("token", "dasdsa");
        return "/login";
    }


}
