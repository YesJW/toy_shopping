package com.toyshopping.toy_shopping.data.dto;

import com.toyshopping.toy_shopping.data.entity.Users;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {

    private String uid;
    private String password;
    private String name;
    private List<String> roles = new ArrayList<>();
    private String phone;

    public Users toEntity(String en_password, List<String> roles) {
        return Users.builder()
                .uid(uid)
                .password(en_password)
                .name(name)
                .phone(phone)
                .roles(roles)
                .build();
    }

}
