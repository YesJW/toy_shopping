package com.toyshopping.toy_shopping.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long numb;
    private Long uno;
    private String name;
    private int price;
    private int stock;

}
