package com.ironhack.Homework3.model;


import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.AccountRepository;
import com.ironhack.Homework3.Repository.ContactRepository;
import com.ironhack.Homework3.Repository.OpportunityRepository;
import com.ironhack.Homework3.Repository.SalesRepRepository;
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
class OpportunityTest {
    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;

    Industry industry1;
    SalesRep salesRep1;
    Account account1;
    Contact contact1, contact2, contact3;
    Opportunity opportunity1,opportunity2,opportunity3;


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
        opportunity1 = new Opportunity(Product.BOX,9356, contact1,salesRep1,account1);
        opportunity2 = new Opportunity(Product.FLATBED,30, contact2,salesRep1,account1);
        opportunity3 = new Opportunity(Product.HYBRID,34, contact3,salesRep1,account1);

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
    void testToString_differentOpportunities_returnStringOpportunity() {
        assertEquals("=== Opportunity 1 ===\n- product: BOX\n- trucks quantity: 9356\n" +
                        "- decision maker:  CONTACT 1\n   - name: Alex Bermejo\n   - phone number: +34 652899076\n" +
                        "   - email: alex.bermejo@gmail.com\n   - company name: Accenture\n- status: OPEN\n" +
                        "- salesRep id: 1\n", opportunity1.toString());
        assertEquals("=== Opportunity 2 ===\n- product: FLATBED\n- trucks quantity: 30\n" +
                        "- decision maker:  CONTACT 2\n   - name: Alejandro Hernandez\n   - phone number: " +
                        "+4 652899076\n   - email: alejandrito.divertido@hotmail.com\n   - company name: " +
                        "Accenture\n- status: OPEN\n- salesRep id: 1\n", opportunity2.toString());
        assertEquals("=== Opportunity 3 ===\n- product: HYBRID\n- trucks quantity: 34\n" +
                        "- decision maker:  CONTACT 3\n   - name: Anastasia Garcia\n   - phone number: +34 456735\n" +
                        "   - email: anastigarcA.aLMeid@accenture.com\n   - company name: Accenture\n- status: OPEN\n" +
                        "- salesRep id: 1\n", opportunity3.toString());
    }

    @Test
    void showOpportunity_differentOpportunities_returnIdDecisionMaker() {
        assertEquals("id: 1 -> decision maker: Alex Bermejo",opportunity1.showOpportunity());
        assertEquals("id: 2 -> decision maker: Alejandro Hernandez",opportunity2.showOpportunity());
        assertEquals("id: 3 -> decision maker: Anastasia Garcia",opportunity3.showOpportunity());
    }
}