package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import com.toyshopping.toy_shopping.data.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductResponseDto> getUserProduct();

    ProductResponseDto getProduct(Long id);

    ProductResponseDto saveProduct(ProductDto productDto, MultipartFile imageFile) throws IOException;

    ProductResponseDto changeProduct(Long numb, String name, int price, int stock);

    List<ProductResponseDto> getAllProduct();

    void deleteProduct(Long number) throws Exception;

    List<ProductResponseDto> getSearchProduct(String keyword, String sort);


}
