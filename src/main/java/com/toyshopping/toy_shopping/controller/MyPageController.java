package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(MyPageController.class);
    private UserRepository userRepository;

    public MyPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping()
    public ModelAndView myPage() {
        ModelAndView mav = new ModelAndView("userDetail");
        LOGGER.info("[mypageGetMethod] mypage get 메서드 호출됨.");
        return mav;
    }

    @GetMapping(value = "/sales")
    public ModelAndView mySales() {
        ModelAndView mav = new ModelAndView("sales");
        LOGGER.info("[mySalesGetMethod] mySalesget 메서드 호출됨.");
        return mav;
    }

    @GetMapping("/user_detail")
    public UserDto getUser(Principal principal){
        LOGGER.info("[getUser] getUser 메서드 호출됨.");
        UserDto userDto = new UserDto();
        userDto.setUserId(userRepository.getByUid(principal.getName()).getUid());
        userDto.setRoles(userRepository.getByUid(principal.getName()).getRoles());
        userDto.setUserName(userRepository.getByUid(principal.getName()).getName());
        return userDto;
    }
}
