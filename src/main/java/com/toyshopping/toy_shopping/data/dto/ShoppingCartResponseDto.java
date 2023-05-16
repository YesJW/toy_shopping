package com.toyshopping.toy_shopping.data.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponseDto {
    private Long cNum;
    private Long pNum;
    private int price;
    private String pName;
    private int stock;
    private Long user_no;

}
