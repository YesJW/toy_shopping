package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.UsersDto;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FindIdPwServiceImpl implements FindIdPwService{
    private final Logger LOGGER = LoggerFactory.getLogger(FindIdPwServiceImpl.class);
    private final UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    @Autowired
    FindIdPwServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;}

    @Override
    public String findUserId(String name, String phone) {
        Users users = userRepository.findUserByNameAndPhone(name, phone);
        LOGGER.info("id : {}", users.getUid());
        if (users != null) {
            String id = users.getUid();
            return id;
        }
        return "아이디를 찾지 못했습니다.";
    }

    @Override
    public UsersDto search_User_Pw(String id, String name, String phone) {
        Users users = userRepository.findUserByUidAndNameAndPhone(id, name, phone);
        if (users != null) {
            UsersDto userDto = UsersDto.builder()
                    .uid(users.getUid())
                    .id(users.getId())
                    .name(users.getName())
                    .roles(users.getRoles())
                    .phone(users.getPhone())
                    .build();


            return userDto;
        }
        return null;
    }

    @Override
    public UsersDto changeUserPw(String id, String phone, String password) {
        Users users = userRepository.findUserByUidAndPhone(id, phone);
        if (users != null) {
            users.setPassword(passwordEncoder.encode(password));
            Users changePassword = userRepository.save(users);

            UsersDto userDto = UsersDto.builder()
                    .uid(users.getUid())
                    .id(users.getId())
                    .name(users.getName())
                    .roles(users.getRoles())
                    .phone(users.getPhone())
                    .build();

            return userDto;
        }
        return null;
    }
}
