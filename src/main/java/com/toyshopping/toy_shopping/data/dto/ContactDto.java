package com.toyshopping.toy_shopping.data.dto;

import com.toyshopping.toy_shopping.data.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
public class ContactDto {
    private String name;

    private String title;

    private String message;

    private Boolean ans;

    private String to_Name;

    private String time;
}
