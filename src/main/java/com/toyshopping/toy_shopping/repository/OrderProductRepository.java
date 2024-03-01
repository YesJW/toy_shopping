package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
