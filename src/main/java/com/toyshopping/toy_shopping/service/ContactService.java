package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ContactDto;

import java.util.List;

public interface ContactService {

    List<ContactDto> getContact(Long id);

    ContactDto saveContact(ContactDto contactDto);

    ContactDto changeContact(Long num, String title, String message);

    void deleteContact(Long num) throws Exception;

}
