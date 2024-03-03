package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.CartProductDto;
import com.toyshopping.toy_shopping.data.entity.*;
import com.toyshopping.toy_shopping.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderProductRepository orderProductRepository;

    private ShoppingCartService shoppingCartService;

    private CartProductRepository cartProductRepository;
    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        OrderProductRepository orderProductRepository, ShoppingCartService shoppingCartService,
                        CartProductRepository cartProductRepository, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.shoppingCartService = shoppingCartService;
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Orders createOrder(Users users) {
        Orders orders = Orders.createdOrder(users);
        return orders;
    }

    @Transactional
    public void buyCartProducts(List<CartProductDto> cartProductDtoList) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.getByUid(authentication.getName());
        Orders orders = createOrder(users);
        orderRepository.save(orders);
        List<OrderProduct> orderProducts = addOrderProducts(cartProductDtoList, orders, users);
        orders.setOrderProducts(orderProducts);
        orderRepository.save(orders);


        for (CartProductDto cartProductDto : cartProductDtoList) {
            Product product = productRepository.getById(cartProductDto.getProductId());
            product.setStock(product.getStock() - cartProductDto.getCount());
            productRepository.save(product);
        }

    }



    public List<OrderProduct> addOrderProducts(List<CartProductDto> cartProductDtos, Orders orders, Users users) throws Exception {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (CartProductDto cartProductDto : cartProductDtos) {
            LocalDateTime localDateTime = LocalDateTime.now();
            String localDateTimeFormat1
                    = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            OrderProduct orderProduct = OrderProduct.builder()
                    .orders(orders)
                    .price(cartProductDto.getPrice())
                    .productName(cartProductDto.getProductName())
                    .total(cartProductDto.getPrice() * cartProductDto.getCount())
                    .orderNumber(localDateTimeFormat1)
                    .count(cartProductDto.getCount())
                    .users(users)
                    .build();
            orderProductRepository.save(orderProduct);
            orderProducts.add(orderProduct);
            shoppingCartService.deleteShoppingCartProduct(cartProductDto.getId());
        }
        return orderProducts;
    }

}
