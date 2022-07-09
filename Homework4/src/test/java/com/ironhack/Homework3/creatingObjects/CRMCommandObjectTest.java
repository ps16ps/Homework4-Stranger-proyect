package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.*;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.model.Account;
import com.ironhack.Homework3.model.Contact;
import com.ironhack.Homework3.model.Lead;
import com.ironhack.Homework3.model.SalesRep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CRMCommandObjectTest {

    Industry industry1;
    Account account1;
    SalesRep salesRep;
    Lead lead1, lead2;
    Contact contact1,contact2;

    @Autowired
    private CRMActions crmCommand;

    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;


    @BeforeEach
    void setUp() {

        salesRep = new SalesRep("Luli Gil");
        salesRepRepository.save(salesRep);
        lead1 = new Lead("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture",salesRep);
        lead2 = new Lead("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture",salesRep);
        leadRepository.saveAll(List.of(lead1, lead2));

        industry1 = Industry.ECOMMERCE;
        account1 = new Account(industry1,456,"Barcelona","España");
        contact1 = new Contact("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture", account1);
        contact2 = new Contact("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture", account1);
        account1.setContactList(List.of(contact1,contact2));
        accountRepository.save(account1);
        contactRepository.saveAll(List.of(contact1,contact2));
    }

    @AfterEach
    void tearDown() {
        leadRepository.deleteAll();
        contactRepository.deleteAll();
        salesRepRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void showLeadsCommand_GoodStament_Comment(){
        String result =  "id: 1 -> name: Alex Bermejo" + "\n" +
                "id: 2 -> name: Alejandro Hernandez" + "\n";
        assertEquals(result,crmCommand.showLeadsCommand());
    }

    @Test
    void lookUpLeadIdCommand_BadStament_Comment(){
        String result1 =  "Id not found, try again";
        assertEquals(result1,crmCommand.lookUpLeadIdCommand(10L));  //no opportunity with that id
        leadRepository.deleteAll();
        String result =  "There are no Leads yet";
        assertEquals(result,crmCommand.lookUpLeadIdCommand(1L)); //no opportunity yet
    }

    @Test
    void lookUpLeadIdCommand_GoodStament_Comment(){
        String result1 = "=== Lead 1 ===\n- name: Alex Bermejo\n- phone number: +34 652899076\n" +
                    "- email: alex.bermejo@gmail.com\n- company name: Accenture\n" +
                "- salesRep id: 1";
        String result2 = "=== Lead 2 ===\n- name: Alejandro Hernandez\n- phone number: +4 652899076\n" +
                    "- email: alejandrito.divertido@hotmail.com\n- company name: Accenture\n" +
                "- salesRep id: 1";
        String string1 = crmCommand.lookUpLeadIdCommand(1L).trim();
        assertEquals(result1,string1);
        String string2 = crmCommand.lookUpLeadIdCommand(2L).trim();
        assertEquals(result2,string2);
    }


    @Test
    void showContactsCommand_BadStament_Comment(){
        contactRepository.deleteAll();
        String result =  "There are no Contacts yet";
        assertEquals(result,crmCommand.showContactsCommand()); //no lead yet
    }

    @Test
    void showContactsCommand_GoodStament_Comment(){
        String result =  "id: 1 -> name: Alex Bermejo" + "\n" +
                "id: 2 -> name: Alejandro Hernandez" + "\n";
        assertEquals(result,crmCommand.showContactsCommand());
    }

    @Test
    void lookUpContactIdCommand_BadStament_Comment(){
        String result1 =  "Id not found, try again";
        assertEquals(result1,crmCommand.lookUpContactIdCommand(10L));  //no opportunity with that id
        contactRepository.deleteAll();
        String result =  "There are no Contacts yet";
        assertEquals(result,crmCommand.lookUpContactIdCommand(100000L)); //no opportunity yet
    }

    @Test
    void lookUpContactIdCommand_GoodStament_Comment(){
        String result1 = "=== Contact 1 ===\n- name: Alex Bermejo\n- phone number: +34 652899076\n" +
                "- email: alex.bermejo@gmail.com\n- company name: Accenture\n";
        String result2 = "=== Contact 2 ===\n- name: Alejandro Hernandez\n- phone number: +4 652899076\n" +
                "- email: alejandrito.divertido@hotmail.com\n- company name: Accenture\n";
        String string1 = crmCommand.lookUpContactIdCommand(1L);
        assertEquals(result1,string1);
        String string2 = crmCommand.lookUpContactIdCommand(2L);
        assertEquals(result2,string2);
    }

}