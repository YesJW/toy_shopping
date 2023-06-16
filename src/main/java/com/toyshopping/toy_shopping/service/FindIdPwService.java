package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.UserDto;

public interface FindIdPwService {

    String findUserId(String name, String phone);

    UserDto search_User_Pw(String id, String name, String phone);

    UserDto changeUserPw(String id, String phone, String password);
}
