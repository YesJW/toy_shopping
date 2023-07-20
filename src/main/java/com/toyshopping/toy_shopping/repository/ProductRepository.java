package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUno_id(Long user_no);

    List<Product> findAllByNameContainingOrderByNameDesc(String name);

    List<Product> findAllByNameContainingOrderByPriceDesc(String name);

    List<Product> findALlByNameContainingOrderByStockDesc(String name);

    List<Product> findAllByNameContainingOrderByPriceAsc(String name);

    List<Product> findALlByNameContainingOrderByStockAsc(String name);
}
