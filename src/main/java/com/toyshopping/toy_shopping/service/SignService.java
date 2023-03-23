package com.toyshopping.toy_shopping.service;

public interface SignService {

    SignUpResultDto signUp(String id, String password, String name, String role);

    SignInResultDto signIn(String id, String password) throws RuntimeException;

}
