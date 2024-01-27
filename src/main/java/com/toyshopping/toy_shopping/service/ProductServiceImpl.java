package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import com.toyshopping.toy_shopping.data.entity.Product;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        productResponseDto.setImgName(product.getImgName());
        productResponseDto.setImgPath(product.getImgPath());

        return productResponseDto;

    }

    @Override
    public ProductResponseDto saveProduct(ProductDto productDto, MultipartFile imgFile) throws IOException {
        Users users = (Users)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("[UserNo 가져오기] text : {}", users.getId());
        String imgOriName = imgFile.getOriginalFilename();
        String imgName = "";

        String path = System.getProperty("user.dir") + "/src/main/resources/static/images/";

        UUID uuid = UUID.randomUUID();

        imgName = uuid + "_" + imgOriName;

        File saveImage = new File(path, imgName);

        imgFile.transferTo(saveImage);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setUno(users);
        product.setImgName(imgName);
        product.setImgPath("/images/" + imgName);
        Product saveProduct = productRepository.save(product);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumb(saveProduct.getNumb());
        productResponseDto.setStock(saveProduct.getStock());
        productResponseDto.setName(saveProduct.getName());
        productResponseDto.setPrice(saveProduct.getPrice());

        return  productResponseDto;
    }

    @Override
    public ProductResponseDto changeProduct(Long numb, String name, int stock, int price) {
        Product foundProduct = productRepository.findById(numb).get();
        foundProduct.setName(name);
        foundProduct.setStock(stock);
        foundProduct.setPrice(price);
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
            productResponseDto.setImgName(p.getImgName());
            productResponseDto.setImgPath(p.getImgPath());
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        productRepository.deleteById(number);
    }

    @Override
    public List<ProductResponseDto> getSearchProduct(String keyword, String sort) {
        List<Product> products = null;
        switch (sort) {
            case "이름순":
                products = productRepository.findAllByNameContainingOrderByNameDesc(keyword);
                break;
            case "높은 가격순":
                products = productRepository.findAllByNameContainingOrderByPriceDesc(keyword);
                break;
            case "갯수 많은순":
                products = productRepository.findALlByNameContainingOrderByStockDesc(keyword);
                break;
            case "낮은 가격순":
                products = productRepository.findAllByNameContainingOrderByPriceAsc(keyword);
                break;
            case "갯수 적은순":
                products = productRepository.findALlByNameContainingOrderByStockAsc(keyword);
                break;
            default: break;
        }
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = new ProductResponseDto();
            productResponseDto.setNumb(product.getNumb());
            productResponseDto.setStock(product.getStock());
            productResponseDto.setName(product.getName());
            productResponseDto.setPrice(product.getPrice());
            productResponseDto.setUser_no(product.getUno().getId());

            productResponseDtos.add(productResponseDto);
        }
        return productResponseDtos;
    }
}