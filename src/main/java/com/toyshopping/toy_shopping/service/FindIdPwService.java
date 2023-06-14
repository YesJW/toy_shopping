package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.UserDto;

public interface FindIdPwService {

    String findUserId(String name, String phone);

    UserDto findUserPw(String id, String name, String phone);
}
