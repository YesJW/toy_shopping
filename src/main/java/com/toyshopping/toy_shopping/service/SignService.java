package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.SignInResultDto;
import com.toyshopping.toy_shopping.data.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signUp(String id, String password, String name, String number, String role);

    SignInResultDto signIn(String id, String password) throws RuntimeException;

}
