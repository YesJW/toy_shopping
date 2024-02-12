package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.ContactAdminDto;
import com.toyshopping.toy_shopping.data.dto.ContactDto;
import com.toyshopping.toy_shopping.data.dto.ContactReplyDto;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.service.ContactService;
import com.toyshopping.toy_shopping.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<ContactDto>> getAllContact() {

        List<ContactDto> contactDtos = contactService.getAllContact();
        return ResponseEntity.status(HttpStatus.OK).body(contactDtos);

    }

    @GetMapping(value = "/getContact")
    @ResponseBody
    public ResponseEntity<ContactDto> getContact(@RequestParam Long num) {
        LOGGER.info("[ContactController] getContact 호출");

        ContactDto contactDto = contactService.getContact(num);

        return ResponseEntity.status(HttpStatus.OK).body(contactDto);

    }

    @PostMapping(value = "/sendContact")
    @ResponseBody
    public ResponseEntity<ContactDto> sendContact(@RequestParam("title") String title, @RequestParam("message") String message) {
        ContactDto contactDto = new ContactDto();
        contactDto.setTitle(title);
        contactDto.setMessage(message);
        ContactDto save_con = contactService.sendContact(contactDto);

        return ResponseEntity.status(HttpStatus.OK).body(save_con);
    }

    @PutMapping(value = "/replyContact")
    @ResponseBody
    public ResponseEntity<ContactReplyDto> contactReply(@RequestParam("num") Long number, @RequestParam("message") String message) {
        ContactReplyDto contactReplyDto = contactService.replyContact(number, message);

        return ResponseEntity.status(HttpStatus.OK).body(contactReplyDto);
    }
}
