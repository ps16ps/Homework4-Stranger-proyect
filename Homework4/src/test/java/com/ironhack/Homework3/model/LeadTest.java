package com.ironhack.Homework3.model;

import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.LeadRepository;
import com.ironhack.Homework3.Repository.SalesRepRepository;
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
class LeadTest {

    SalesRep salesRep1;
    Lead lead1,lead2,lead3;
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private SalesRepRepository salesRepRepository;

    @BeforeEach
    void setUp() {
        salesRep1=new SalesRep("Antonio Garcia");
        lead1 = new Lead("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture",salesRep1);
        lead2 = new Lead("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture",salesRep1);
        lead3 = new Lead("Anastasia Garcia","+34 456735",
                "anastigarcA.aLMeid@accenture.com","Accenture",salesRep1);

        salesRepRepository.save(salesRep1);
        leadRepository.saveAll(List.of(lead1, lead2, lead3));
    }

    @AfterEach
    void tearDown(){
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
    }
    @Test
    void showLead_differentContact_returnLead() {
        assertEquals("id: 1 -> name: Alex Bermejo",lead1.showLead());
        assertEquals("id: 2 -> name: Alejandro Hernandez",lead2.showLead());
        assertEquals("id: 3 -> name: Anastasia Garcia",lead3.showLead());
    }

    @Test
    void testToString_differentContacts_returnStringLead() {
        assertEquals("=== Lead 1 ===\n- name: Alex Bermejo\n- phone number: +34 652899076\n" +
                "- email: alex.bermejo@gmail.com\n- company name: Accenture\n- salesRep id: 1\n",lead1.toString());
        assertEquals("=== Lead 2 ===\n- name: Alejandro Hernandez\n- phone number: +4 652899076\n" +
                "- email: alejandrito.divertido@hotmail.com\n- company name: Accenture\n- salesRep id: 1\n",lead2.toString());
        assertEquals("=== Lead 3 ===\n- name: Anastasia Garcia\n- phone number: +34 456735\n" +
                "- email: anastigarcA.aLMeid@accenture.com\n- company name: Accenture\n- salesRep id: 1\n",lead3.toString());
    }
}