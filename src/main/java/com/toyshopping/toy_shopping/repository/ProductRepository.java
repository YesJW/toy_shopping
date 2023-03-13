package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
