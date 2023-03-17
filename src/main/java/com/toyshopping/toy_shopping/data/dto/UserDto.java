package com.toyshopping.toy_shopping.data.dto;

import lombok.Data;

@Data
public class UserDto {
    private long userNo;
    private String userId;
    private String userPw;
    private String userName;
    private String userAuth;
}
