package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.ShoppingCart;
import com.toyshopping.toy_shopping.data.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart getByUno_id(Long user_id);
}
