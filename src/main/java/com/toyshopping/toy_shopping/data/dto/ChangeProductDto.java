package com.toyshopping.toy_shopping.data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChangeProductDto {
    private Long number;
    private String name;
    private int price;
    private int stock;

}
