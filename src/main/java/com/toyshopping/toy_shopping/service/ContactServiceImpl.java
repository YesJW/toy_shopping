package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ContactDto;
import com.toyshopping.toy_shopping.data.entity.Contact;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{


    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<ContactDto> getContact(Long id) {
        List<Contact> contacts = contactRepository.findAllByUno_id(id);
        List<ContactDto> contactDtos = new ArrayList<>();
        for(Contact c : contacts){
            ContactDto contactDto = new ContactDto();
            contactDto.setTitle(c.getTitle());
            contactDto.setMessage(c.getMessage());
            contactDto.setAns(c.getAnswer());
            contactDto.setName(c.getName());
            contactDto.setTime(c.getTime());
            contactDtos.add(contactDto);

        }

        return contactDtos;
    }

    @Override
    public ContactDto saveContact(ContactDto contactDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-mm-dd hh:mm:ss");
        String nowTime = now.format(formatter);
        Contact contact = new Contact();
        contact.setUno(user);
        contact.setMessage(contactDto.getMessage());
        contact.setName(contactDto.getName());
        contact.setTitle(contactDto.getTitle());
        contact.setTo_name(contactDto.getTo_Name());
        contact.setAnswer(false);
        contact.setTime(nowTime);
        Contact saveContact = contactRepository.save(contact);

        ContactDto responseContactDto = new ContactDto();
        responseContactDto.setName(saveContact.getName());
        responseContactDto.setTitle(saveContact.getTitle());
        responseContactDto.setMessage(saveContact.getMessage());
        responseContactDto.setTo_Name(saveContact.getTo_name());

        return responseContactDto;
    }

    @Override
    public ContactDto changeContact(Long num, String title, String message) {

        return null;
    }

    @Override
    public void deleteContact(Long num) throws Exception {

    }
}
