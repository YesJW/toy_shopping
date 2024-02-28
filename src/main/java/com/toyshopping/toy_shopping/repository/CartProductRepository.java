package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    CartProduct findByCart_cNumAndProduct_numb(Long cartId, Long pId);

}
