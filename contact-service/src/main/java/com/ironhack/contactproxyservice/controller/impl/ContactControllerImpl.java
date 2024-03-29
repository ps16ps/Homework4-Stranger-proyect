package com.ironhack.contactproxyservice.controller.impl;

import com.ironhack.contactproxyservice.controller.interfaces.ContactController;
import com.ironhack.contactproxyservice.model.Contact;
import com.ironhack.contactproxyservice.repository.ContactRepository;
import com.ironhack.contactproxyservice.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContactControllerImpl implements ContactController {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts")
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> findAll() {
        List<Contact> contactList = contactRepository.findAll();
        return contactList;
    }

    @GetMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Contact findById(@PathVariable Long id) {
        return contactService.findById(id);
    }

    @PostMapping("/contacts")
    @ResponseStatus (HttpStatus.CREATED)
    public Contact saveContact (@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
}

