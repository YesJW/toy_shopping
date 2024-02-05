package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.UsersDto;
import com.toyshopping.toy_shopping.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(MyPageController.class);
    private UserRepository userRepository;

    public MyPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "AuthenticationToken", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping()
    public String myPage() {

        return "/userDetail";
    }

    @GetMapping(value = "/sales")
    public String mySales() {
        return "/sales";
    }

    @GetMapping("/user_detail")
    public UsersDto getUser(Principal principal){
        LOGGER.info("[getUser] getUser 메서드 호출됨.");
        UsersDto userDto = new UsersDto();
        userDto.setUid(userRepository.getByUid(principal.getName()).getUid());
        userDto.setRoles(userRepository.getByUid(principal.getName()).getRoles());
        userDto.setName(userRepository.getByUid(principal.getName()).getName());
        return userDto;
    }
}
