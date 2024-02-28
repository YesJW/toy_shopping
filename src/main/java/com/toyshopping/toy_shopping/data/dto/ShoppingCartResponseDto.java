package com.toyshopping.toy_shopping.data.dto;


import com.toyshopping.toy_shopping.data.entity.CartProduct;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShoppingCartResponseDto {
    private Long cNum;
    private List<CartProductDto> pNum;
    private int price;
    private String pName;
    private int stock;
    private Long user_no;

}
