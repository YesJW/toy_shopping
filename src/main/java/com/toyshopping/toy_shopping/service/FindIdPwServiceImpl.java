package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.controller.MainPageController;
import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindIdPwServiceImpl implements FindIdPwService{
    private final Logger LOGGER = LoggerFactory.getLogger(FindIdPwServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    FindIdPwServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;}

    @Override
    public String findUserId(String name, String phone) {
        User user = userRepository.findUserByNameAndPhone(name, phone);
        LOGGER.info("id : {}",user.getUid());
        if (user != null) {
            String id = user.getUid();
            return id;
        }
        return null;
    }

    @Override
    public UserDto findUserPw(String id, String name, String phone) {
        return null;
    }
}
