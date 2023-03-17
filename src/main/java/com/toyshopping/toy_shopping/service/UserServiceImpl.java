package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){ this.userRepository = userRepository;}

    @Override
    public UserDto register(UserDto userDto) {
        User user1 = new User();
        user1.setUserNo(userDto.getUserNo());
        user1.setUserName(userDto.getUserName());
        user1.setUserId(userDto.getUserId());
        user1.setUserPw(userDto.getUserPw());
        user1.setUserAuth(userDto.getUserAuth());

        User user = userRepository.save(user1);

        UserDto userDto1 = new UserDto();
        userDto1.setUserNo(user.getUserNo());
        userDto1.setUserName(user.getUserName());
        userDto1.setUserId(userDto.getUserId());
        userDto1.setUserPw(userDto.getUserPw());
        userDto1.setUserAuth(user1.getUserAuth());
        return userDto1;
    }



}
