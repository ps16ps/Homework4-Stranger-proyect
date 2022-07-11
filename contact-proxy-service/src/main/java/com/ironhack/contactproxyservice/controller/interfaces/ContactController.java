package com.ironhack.contactproxyservice.controller.interfaces;

import com.ironhack.contactproxyservice.model.Contact;

import java.util.List;

public interface ContactController {
    List<Contact> findAll();
    Contact findById(Long id);
    Contact saveContact(Contact contact);
}
