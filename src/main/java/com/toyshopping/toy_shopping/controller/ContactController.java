package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.ContactAdminDto;
import com.toyshopping.toy_shopping.data.dto.ContactDto;
import com.toyshopping.toy_shopping.data.dto.ContactReplyDto;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.ContactRepository;
import com.toyshopping.toy_shopping.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ContactController {

    private static Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
    private ContactRepository contactRepository;
    private ContactService contactService;

    @Autowired
    public ContactController(ContactRepository contactRepository, ContactService contactService) {
        this.contactService = contactService;
        this.contactRepository = contactRepository;
    }

    @GetMapping(value = "/contactPage")
    public ModelAndView getContactPage() {
        LOGGER.info("[ContactController] getContactPage 호출");
        ModelAndView mav = new ModelAndView("contactPage");
        return mav;
    }

    @GetMapping(value = "/contactAns")
    public ModelAndView getContactAnsPage() {
        LOGGER.info("[ContactController] getContactAns 호출");
        ModelAndView mav = new ModelAndView("contactAns");
        return mav;
    }

    @GetMapping(value = "/getAllContact")
    public <T> ResponseEntity<T> getAllContact() {
        User userDto = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("[ContactController] getAllContact 호출" + userDto.getRoles().get(0));

        if(userDto.getRoles().get(0).equals("ROLE_USER")) {
            List<ContactDto> contactDtos = contactService.getAllContact(userDto.getId());
            return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.OK).body(contactDtos);
        }
        else{
            List<ContactAdminDto> contactAdminDtos = contactService.getAdminContact(userDto.getName().toString());
            return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.OK).body(contactAdminDtos);

        }

    }

    @GetMapping(value = "/getContact")
    public ResponseEntity<ContactDto> getContact(@RequestParam Long num) {
        LOGGER.info("[ContactController] getContact 호출");

        ContactDto contactDto = contactService.getContact(num);

        return ResponseEntity.status(HttpStatus.OK).body(contactDto);

    }

    @GetMapping(value = "/getAdminContact")
    public ResponseEntity<List<ContactAdminDto>> getAdminContact() {
        LOGGER.info("[ContactController] getAdminContact 호출");
        List<ContactAdminDto> contactAdminDtos = contactService.getAdminContact("admin");

        return ResponseEntity.status(HttpStatus.OK).body(contactAdminDtos);
    }

    @PostMapping(value = "/sendContact")
    public ResponseEntity<ContactDto> sendContact(@RequestParam("title") String title, @RequestParam("message") String message) {
        LOGGER.info("[ContactController] sendContact 호출");
        User userDto = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContactDto contactDto = new ContactDto();
        contactDto.setTitle(title);
        contactDto.setMessage(message);
        contactDto.setName(userDto.getName());
        if (userDto.getName() != "admin") {
            contactDto.setTo_Name("admin");
        }
        ContactDto save_con = contactService.saveContact(contactDto);

        return ResponseEntity.status(HttpStatus.OK).body(save_con);
    }

    @PutMapping(value = "/replyContact")
    public ResponseEntity<ContactReplyDto> contactReply(@RequestParam("num") Long number, @RequestParam("message") String message) {
        ContactReplyDto contactReplyDto = contactService.replyContact(number, message);

        return ResponseEntity.status(HttpStatus.OK).body(contactReplyDto);
    }
}
