package com.ironhack.contactproxyservice.service;

import com.ironhack.contactproxyservice.model.Contact;
import com.ironhack.contactproxyservice.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact findById(Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact with id " + id + " not found"));
        return contact;
    }
}
