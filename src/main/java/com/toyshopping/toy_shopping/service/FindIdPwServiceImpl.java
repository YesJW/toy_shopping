package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.controller.MainPageController;
import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FindIdPwServiceImpl implements FindIdPwService{
    private final Logger LOGGER = LoggerFactory.getLogger(FindIdPwServiceImpl.class);
    private final UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    @Autowired
    FindIdPwServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;}

    @Override
    public String findUserId(String name, String phone) {
        User user = userRepository.findUserByNameAndPhone(name, phone);
        LOGGER.info("id : {}",user.getUid());
        if (user != null) {
            String id = user.getUid();
            return id;
        }
        return "아이디를 찾지 못했습니다.";
    }

    @Override
    public UserDto search_User_Pw(String id, String name, String phone) {
        User user = userRepository.findUserByUidAndNameAndPhone(id, name, phone);
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setUserNo(user.getId());
            userDto.setUserId(user.getUid());
            userDto.setUserPw(user.getPassword());
            userDto.setRoles(user.getRoles());
            userDto.setUserName(user.getName());
            userDto.setPhone(user.getPhone());

            return userDto;
        }
        return null;
    }

    @Override
    public UserDto changeUserPw(String id, String phone, String password) {
        User user = userRepository.findUserByUidAndPhone(id, phone);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            User changePassword = userRepository.save(user);

            UserDto userDto = new UserDto();
            userDto.setUserNo(changePassword.getId());
            userDto.setUserId(changePassword.getUid());
            userDto.setUserPw(changePassword.getPassword());
            userDto.setRoles(changePassword.getRoles());
            userDto.setUserName(changePassword.getName());
            userDto.setPhone(changePassword.getPhone());

            return userDto;
        }
        return null;
    }
}
