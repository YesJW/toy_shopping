package com.toyshopping.toy_shopping.data.dto;

import com.toyshopping.toy_shopping.data.entity.Orders;
import com.toyshopping.toy_shopping.data.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductDto {
    private Long id;
    private Orders orders;
    private Users users;
    private String  productName;
    private int orderId;
    private int price;
    private int count;
    private int total;

    private Long shoppingCartProductId;

}
