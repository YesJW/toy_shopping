package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
