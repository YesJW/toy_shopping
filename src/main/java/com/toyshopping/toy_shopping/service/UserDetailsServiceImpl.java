package com.toyshopping.toy_shopping.service;


import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUsername] loadUserbyUsername 수행. userid: {}", uid);
        return userRepository.findByUid(uid)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

    }

    private UserDetails createUserDetails(Users user){
        return User.builder().username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();

    }


}
