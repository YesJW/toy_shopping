package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUid(String uid);
}
