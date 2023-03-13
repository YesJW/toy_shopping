package com.toyshopping.toy_shopping.data.dto;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String name;
    private int price;
    private int stock;

}
