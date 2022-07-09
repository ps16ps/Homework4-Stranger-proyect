package com.ironhack.Homework3.creatingObjects;


import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.AccountRepository;
import com.ironhack.Homework3.Repository.ContactRepository;
import com.ironhack.Homework3.Repository.OpportunityRepository;
import com.ironhack.Homework3.Repository.SalesRepRepository;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.enums.Product;
import com.ironhack.Homework3.enums.Status;
import com.ironhack.Homework3.model.Account;
import com.ironhack.Homework3.model.Contact;
import com.ironhack.Homework3.model.Opportunity;
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
class CRMCommandOpportunityTest {
    @Autowired
    private CRMActions crmCommand;
    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;
    Opportunity opportunity1, opportunity2;
    Contact contact1, contact2;
    SalesRep salesRep;
    Account account;

    @BeforeEach
    void setUp() {
        salesRep = new SalesRep("Luli Garcia");
        account = new Account(Industry.MEDICAL,12,"Barcelona","Spain");

        contact1 = new Contact("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture",account);
        contact2 = new Contact("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture",account);
        opportunity1 = new Opportunity(Product.BOX,9356, contact1,salesRep,account);
        opportunity2 = new Opportunity(Product.FLATBED,30, contact2,salesRep,account);

        salesRepRepository.save(salesRep);
        accountRepository.save(account);
        contactRepository.saveAll(List.of(contact1,contact2));
        opportunityRepository.saveAll(List.of(opportunity1,opportunity2));
    }

    @AfterEach
    void tearDown() {
        opportunityRepository.saveAll(List.of(opportunity1,opportunity2));
        contactRepository.saveAll(List.of(contact1,contact2));
        accountRepository.save(account);
        salesRepRepository.save(salesRep);
    }

    @Test
    void closeWonIdCommand_BadStament_Comment(){
        String result1 =  "Id not found, try again";
        assertEquals(result1,crmCommand.closeWonIdCommand(10L));  //no opportunity with that id
        opportunityRepository.deleteAll();
        String result =  "There are no Opportunities yet";
        assertEquals(result,crmCommand.closeWonIdCommand(100000L)); //no opportunity yet
    }

    @Test
    void closeWonIdCommand_GoodStament_StatusCloseWon(){
        Status result = Status.CLOSED_WON;
        String string1 = crmCommand.closeWonIdCommand(1L);
        String stringResult1 = "Status of Opportunity 1 changed to CLOSED_WON";
        assertEquals(stringResult1,string1);
        Status opportunityStatus1 = opportunityRepository.findById(1L).get().getStatus();
        assertEquals(result,opportunityStatus1);
        String string2 = crmCommand.closeWonIdCommand(2L);
        String stringResult2 = "Status of Opportunity 2 changed to CLOSED_WON";
        assertEquals(stringResult2,string2);
        Status opportunityStatus2 = opportunityRepository.findById(2L).get().getStatus();
        assertEquals(result,opportunityStatus2);
    }

    @Test
    void closeLostIdCommand_BadStament_Comment(){
        String result1 =  "Id not found, try again";
        assertEquals(result1,crmCommand.closeLostIdCommand(10L));  //no opportunity with that id
        opportunityRepository.deleteAll();
        String result =  "There are no Opportunities yet";
        assertEquals(result,crmCommand.closeLostIdCommand(200000L)); //no opportunity yet
    }

    @Test
    void closeLostIdCommand_GoodStament_StatusCloseWon(){
        Status result = Status.CLOSED_LOST;
        String string1 = crmCommand.closeLostIdCommand(1L);
        String stringResult1 = "Status of Opportunity 1 changed to CLOSED_LOST";
        assertEquals(stringResult1,string1);
        Status opportunityStatus1 = opportunityRepository.findById(1L).get().getStatus();
        assertEquals(result,opportunityStatus1);
        String string2 = crmCommand.closeLostIdCommand(2L);
        String stringResult2 = "Status of Opportunity 2 changed to CLOSED_LOST";
        assertEquals(stringResult2,string2);
        Status opportunityStatus2 = opportunityRepository.findById(2L).get().getStatus();
        assertEquals(result,opportunityStatus2);
    }

    @Test
    void showOpportunitiesCommand_BadStament_Comment(){
        opportunityRepository.deleteAll();
        String result =  "There are no Opportunities yet";
        assertEquals(result,crmCommand.showOpportunitiesCommand()); //no opportunity yet
    }

    @Test
    void showOpportunitiesCommand_GoodStament_Comment(){
        String result =  "id: 1 -> decision maker: Alex Bermejo" + "\n" +
                "id: 2 -> decision maker: Alejandro Hernandez" + "\n";
        assertEquals(result,crmCommand.showOpportunitiesCommand());
    }

    @Test
    void lookUpOpportunityIdCommand_BadStament_Comment(){
        String result1 =  "Id not found, try again";
        assertEquals(result1,crmCommand.lookUpOpportunityIdCommand(10L));//no opportunity with that id
        opportunityRepository.deleteAll();
        String result =  "There are no Opportunities yet";
        assertEquals(result,crmCommand.lookUpOpportunityIdCommand(100000L)); //no opportunity yet
    }

    @Test
    void lookUpOpportunityIdCommand_GoodStament_Comment(){
        String result2 = "=== Opportunity 1 ===\n- product: BOX\n- trucks quantity: 9356\n" +
                "- decision maker:  CONTACT 1\n   - name: Alex Bermejo\n   - phone number: +34 652899076\n" +
                "   - email: alex.bermejo@gmail.com\n   - company name: Accenture\n- status: OPEN\n" +
                "- salesRep id: 1";

        String result = "=== Opportunity 2 ===\n- product: FLATBED\n- trucks quantity: 30\n" +
                "- decision maker:  CONTACT 2\n   - name: Alejandro Hernandez\n   - phone number: +4 652899076\n" +
                "   - email: alejandrito.divertido@hotmail.com\n   - company name: Accenture\n- status: OPEN\n" +
                "- salesRep id: 1";
        String string1 = crmCommand.lookUpOpportunityIdCommand(1L);
        assertEquals(result2,string1.trim());
        String string2 = crmCommand.lookUpOpportunityIdCommand(2L);
        assertEquals(result,string2.trim());
    }
}