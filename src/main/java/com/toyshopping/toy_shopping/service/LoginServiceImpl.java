package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.LoginRepository;
import com.toyshopping.toy_shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = loginRe.findByEmail(email);

        if (member == null) throw new UsernameNotFoundException("Not Found account.");

        return member;
    }
}