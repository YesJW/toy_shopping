package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.ContactDto;
import com.toyshopping.toy_shopping.data.dto.UserDto;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.repository.ContactRepository;
import com.toyshopping.toy_shopping.service.ContactService;
import com.toyshopping.toy_shopping.service.ContactServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping(value = "/getContact")
    public ResponseEntity<List<ContactDto>> getContact() {
        LOGGER.info("[ContactController] getContact 호출");
        User userDto = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ContactDto> contactDtos = contactService.getContact(userDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(contactDtos);

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
}
