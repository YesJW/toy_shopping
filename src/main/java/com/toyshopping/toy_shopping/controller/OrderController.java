package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.CartProductDto;
import com.toyshopping.toy_shopping.data.dto.OrderDto;
import com.toyshopping.toy_shopping.data.dto.OrderProductDto;
import com.toyshopping.toy_shopping.data.dto.ProductDto;
import com.toyshopping.toy_shopping.data.entity.OrderProduct;
import com.toyshopping.toy_shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAllOrderHisyory")
    public ResponseEntity<List<OrderDto>> getAllOrderHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders( ));
    }

}
