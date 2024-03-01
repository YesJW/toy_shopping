package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.CartProductDto;
import com.toyshopping.toy_shopping.data.dto.OrderProductDto;
import com.toyshopping.toy_shopping.data.entity.CartProduct;
import com.toyshopping.toy_shopping.data.entity.Order;
import com.toyshopping.toy_shopping.data.entity.OrderProduct;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.OrderRepository;
import com.toyshopping.toy_shopping.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.getByUid(authentication.getName());



    }

    @Transactional
    public void buyCartProducts(List<CartProductDto> cartProductDtoList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.getByUid(authentication.getName());

        List<OrderProductDto> orderProductDtos = addOrderProducts(cartProductDtoList, , users);

    }

    @Transactional
    public Order addOrder(List<OrderProduct> orderProducts, Users users) {

    }


    public List<OrderProductDto> addOrderProducts(List<CartProductDto> cartProductDtos, Order order, Users users) {
        List<OrderProductDto> orderProductDtos = new ArrayList<>();

        for (CartProductDto cartProductDto : cartProductDtos) {
            OrderProductDto orderProductDto = OrderProductDto.builder()
                    .order(order)
                    .price(cartProductDto.getPrice())
                    .productName(cartProductDto.getProductName())
                    .total(cartProductDto.getPrice() * cartProductDto.getCount())
                    .orderId((int) System.currentTimeMillis())
                    .count(cartProductDto.getCount())
                    .users(users)
                    .build();
            orderProductDtos.add(orderProductDto);


        }
    }

}
