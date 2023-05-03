package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    List<ProductResponseDto> getProduct(Long user_no);

    ProductResponseDto saveProduct(ProductDto productDto);

    ProductResponseDto changeProduct(Long numb, String name, int price, int stock);

    void deleteProduct(Long number) throws Exception;

}
