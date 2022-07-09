package com.ironhack.Homework3.model;


import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.*;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.enums.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountTest {
    Industry industry1;
    Contact contact1, contact2, contact3;
    List<Contact> contactList;
    Opportunity opportunity1, opportunity2, opportunity3;
    List<Opportunity> opportunityList;
    Account account1;
    SalesRep salesRep1;
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private SalesRepRepository salesRepRepository;

    @BeforeEach
    void setUp() {
        salesRep1 = new SalesRep("Antonio Garcia");
        industry1 = Industry.ECOMMERCE;
        account1 = new Account(industry1, 456, "Barcelona", "España");
        contact1 = new Contact("Alex Bermejo", "+34 652899076",
                "alex.bermejo@gmail.com", "Accenture", account1);
        contact2 = new Contact("Alejandro Hernandez", "+4 652899076",
                "alejandrito.divertido@hotmail.com", "Accenture", account1);
        contact3 = new Contact("Anastasia Garcia", "+34 456735",
                "anastigarcA.aLMeid@accenture.com", "Accenture", account1);
        contactList = List.of(contact1, contact2, contact3);
        opportunity1 = new Opportunity(Product.BOX, 9356, contact1, salesRep1, account1);
        opportunity2 = new Opportunity(Product.FLATBED, 25, contact2, salesRep1, account1);
        opportunity3 = new Opportunity(Product.HYBRID, 34, contact3, salesRep1, account1);
        opportunityList = List.of(opportunity1, opportunity2, opportunity3);
        account1.setOpportunityList(opportunityList);
        account1.setContactList(contactList);

        salesRepRepository.save(salesRep1);
        accountRepository.save(account1);
        contactRepository.saveAll(List.of(contact1,contact2,contact3));
        opportunityRepository.saveAll(List.of(opportunity1,opportunity2,opportunity3));
    }

    @AfterEach
    void tearDown() {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        salesRepRepository.deleteAll();
    }

    @Test
    void testToString() {
        assertEquals("=== Account 1 ===\n- industry: ECOMMERCE\n- employee count: 456\n" +
                "- city: Barcelona\n- country: España\n- " +
                "id's of the contacts: \n1\n2\n3\n" +
                "- id's of the opportunities: \n1\n2\n3\n", account1.toString());
    }

    @Test
    void printIdsContactList() {
        assertEquals("1\n2\n3\n",
                account1.printIdsContactList(opportunityList));
    }

    @Test
    void printIdsOpportunityList() {
        assertEquals("1\n2\n3\n", account1.printIdsOpportunityList(opportunityList));
    }

    @Test
    void showAccount() {
        assertEquals("id: 1 -> id of first opportunity: 1", account1.showAccount());
    }
}