package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users getByUid(String uid);

    Users findUserByNameAndPhone(String name, String phone);

    Users findUserByUidAndNameAndPhone(String uid, String name, String phone);

    Users findUserByUidAndPhone(String uid, String phone);

    Optional<Users> findByUid(String username);

    boolean existsByUid(String uid);
}
