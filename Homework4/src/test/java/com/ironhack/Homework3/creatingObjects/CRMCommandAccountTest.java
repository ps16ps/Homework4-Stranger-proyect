package com.ironhack.Homework3.creatingObjects;


import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.AccountRepository;
import com.ironhack.Homework3.Repository.ContactRepository;
import com.ironhack.Homework3.Repository.OpportunityRepository;
import com.ironhack.Homework3.Repository.SalesRepRepository;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.enums.Product;
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
class CRMCommandAccountTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private CRMActions crmCommand;

    Industry industry1;
    Industry industry2;
    Contact contact1,contact2;
    List<Contact> contactList,contactList2;
    SalesRep salesRep;
    Opportunity opportunity1,opportunity2;
    List<Opportunity> opportunityList,opportunityList2;
    Account account1,account2;

    @BeforeEach
    void setUp() {

        industry1 = Industry.ECOMMERCE;
        industry2 = Industry.OTHER;
        account1 = new Account(industry1,456,"Barcelona","España");
        account2 = new Account(industry2,56,"Roma","Italia");

        contact1 = new Contact("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture",account1);
        contact2 = new Contact("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture",account2);

        contactList = List.of(contact1);
        account1.setContactList(contactList);
        contactList2 = List.of(contact2);
        account2.setContactList(contactList2);

        salesRep = new SalesRep("Luli Gil");
        opportunity1 = new Opportunity(Product.BOX,9356, contact1,salesRep,account1);
        opportunity2 = new Opportunity(Product.FLATBED,0, contact2, salesRep, account2);
        opportunityList = List.of(opportunity1);
        account1.setOpportunityList(opportunityList);
        opportunityList2 = List.of(opportunity2);
        account2.setOpportunityList(opportunityList2);

        salesRepRepository.save(salesRep);
        accountRepository.saveAll(List.of(account1,account2));
        contactRepository.saveAll(List.of(contact1,contact2));
        opportunityRepository.saveAll(List.of(opportunity1,opportunity2));

    }

    @AfterEach
    void tearDown() {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        salesRepRepository.deleteAll();
    }

    @Test
    void showAccountsCommand_BadStament_Comment(){
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        String result =  "There are no Accounts yet";
        assertEquals(result,crmCommand.showAccountsCommand()); //no lead yet
    }

    @Test
    void showAccountsCommand_GoodStament_Comment(){
        String result =  "id: 1 -> id of first opportunity: 1\n"+
                "id: 2 -> id of first opportunity: 2\n";
        assertEquals(result,crmCommand.showAccountsCommand());
    }

    @Test
    void lookUpAccountIdCommand_BadStament_Comment(){
        String result1 =  "Id not found, try again";
        assertEquals(result1,crmCommand.lookUpAccountIdCommand(10L));  //no opportunity with that id
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        String result =  "There are no Accounts yet";
        assertEquals(result,crmCommand.lookUpAccountIdCommand(1L)); //no opportunity yet
    }

    @Test
    void lookUpAccountIdCommand_GoodStament_Comment(){
        String result1 = "=== Account 1 ===\n- industry: ECOMMERCE\n- employee count: 456\n- city: " +
                "Barcelona\n- country: España\n- " +
                "id's of the contacts: \n1\n- id's of the opportunities: \n1\n";
        String result2 = "=== Account 2 ===\n- industry: OTHER\n- employee count: 56\n- city: " +
                "Roma\n- country: Italia\n- " +
                "id's of the contacts: \n2\n- id's of the opportunities: \n2\n";
        String string1 = crmCommand.lookUpAccountIdCommand(1L);
        assertEquals(result1,string1);
        String string2 = crmCommand.lookUpAccountIdCommand(2L);
        assertEquals(result2,string2);
    }
    @Test
    void lookUpAccountIdCommand() {
    }

    @Test
    void helpCommand() {
        String string = "=== Possible Commands ===\n"+
                "NEW LEAD -> Add Lead to the CRM system\n" +
                "NEW SALESREP -> Add SalesRep to the CRM system\n" +
                "SHOW LEADS -> Display a list of all the LEADS' id and name\n" +
                "SHOW SALESREP -> Display a list of all the SalesRep' id and name\n"+
                "SHOW CONTACTS -> Display a list of all the CONTACTS' id and name\n" +
                "SHOW OPPORTUNITIES -> Display a list of all the OPPORTUNITIES' id and name of the decision maker\n" +
                "SHOW ACCOUNTS -> Display a list of all the ACCOUNTS id and name of the first contact\n" +
                "LOOKUP LEAD <ID> -> Display the selected LEAD's details with the indicated Id Number\n" +
                "LOOKUP ACCOUNT <ID> -> Display the selected ACCOUNT's details with the indicated Id Number\n" +
                "LOOKUP OPPORTUNITY <ID> -> Display the selected OPPORTUNITY's details with the indicated Id Number\n" +
                "LOOKUP CONTACT <ID> -> Display the selected CONTACT's details with the indicated Id Number\n" +
                "CONVERT <ID> -> Converts the selected LEAD in CONTACT, OPPORTUNITY and ACCOUNT " +
                "and removes it from the system\n" +
                "CLOSE LOST <ID> -> Changes the selected OPPORTUNITY status to CLOSE-LOST\n" +
                "CLOSE WON <ID> -> Changes the selected OPPORTUNITY status to CLOSE-WON\n" +
                "REPORT LEAD BY SALESREP -> A count of Leads by SalesRep\n" +
                "REPORT OPPORTUNITY BY SALESREP -> A count of all Opportunities by SalesRep\n" +
                "REPORT CLOSED-WON BY SALESREP -> A count of all CLOSED_WON Opportunities by SalesRep\n" +
                "REPORT CLOSED-LOST BY SALESREP -> A count of all CLOSED_LOST Opportunities by SalesRep\n" +
                "REPORT OPEN BY SALESREP -> A count of all OPEN Opportunities by SalesRep\n" +
                "REPORT OPPORTUNITY BY THE PRODUCT -> A count of all Opportunities by the product\n" +
                "REPORT CLOSED-WON BY THE PRODUCT -> A count of all CLOSED_WON Opportunities\n" +
                "REPORT CLOSED-LOST THE PRODUCT -> A count of all CLOSED_LOST Opportunities\n" +
                "REPORT OPEN BY THE PRODUCT -> A count of all OPEN Opportunities by the product\n" +
                "REPORT OPPORTUNITY BY COUNTRY -> A count of all Opportunities by country\n" +
                "REPORT CLOSED-WON BY COUNTRY -> A count of all CLOSED_WON Opportunities by country\n" +
                "REPORT CLOSED-LOST BY COUNTRY -> A count of all CLOSED_LOST Opportunities by country\n" +
                "REPORT OPEN BY COUNTRY -> A count of all OPEN Opportunities\n" +
                "REPORT OPPORTUNITY BY CITY -> A count of all Opportunities by the city\n" +
                "REPORT CLOSED-WON BY CITY -> A count of all CLOSED_WON Opportunities by the city\n" +
                "REPORT CLOSED-LOST BY CITY -> A count of all CLOSED_LOST Opportunities by the city\n" +
                "REPORT OPEN BY CITY -> A count of all OPEN Opportunities by the city\n" +
                "REPORT OPPORTUNITY BY INDUSTRY -> A count of all Opportunities by industry\n" +
                "REPORT CLOSED-WON BY INDUSTRY -> A count of all CLOSED_WON Opportunities by industry\n" +
                "REPORT CLOSED-LOST BY INDUSTRY -> A count of all CLOSED_LOST Opportunities by industry\n" +
                "REPORT OPEN BY INDUSTRY -> A count of all OPEN Opportunities by industry\n" +
                "MEAN EMPLOYEECOUNT -> The mean employeeCount\n" +
                "MEDIAN EMPLOYEECOUNT -> The median employeeCount\n" +
                "MAX EMPLOYEECOUNT -> The max employeeCount\n" +
                "MIN EMPLOYEECOUNT -> The min employeeCount\n" +
                "MEAN QUANTITY -> The mean quantity of products order\n" +
                "MEDIAN QUANTITY -> The median quantity of products order\n" +
                "MAX QUANTITY -> The max quantity of products order\n" +
                "MIN QUANTITY -> The min quantity of products order\n" +
                "MEAN OPPS PER ACCOUNT -> The mean number of Opportunities associated with an Account\n" +
                "MEDIAN OPPS PER ACCOUNT -> The median number of Opportunities associated with an Account\n" +
                "MAX OPPS PER ACCOUNT -> The maximum number of Opportunities associated with an Account\n" +
                "MIN OPPS PER ACCOUNT -> The minimum number of Opportunities associated with an Account\n" +
                "QUIT -> Terminates the CRM system";
        assertEquals(string, crmCommand.helpCommand());
    }
}