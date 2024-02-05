package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.ChangeProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.service.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/getUserProducts")
    @ResponseBody
    public ResponseEntity<List<ProductResponseDto>> getUserProduct() {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ProductResponseDto> productResponseDto = productService.getUserProduct(users.getId());

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);

    }

    @GetMapping(value = "/getAllProduct")
    @ResponseBody
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        List<ProductResponseDto> productResponseDtos = productService.getAllProduct();

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtos);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/addProduct")
    @ResponseBody
    public ResponseEntity<ProductResponseDto> createProduct(@RequestParam("name") String name, @RequestParam("price") int price, @RequestParam("stock") int stock, @RequestParam("imgFile") MultipartFile imgFile) throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setStock(stock);
        productDto.setPrice(price);

        ProductResponseDto productResponseDto = productService.saveProduct(productDto, imgFile);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @GetMapping(value = "/addProductPage")
    public String productPage() {
        LOGGER.info("[mypageGetMethod] mypage get 메서드 호출됨.");
        return "/addProduct";
    }

    @PutMapping("/updateProduct")
    @ResponseBody
    public ResponseEntity<ProductResponseDto> changeProductName(
            @RequestBody ChangeProductDto changeProductDto) throws Exception {
        ProductResponseDto productResponseDto = productService.changeProduct(
                changeProductDto.getNumber(),
                changeProductDto.getName(),
                changeProductDto.getStock(),
                changeProductDto.getPrice());

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);

    }

    @DeleteMapping("/deleteProduct")
    @ResponseBody
    public ResponseEntity<String> deleteProduct(@RequestParam("number") Long number) throws Exception {
        productService.deleteProduct(number);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

    @GetMapping(value = "/getProductDetail")
    @ResponseBody
    public ProductResponseDto getProductDetail(@RequestParam("id") Long productId) {
        LOGGER.info("[getProductDetailGetMethod] getProductDetail 메서드 호출됨.");
        ProductResponseDto product = productService.getProduct(productId);

        return product;
    }

    @GetMapping(value = "/productDetail")
    public String productDetailPage() {
        LOGGER.info("[productDetailGetMethod] productDetail 메서드 호출됨.");
        return "/productDetail";
    }

    @GetMapping(value = "/updatePage")
    public String updatePage() {
        LOGGER.info("[updateProductGetMethod] updateProduct 메서드 호출됨.");
        return "/updatePage";
    }

    @GetMapping(value = "/searchPage")
    public String searchProductPage(){
        return "/searchPage";
    }

    @GetMapping(value = "/searchProducts")
    public ResponseEntity<List<ProductResponseDto>> searchProduct(@RequestParam("q") String keyword, @RequestParam(defaultValue = "이름순",value = "sort") String sortOrder) {

        LOGGER.info("[ProductController] searchProducts 메서드 호출됨.");
        List<ProductResponseDto> searchResult = productService.getSearchProduct(keyword,sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(searchResult);
    }

}
