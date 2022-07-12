package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.model.Contact;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("contact-service")
public interface ContactClient {


    @GetMapping("/contacts")
    List<Contact> findAll();


    @GetMapping("/contacts/{id}")
    Contact findById(@PathVariable Long id);


    @PostMapping("/contacts")
    Contact saveContact(@RequestBody Contact contact);
}