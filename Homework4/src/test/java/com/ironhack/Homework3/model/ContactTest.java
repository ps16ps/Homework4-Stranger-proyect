package com.ironhack.Homework3.model;

import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.AccountRepository;
import com.ironhack.Homework3.Repository.ContactRepository;
import com.ironhack.Homework3.Repository.SalesRepRepository;
import com.ironhack.Homework3.enums.Industry;
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
class ContactTest {

    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;
    Contact contact1,contact2,contact3;
    Industry industry1;
    SalesRep salesRep1;
    Account account1;
    @BeforeEach
    void setUp() {
        salesRep1=new SalesRep("Antonio Garcia");
        industry1 = Industry.ECOMMERCE;
        account1 = new Account(industry1,456,"Barcelona","España");
        contact1 = new Contact("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture",account1);
        contact2 = new Contact("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture",account1);
        contact3 = new Contact("Anastasia Garcia","+34 456735",
                "anastigarcA.aLMeid@accenture.com","Accenture",account1);

        salesRepRepository.save(salesRep1);
        accountRepository.save(account1);
        contactRepository.saveAll(List.of(contact1,contact2,contact3));
    }
    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        salesRepRepository.deleteAll();
    }

    @Test
    void testToString_insertContact_returnStringContact() {
        assertEquals("=== Contact 1 ===\n- name: Alex Bermejo\n- phone number: +34 652899076\n" +
                "- email: alex.bermejo@gmail.com\n- company name: Accenture\n",contact1.toString());
        assertEquals("=== Contact 2 ===\n- name: Alejandro Hernandez\n- phone number: +4 652899076\n" +
                "- email: alejandrito.divertido@hotmail.com\n- company name: Accenture\n",contact2.toString());
        assertEquals("=== Contact 3 ===\n- name: Anastasia Garcia\n- phone number: +34 456735\n" +
                "- email: anastigarcA.aLMeid@accenture.com\n- company name: Accenture\n",contact3.toString());
    }

    @Test
    void printDecisionMaker_differentContacts_returnStringContact() {
        assertEquals(" CONTACT 1\n   - name: Alex Bermejo\n   - phone number: +34 652899076\n" +
                "   - email: alex.bermejo@gmail.com\n   - company name: Accenture\n",contact1.printDecisionMaker());
        assertEquals(" CONTACT 2\n   - name: Alejandro Hernandez\n   - phone number: +4 652899076\n" +
                        "   - email: alejandrito.divertido@hotmail.com\n   - company name: Accenture\n",
                contact2.printDecisionMaker());
        assertEquals(" CONTACT 3\n   - name: Anastasia Garcia\n   - phone number: +34 456735\n" +
                        "   - email: anastigarcA.aLMeid@accenture.com\n   - company name: Accenture\n",
                contact3.printDecisionMaker());
    }

    @Test
    void showContact_differentContact_returnIdName() {
        assertEquals("id: 1 -> name: Alex Bermejo",contact1.showContact());
        assertEquals("id: 2 -> name: Alejandro Hernandez",contact2.showContact());
        assertEquals("id: 3 -> name: Anastasia Garcia",contact3.showContact());
    }


}