package com.ironhack.Homework3.creatingObjects;


import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.*;
import com.ironhack.Homework3.model.*;
import com.ironhack.Homework3.enums.Command;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.enums.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CRMCommandMenuTest {
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
    @Autowired
    private CRMActions crmActions;
    @Autowired
    private CRMMenu crmCommand;
    Lead lead1, lead2;
    Industry industry1;
    Contact contact1,contact2;
    SalesRep salesRep1;
    List<Contact> contactList;
    Opportunity opportunity1,opportunity2;
    List<Opportunity> opportunityList;
    Account account1;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        salesRep1=new SalesRep("Antonio Garcia");
        lead1 = new Lead("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture",salesRep1);
        lead2 = new Lead("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture",salesRep1);
        industry1 = Industry.ECOMMERCE;
        account1 = new Account(industry1,456,"Barcelona","España");
        contact1 = new Contact("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture",account1);
        contact2 = new Contact("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture",account1);
        contactList = List.of(contact1,contact2);
        opportunity1 = new Opportunity(Product.BOX,9356, contact1,salesRep1,account1);
        opportunity2 = new Opportunity(Product.FLATBED,30, contact2,salesRep1,account1);
        opportunityList = List.of(opportunity1,opportunity2);
        account1.setOpportunityList(opportunityList);
        account1.setContactList(contactList);

        salesRepRepository.save(salesRep1);
        leadRepository.saveAll(List.of(lead1,lead2));
        accountRepository.save(account1);
        contactRepository.saveAll(List.of(contact1,contact2));
        opportunityRepository.saveAll(List.of(opportunity1,opportunity2));

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);

        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
    }

    @Test
    void getCommandString_OnlyLetters_CorrectOutput(){
        assertEquals("CLOSE_WON", crmCommand.getCommandString("close won"));
        assertEquals("CONVERT", crmCommand.getCommandString("  convert "));
    }

    @Test
    void getCommandString_LettersAndNumbers_CorrectOutput(){
        assertEquals("CLOSE_WON", crmCommand.getCommandString("close won 12"));
        assertEquals("CONVERT", crmCommand.getCommandString(" convert 5"));
    }

    @Test
    void getCommandString_OnlyNumbers_EmptyOutput(){
        assertEquals("", crmCommand.getCommandString("12"));
    }

    @Test
    void getCommandString_OnlySpaces_EmptyOutput(){
        assertEquals("", crmCommand.getCommandString("  "));
    }

    @Test
    void getCommandString_EmptyString_EmptyOutput(){
        assertEquals("", crmCommand.getCommandString(""));
    }

    @Test
    void getCommandId_OnlyLetters_NegativeOutput(){
        assertEquals(-1, crmCommand.getCommandId("close won"));
    }

    @Test
    void getCommandId_LettersAndNumbers_CorrectOutput(){
        assertEquals(12, crmCommand.getCommandId("close won 12"));
        assertEquals(5, crmCommand.getCommandId(" convert 5 "));
    }

    @Test
    void getCommandId_OnlyNumbers_CorrectOutput(){
        assertEquals(12, crmCommand.getCommandId("12"));
        assertEquals(5, crmCommand.getCommandId("  5  "));
    }

    @Test
    void getCommandId_OnlySpaces_NegativeOutput(){
        assertEquals(-1L, crmCommand.getCommandId("  "));
    }

    @Test
    void getCommandId_EmptyString_NegativeOutput(){
        assertEquals(-1L, crmCommand.getCommandId(""));
    }

    @Test
    void menuActions_showExistingSalesRep_SalesRep() {
        assertEquals("id: 1 -> name: Antonio Garcia" , crmActions.showSalesRepCommand().trim());
    }

    @Test
    void menuActions_showNoSalesRep_SalesRep() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.SHOW_SALESREPS,-1L);
        assertEquals("There are no SalesReps yet" , outContent.toString().trim());
    }


    @Test
    void menuActions_showExistingLeads_LeadMap() {
        assertEquals("id: 1 -> name: Alex Bermejo" + "\n" +
                "id: 2 -> name: Alejandro Hernandez" , crmActions.showLeadsCommand().trim());
    }

    @Test
    void menuActions_showNoLeads_LeadMap() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.SHOW_LEADS,-1L);
        assertEquals("There are no Leads yet" , outContent.toString().trim());
    }

    @Test
    void menuActions_showExistingContacts_ContactMap() throws InterruptedException {
        crmCommand.menuActions(Command.SHOW_CONTACTS,-1L);
        assertEquals("id: 1 -> name: Alex Bermejo" + "\n" +
                "id: 2 -> name: Alejandro Hernandez" , outContent.toString().trim());
    }

    @Test
    void menuActions_showNoContacts_ContactMap() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.SHOW_CONTACTS,-1L);
        assertEquals("There are no Contacts yet" , outContent.toString().trim());
    }

    @Test
    void menuActions_showExistingOpportunites_OpportunityMap() throws InterruptedException {
        crmCommand.menuActions(Command.SHOW_OPPORTUNITIES,-1L);
        assertEquals("id: 1 -> decision maker: Alex Bermejo" + "\n" +
                "id: 2 -> decision maker: Alejandro Hernandez" , outContent.toString().trim());
    }

    @Test
    void menuActions_showNoOpportunities_OpportunityMap() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.SHOW_OPPORTUNITIES,-1L);
        assertEquals("There are no Opportunities yet" , outContent.toString().trim());
    }

    @Test
    void menuActions_showExistingAccounts_AccountMap() throws InterruptedException {
        crmCommand.menuActions(Command.SHOW_ACCOUNTS,-11L);
        assertEquals("id: 1 -> id of first opportunity: 1" , outContent.toString().trim());
    }

    @Test
    void menuActions_showNoAccounts_AccountMap() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.SHOW_ACCOUNTS,-1L);
        assertEquals("There are no Accounts yet" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpExistingLeads_LeadMap() throws InterruptedException {
        crmCommand.menuActions(Command.LOOKUP_LEAD,1L);
        assertEquals("=== Lead 1 ===\n- name: Alex Bermejo\n- phone number: +34 652899076\n" +
                "- email: alex.bermejo@gmail.com\n- company name: Accenture\n- salesRep id: 1" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpNoExistingLeads_LeadMap() throws InterruptedException {
        crmCommand.menuActions(Command.LOOKUP_LEAD,20L);
        assertEquals("Id not found, try again" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpNoLeads_LeadMap() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.LOOKUP_LEAD,1L);
        assertEquals("There are no Leads yet" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpExistingContacts_ContactMap() throws InterruptedException {
        crmCommand.menuActions(Command.LOOKUP_CONTACT,1L);
        assertEquals("=== Contact 1 ===\n- name: Alex Bermejo\n- phone number: +34 652899076\n" +
        "- email: alex.bermejo@gmail.com\n- company name: Accenture" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpNoExistingContacts_ContactMap() throws InterruptedException {
        crmCommand.menuActions(Command.LOOKUP_CONTACT,20L);
        assertEquals("Id not found, try again" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpNoContacts_ContactMap() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.LOOKUP_CONTACT,100000L);
        assertEquals("There are no Contacts yet" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpExistingOpportunites_OpportunityMap() throws InterruptedException {
        crmCommand.menuActions(Command.LOOKUP_OPPORTUNITY,1L);
        assertEquals("=== Opportunity 1 ===\n- product: BOX\n- trucks quantity: 9356\n" +
                "- decision maker:  CONTACT 1\n   - name: Alex Bermejo\n   - phone number: +34 652899076\n" +
                "   - email: alex.bermejo@gmail.com\n   - company name: Accenture\n- status: OPEN\n" +
                "- salesRep id: 1" ,
                outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpNoExistingOpportunites_OpportunityMap() throws InterruptedException {
        crmCommand.menuActions(Command.LOOKUP_OPPORTUNITY,200020L);
        assertEquals("Id not found, try again" ,
                outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpNoOpportunities_OpportunityMap() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.LOOKUP_OPPORTUNITY,1L);
        assertEquals("There are no Opportunities yet" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpExistingAccounts_AccountMap() throws InterruptedException {
        crmCommand.menuActions(Command.LOOKUP_ACCOUNT,1L);
        assertEquals("=== Account 1 ===\n- industry: ECOMMERCE\n- employee count: 456\n- city: " +
                "Barcelona\n- country: España\n- " +
                "id's of the contacts: \n1\n2\n- id's of the opportunities: \n1\n2" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpNoExistingAccounts_AccountMap() throws InterruptedException {
        crmCommand.menuActions(Command.LOOKUP_ACCOUNT,30L);
        assertEquals("Id not found, try again" , outContent.toString().trim());
    }

    @Test
    void menuActions_LookUpNoAccounts_AccountMap() throws InterruptedException {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        salesRepRepository.deleteAll();
        crmCommand.menuActions(Command.LOOKUP_ACCOUNT,1L);
        assertEquals("There are no Accounts yet" , outContent.toString().trim());
    }

    @Test
    void menuActions_CloseWonExistingOpportunity_CloseWonMessage() throws InterruptedException {
        crmCommand.menuActions(Command.CLOSE_WON,1L);
        assertEquals("Status of Opportunity 1 changed to CLOSED_WON", outContent.toString().trim());
    }

    @Test
    void menuActions_CloseWonNoExistingOpportunity_CloseWonMessage() throws InterruptedException {
        crmCommand.menuActions(Command.CLOSE_WON,1L);
        assertEquals("Status of Opportunity 1 changed to CLOSED_WON", outContent.toString().trim());
    }

    @Test
    void menuActions_CloseLostNoExistingOpportunity_CloseLostMessage() throws InterruptedException {
        crmCommand.menuActions(Command.CLOSE_LOST,1L);
        assertEquals("Status of Opportunity 1 changed to CLOSED_LOST", outContent.toString().trim());
    }

    @Test
    void menuActions_helpCommand_HelpMessage() throws InterruptedException {
        crmCommand.menuActions(Command.HELP,-1L);
        assertEquals("=== Possible Commands ===\n"+
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
                "QUIT -> Terminates the CRM system",outContent.toString().trim());
    }
}