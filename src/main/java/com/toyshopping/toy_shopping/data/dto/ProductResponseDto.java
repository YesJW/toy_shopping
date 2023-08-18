package com.toyshopping.toy_shopping.data.dto;

import com.toyshopping.toy_shopping.data.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDto {

    private Long numb;
    private String name;
    private int price;
    private int stock;
    private Long user_no;
    private String imgName;
    private String imgPath;

}
