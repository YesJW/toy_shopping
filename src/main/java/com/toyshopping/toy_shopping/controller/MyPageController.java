package com.toyshopping.toy_shopping.controller;

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

    @GetMapping("/user_detail")
    /*public Map<String, String> currentUserName(Principal principal) {
        LOGGER.info("[userDetailGetMethod] 메서드 호출됨 : {}", principal.getName());
        User user = userRepository.getByUid(principal.getName());
        Map<String, String> map_user = new HashMap<>();
        map_user.put("id", user.getUid());
        map_user.put("name", user.getName());
        map_user.put("role", user.getRoles().toString());
        return map_user;
    }*/
    public User getUser(Principal principal){
        LOGGER.info("[getUser] getUser 메서드 호출됨.");
        User user = userRepository.getByUid(principal.getName());
        return user;
    }
}
