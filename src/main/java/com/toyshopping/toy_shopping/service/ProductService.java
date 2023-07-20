package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import com.toyshopping.toy_shopping.data.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductResponseDto> getUserProduct(Long user_no);

    ProductResponseDto getProduct(Long id);

    ProductResponseDto saveProduct(ProductDto productDto);

    ProductResponseDto changeProduct(Long numb, String name, int price, int stock);

    List<ProductResponseDto> getAllProduct();

    void deleteProduct(Long number) throws Exception;

    List<ProductResponseDto> getSearchProduct(String keyword, String sort);


}
