package com.toyshopping.toy_shopping.data.dto;

import lombok.Data;

@Data
public class ContactAdminDto {
    private Long num;

    private String name;

    private String title;

    private String message;

    private String time;

    private boolean ans;
}

