package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import com.toyshopping.toy_shopping.data.entity.Product;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDto> getUserProduct(Long user_no) {

        List<Product> products = productRepository.findAllByUno_id(user_no);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product p : products){
            ProductResponseDto productResponseDto = new ProductResponseDto();
            productResponseDto.setNumb(p.getNumb());
            productResponseDto.setName(p.getName());
            productResponseDto.setPrice(p.getPrice());
            productResponseDto.setStock(p.getStock());
            productResponseDto.setUser_no(p.getUno().getId());
            productResponseDtos.add(productResponseDto);
        }


        return productResponseDtos;
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id).get();
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setName(product.getName());
        productResponseDto.setStock(product.getStock());
        productResponseDto.setUser_no(product.getUno().getId());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setNumb(product.getNumb());

        return productResponseDto;

    }

    @Override
    public ProductResponseDto saveProduct(ProductDto productDto) {

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("[UserNo 가져오기] text : {}", user.getId());
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setUno(user);
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
    public List<ProductResponseDto> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product p : products){
            ProductResponseDto productResponseDto = new ProductResponseDto();
            productResponseDto.setNumb(p.getNumb());
            productResponseDto.setName(p.getName());
            productResponseDto.setPrice(p.getPrice());
            productResponseDto.setStock(p.getStock());
            productResponseDto.setUser_no(p.getUno().getId());
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        productRepository.deleteById(number);
    }
}