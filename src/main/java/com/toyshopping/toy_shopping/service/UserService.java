package com.toyshopping.toy_shopping.service;


import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.data.entity.User;

public interface UserService {

    UserDto register(UserDto userDto);

}
