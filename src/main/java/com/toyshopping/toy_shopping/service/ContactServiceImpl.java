package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ContactDto;
import com.toyshopping.toy_shopping.data.entity.Contact;
import com.toyshopping.toy_shopping.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl implements ContactService{

    @Autowired
    ContactRepository contactRepository;

    @Override
    public List<ContactDto> getContact(Long id) {
        List<Contact> contacts = contactRepository.findAllByUno(id);
        List<ContactDto> contactDtos = new ArrayList<>();
        for(Contact c : contacts){
            ContactDto contactDto = new ContactDto();
            contactDto.setNum(c.getNum());
            contactDto.setUno(c.getUno());
            contactDto.setTitle(c.getTitle());
            contactDto.setMessage(c.getMessage());
            contactDto.setName(c.getName());
            contactDtos.add(contactDto);
        }


        return contactDtos;
    }

    @Override
    public ContactDto saveContact(ContactDto contactDto) {
        return null;
    }

    @Override
    public ContactDto changeContact(Long num, String title, String message) {
        return null;
    }

    @Override
    public void deleteContact(Long num) throws Exception {

    }
}
