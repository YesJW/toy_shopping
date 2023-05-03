package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUno_id(Long user_no);
}
