package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ContactAdminDto;
import com.toyshopping.toy_shopping.data.dto.ContactDto;
import com.toyshopping.toy_shopping.data.dto.ContactReplyDto;
import com.toyshopping.toy_shopping.data.entity.Contact;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.ContactRepository;
import com.toyshopping.toy_shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{


    private ContactRepository contactRepository;

    private UserRepository userRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ContactDto> getAllContact() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.getByUid(authentication.getName());
        System.out.println(users.getRoles());
        if (users.getRoles().get(0).equals("USER")) {
            List<Contact> contacts = contactRepository.findAllByUno_id(users.getId());
            List<ContactDto> contactDtos = new ArrayList<>();
            for(Contact c : contacts){
                ContactDto contactDto = new ContactDto();
                contactDto.setNum(c.getNum());
                contactDto.setTitle(c.getTitle());
                contactDto.setMessage(c.getMessage());
                contactDto.setAns(c.getAnswer());
                contactDto.setName(c.getName());
                contactDto.setTime(c.getTime());
                contactDto.setReply_time(c.getReply_time());
                contactDto.setTo_Name(c.getToName());
                contactDto.setReply_time(c.getReply());
                contactDtos.add(contactDto);

            }
            if (contactDtos.isEmpty()) {
                return null;
            }
            return contactDtos;
        }

        else{
            System.out.println(users.getId());
            List<Contact> contacts = contactRepository.findAllByToName("admin");
            List<ContactDto> contactAdminDtos = new ArrayList<>();
            for (Contact c : contacts) {
                ContactDto contactAdminDto = new ContactDto();
                contactAdminDto.setNum(c.getNum());
                contactAdminDto.setTitle(c.getTitle());
                contactAdminDto.setMessage(c.getMessage());
                contactAdminDto.setName(c.getName());
                contactAdminDto.setTime(c.getTime());
                contactAdminDto.setAns(c.getAnswer());
                contactAdminDtos.add(contactAdminDto);
            }
            if (contactAdminDtos.isEmpty()) {
                return null;
            }
            return contactAdminDtos;
        }

    }

    @Override
    public ContactDto getContact(Long num) {
        Contact contact = contactRepository.getById(num);
        ContactDto contactDto = new ContactDto();
        contactDto.setTitle(contact.getTitle());
        contactDto.setMessage(contact.getMessage());
        contactDto.setNum(contact.getNum());
        contactDto.setReply(contact.getReply());
        contactDto.setReply_time(contact.getReply_time());
        contactDto.setAns(contact.getAnswer());
        contactDto.setTo_Name(contact.getToName());
        contactDto.setName(contact.getName());
        return contactDto;
    }

    @Override
    public ContactDto sendContact(ContactDto contactDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.getByUid(authentication.getName());
        String nowTime = nowTime();
        Contact contact = new Contact();
        contact.setUno(users);
        contact.setMessage(contactDto.getMessage());
        contact.setName(users.getName());
        contact.setTitle(contactDto.getTitle());
        contact.setToName("admin");
        contact.setAnswer(false);
        contact.setTime(nowTime);
        Contact saveContact = contactRepository.save(contact);

        ContactDto responseContactDto = new ContactDto();
        responseContactDto.setName(saveContact.getName());
        responseContactDto.setTitle(saveContact.getTitle());
        responseContactDto.setMessage(saveContact.getMessage());
        responseContactDto.setTo_Name(saveContact.getToName());

        return responseContactDto;
    }

    @Override
    public ContactReplyDto replyContact(Long number, String message) {
        Contact contact = contactRepository.getById(number);
        contact.setReply(message);
        contact.setReply_time(nowTime());
        contact.setAnswer(true);
        contactRepository.save(contact);

        ContactReplyDto contactReplyDto = new ContactReplyDto();
        contactReplyDto.setNum(number);
        contactReplyDto.setReply(contact.getReply());
        contactReplyDto.setReplyTime(contact.getReply_time());
        return contactReplyDto;
    }

    public String nowTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss");
        String nowTime = now.format(formatter);
        return nowTime;
    }


}
