package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.data.entity.Product;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto getProduct(Long number) {

        Product product = productRepository.findById(number).get();
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumb(product.getNumb());
        productResponseDto.setUno(product.getUno().getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStock(product.getStock());
        return productResponseDto;
    }

    @Override
    public ProductResponseDto saveProduct(ProductDto productDto) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = (UserDto)principal;
        Long uno = userDto.getUserNo();
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setUno();
        Product saveProduct = productRepository.save(product);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumb(saveProduct.getNumb());
        productResponseDto.setStock(saveProduct.getStock());
        productResponseDto.setName(saveProduct.getName());
        productResponseDto.setPrice(saveProduct.getPrice());

        return  productResponseDto;
    }

    @Override
    public ProductResponseDto changeProduct(Long numb, String name, int price, int stock) {
        Product foundProduct = productRepository.findById(numb).get();
        foundProduct.setName(name);
        Product changeProduct = productRepository.save(foundProduct);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumb(changeProduct.getNumb());
        productResponseDto.setName(changeProduct.getName());
        productResponseDto.setPrice(changeProduct.getPrice());
        productResponseDto.setStock(changeProduct.getStock());

        return productResponseDto;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        productRepository.deleteById(number);
    }
}