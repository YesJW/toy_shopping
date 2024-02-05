package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.UsersDto;

public interface FindIdPwService {

    String findUserId(String name, String phone);

    UsersDto search_User_Pw(String id, String name, String phone);

    UsersDto changeUserPw(String id, String phone, String password);
}
