package com.toyshopping.toy_shopping.data.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartDto {
    //private Long cNum;
    private Long pNum;
    //private int price;
    private int stock;
    private Long user_no;

}
