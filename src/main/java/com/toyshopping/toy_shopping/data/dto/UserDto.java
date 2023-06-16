package com.toyshopping.toy_shopping.data.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private long userNo;
    private String userId;
    private String userPw;
    private String userName;
    private List<String> roles = new ArrayList<>();
    private String phone;
}
