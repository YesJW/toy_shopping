package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.Login;
import com.toyshopping.toy_shopping.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {

    public Login findById(String id);
}
