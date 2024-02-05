package com.toyshopping.toy_shopping.data.dto;

import com.toyshopping.toy_shopping.data.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Long id;
    private String uid;
    private String name;
    private List<String> roles;
    private String phone;


    static public UsersDto toDto(Users users) {
        return UsersDto.builder()
                .uid(users.getUid())
                .id(users.getId())
                .name(users.getName())
                .roles(users.getRoles())
                .phone(users.getPhone())
                .build();
    }
    public Users toEntity(){
        return Users.builder()
                .id(id)
                .name(name)
                .phone(phone)
                .uid(uid)
                .build();
    }
}


