package com.toyshopping.toy_shopping.repository;

import com.toyshopping.toy_shopping.data.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByUno_id(Long id);

}
