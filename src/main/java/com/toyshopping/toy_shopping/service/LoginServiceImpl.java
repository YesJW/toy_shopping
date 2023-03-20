package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.entity.Login;
import com.toyshopping.toy_shopping.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements UserDetailsService {

    private final LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Login login = loginRepository.findById(email);

        if (login == null) throw new UsernameNotFoundException("Not Found account.");

        return login;
    }
}