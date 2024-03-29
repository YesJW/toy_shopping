package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ContactAdminDto;
import com.toyshopping.toy_shopping.data.dto.ContactDto;
import com.toyshopping.toy_shopping.data.dto.ContactReplyDto;

import java.util.List;

public interface ContactService {

    List<ContactDto> getAllContact();

    ContactDto getContact(Long num);
    ContactDto sendContact(ContactDto contactDto);

    ContactReplyDto replyContact(Long number, String message);


}
