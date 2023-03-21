package com.toyshopping.toy_shopping.service;


import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.data.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
