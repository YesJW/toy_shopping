package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByUsers_id(Long id);

}
