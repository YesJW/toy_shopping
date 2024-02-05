package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.JwtTokenDto;
import com.toyshopping.toy_shopping.data.dto.SignUpDto;
import com.toyshopping.toy_shopping.data.dto.SignUpResultDto;
import com.toyshopping.toy_shopping.data.dto.UsersDto;

public interface SignService {

    UsersDto signUp(SignUpDto signUpDto);

    JwtTokenDto signIn(String id, String password) throws RuntimeException;

}
