package com.ironhack.contactproxyservice.repository;


import com.ironhack.contactproxyservice.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAll();
    Optional<Contact> findById(Long id);
    Contact saveContact(Contact contact);
}
