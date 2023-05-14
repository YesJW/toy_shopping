package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUno_id(Long user_no);
}
