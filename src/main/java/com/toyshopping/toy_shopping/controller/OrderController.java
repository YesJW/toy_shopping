package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.CartProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.entity.OrderProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @PostMapping("/buyProduct")
    public ResponseEntity<List<OrderProduct>> buyProduct(@RequestBody List<CartProductDto> cartProductDtos) {



        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
