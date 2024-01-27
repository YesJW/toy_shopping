package com.toyshopping.toy_shopping.data.dto;

import lombok.Data;

@Data
public class ContactDto {
    private Long num;

    private String name;

    private String title;

    private String message;

    private String reply;

    private Boolean ans;

    private String to_Name;

    private String time;

    private String reply_time;
}
