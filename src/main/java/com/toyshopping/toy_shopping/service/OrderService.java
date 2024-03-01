package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.CartProductDto;
import com.toyshopping.toy_shopping.data.dto.OrderProductDto;
import com.toyshopping.toy_shopping.data.entity.CartProduct;
import com.toyshopping.toy_shopping.data.entity.Order;
import com.toyshopping.toy_shopping.data.entity.OrderProduct;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.OrderProductRepository;
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
    private OrderProductRepository orderProductRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(Users users) {
        Order order = Order.createdOrder(users);

        return order;

    }

    @Transactional
    public void buyCartProducts(List<CartProductDto> cartProductDtoList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.getByUid(authentication.getName());
        Order order = createOrder(users);
        orderRepository.save(order);
        List<OrderProduct> orderProducts = addOrderProducts(cartProductDtoList, order, users);
        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
    }



    public List<OrderProduct> addOrderProducts(List<CartProductDto> cartProductDtos, Order order, Users users) {
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (CartProductDto cartProductDto : cartProductDtos) {
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .price(cartProductDto.getPrice())
                    .productName(cartProductDto.getProductName())
                    .total(cartProductDto.getPrice() * cartProductDto.getCount())
                    .orderId((int) System.currentTimeMillis())
                    .count(cartProductDto.getCount())
                    .users(users)
                    .build();
            orderProductRepository.save(orderProduct);
            orderProducts.add(orderProduct);

        }
        return orderProducts;
    }

}
