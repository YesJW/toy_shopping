package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import com.toyshopping.toy_shopping.data.dto.ShoppingCartDto;
import com.toyshopping.toy_shopping.data.dto.ShoppingCartResponseDto;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCartResponseDto addShoppingCartProduct(ShoppingCartDto shoppingCartDto);

    List<ShoppingCartResponseDto> getShoppingCartProduct(Long user_no);
    ShoppingCartResponseDto changeCartProduct(Long cNum, int stock);
    void deleteShoppingCartProduct(Long number) throws Exception;
}
