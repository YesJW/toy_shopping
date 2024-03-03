package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.CartProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.entity.OrderProduct;
import com.toyshopping.toy_shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/buyProducts")
    public ResponseEntity<List<OrderProduct>> buyProduct(@RequestBody List<CartProductDto> cartProductDtos) throws Exception {
        orderService.buyCartProducts(cartProductDtos);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
