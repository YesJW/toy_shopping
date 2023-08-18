package com.toyshopping.toy_shopping.data.dto;

import lombok.*;

import java.io.File;

@Data
public class ProductDto {

    private String name;
    private Long userN;
    private int price;
    private int stock;
    private String imgName;
    private String imgPath;

}
