package com.toyshopping.toy_shopping.data.dto;

import com.toyshopping.toy_shopping.data.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
public class ContactDto {
    private Long num;

    private String name;

    private String title;

    private String message;

    private User uno;
}
