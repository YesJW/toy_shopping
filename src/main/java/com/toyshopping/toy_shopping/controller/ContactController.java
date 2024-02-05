package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.ContactAdminDto;
import com.toyshopping.toy_shopping.data.dto.ContactDto;
import com.toyshopping.toy_shopping.data.dto.ContactReplyDto;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ContactController {

    private static Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(value = "/contactPage")
    public String getContactPage() {
        LOGGER.info("[ContactController] getContactPage 호출");
        return "/contactPage";
    }

    @GetMapping(value = "/contactAns")
    public String getContactAnsPage() {
        LOGGER.info("[ContactController] getContactAns 호출");
        return "/contactAns";
    }

    @GetMapping(value = "/getAllContact")
    @ResponseBody
    public <T> ResponseEntity<T> getAllContact() {
        Users usersDto = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("[ContactController] getAllContact 호출" + usersDto.getRoles().get(0));

        if(usersDto.getRoles().get(0).equals("ROLE_USER")) {
            List<ContactDto> contactDtos = contactService.getAllContact(usersDto.getId());
            return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.OK).body(contactDtos);
        }
        else{
            List<ContactAdminDto> contactAdminDtos = contactService.getAdminContact(usersDto.getName().toString());
            return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.OK).body(contactAdminDtos);

        }

    }

    @GetMapping(value = "/getContact")
    @ResponseBody
    public ResponseEntity<ContactDto> getContact(@RequestParam Long num) {
        LOGGER.info("[ContactController] getContact 호출");

        ContactDto contactDto = contactService.getContact(num);

        return ResponseEntity.status(HttpStatus.OK).body(contactDto);

    }

    @GetMapping(value = "/getAdminContact")
    @ResponseBody
    public ResponseEntity<List<ContactAdminDto>> getAdminContact() {
        LOGGER.info("[ContactController] getAdminContact 호출");
        List<ContactAdminDto> contactAdminDtos = contactService.getAdminContact("admin");

        return ResponseEntity.status(HttpStatus.OK).body(contactAdminDtos);
    }

    @PostMapping(value = "/sendContact")
    @ResponseBody
    public ResponseEntity<ContactDto> sendContact(@RequestParam("title") String title, @RequestParam("message") String message) {
        LOGGER.info("[ContactController] sendContact 호출");
        Users usersDto = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContactDto contactDto = new ContactDto();
        contactDto.setTitle(title);
        contactDto.setMessage(message);
        contactDto.setName(usersDto.getName());
        if (usersDto.getName() != "admin") {
            contactDto.setTo_Name("admin");
        }
        ContactDto save_con = contactService.saveContact(contactDto);

        return ResponseEntity.status(HttpStatus.OK).body(save_con);
    }

    @PutMapping(value = "/replyContact")
    @ResponseBody
    public ResponseEntity<ContactReplyDto> contactReply(@RequestParam("num") Long number, @RequestParam("message") String message) {
        ContactReplyDto contactReplyDto = contactService.replyContact(number, message);

        return ResponseEntity.status(HttpStatus.OK).body(contactReplyDto);
    }
}
