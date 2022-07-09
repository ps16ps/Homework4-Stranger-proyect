package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.*;
import com.ironhack.Homework3.enums.Command;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.enums.Product;
import com.ironhack.Homework3.model.*;
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
public class CRMCommandQuerysTest {

    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private CRMMenu crmMenu;

    Industry industry1, industry2;
    SalesRep salesRep1, salesRep2;
    Lead lead;
    Account account1,account2;
    Contact contact1,contact2,contact3;
    List<Contact> contactList1, contactList2;
    Opportunity opportunity1, opportunity2, opportunity3;
    List<Opportunity> opportunityList1, opportunityList2;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        salesRep1 = new SalesRep("Luli Gil");
        salesRep2 = new SalesRep("Antonio Garcia");

        lead = new Lead("Pepa Martín","+65 5685485","pepa@martin.com",
                "Ironhack", salesRep1);

        industry1 = Industry.ECOMMERCE;
        industry2 = Industry.OTHER;
        account1 = new Account(industry1,456,"Barcelona","España");
        account2 = new Account(industry2,56,"Roma","Italia");

        contact1 = new Contact("Alex Bermejo","+34 652899076",
                "alex.bermejo@gmail.com","Accenture",account1);
        contact2 = new Contact("Alejandro Hernandez","+4 652899076",
                "alejandrito.divertido@hotmail.com","Accenture",account2);
        contact3 = new Contact("Pepe Roma","+63 6225454",
                "pepe.roma@gmail.com","Ironhack",account2);

        contactList1 = List.of(contact1);
        account1.setContactList(contactList1);
        contactList2 = List.of(contact2,contact3);
        account2.setContactList(contactList2);

        opportunity1 = new Opportunity(Product.BOX,9356, contact1,salesRep1,account1);
        opportunity2 = new Opportunity(Product.FLATBED,0, contact2, salesRep2, account2);
        opportunity3 = new Opportunity(Product.FLATBED,22, contact3, salesRep2, account2);
        opportunityList1 = List.of(opportunity1);
        account1.setOpportunityList(opportunityList1);
        opportunityList2 = List.of(opportunity2,opportunity3);
        account2.setOpportunityList(opportunityList2);

        salesRepRepository.saveAll(List.of(salesRep1,salesRep2));
        leadRepository.save(lead);
        accountRepository.saveAll(List.of(account1,account2));
        contactRepository.saveAll(List.of(contact1,contact2,contact3));
        opportunityRepository.saveAll(List.of(opportunity1,opportunity2,opportunity3));

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
    void menuActions_ReportLeadBySalesRep() throws InterruptedException {
        String result = "Sales Rep id | Number of Leads"+ System.lineSeparator() + "1|1";
        crmMenu.menuActions(Command.REPORT_LEAD_BY_SALESREP,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpportunityBySalesRep() throws InterruptedException {
        String result = "Sales Rep id | Number of Opportunities"+ System.lineSeparator() + "1|1" +
                System.lineSeparator() + "2|2";
        crmMenu.menuActions(Command.REPORT_OPPORTUNITY_BY_SALESREP,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedLostBySalesRep() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_LOST,1L);
        System.setOut(new PrintStream(outContent));
        String result = "Sales Rep id | Number of Closed-Lost Opportunities"+ System.lineSeparator() + "1|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_LOST_BY_SALESREP,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedWonBySalesRep() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "Sales Rep id | Number of Closed-Won Opportunities"+ System.lineSeparator() + "2|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_WON_BY_SALESREP,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpenBySalesRep() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "Sales Rep id | Number of Open Opportunities"+ System.lineSeparator() + "1|1" +
                System.lineSeparator() + "2|1";
        crmMenu.menuActions(Command.REPORT_OPEN_BY_SALESREP,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpportunityByProduct() throws InterruptedException {
        String result = "Product name | Number of Opportunities"+ System.lineSeparator() + "BOX|1" +
                System.lineSeparator() + "FLATBED|2";
        crmMenu.menuActions(Command.REPORT_OPPORTUNITY_BY_THE_PRODUCT,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedLostByProduct() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_LOST,1L);
        System.setOut(new PrintStream(outContent));
        String result = "Product name | Number of Closed-Lost Opportunities"+ System.lineSeparator() + "BOX|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_LOST_BY_THE_PRODUCT,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedWonByProduct() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "Product name | Number of Closed-Won Opportunities"+ System.lineSeparator() + "FLATBED|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_WON_BY_THE_PRODUCT,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpenByProduct() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "Product name | Number of Open Opportunities"+ System.lineSeparator() + "BOX|1" +
                System.lineSeparator() + "FLATBED|1";
        crmMenu.menuActions(Command.REPORT_OPEN_BY_THE_PRODUCT,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpportunityByCountry() throws InterruptedException {
        String result = "Country name | Number of Opportunities"+ System.lineSeparator() + "España|1" +
                System.lineSeparator() + "Italia|2";
        crmMenu.menuActions(Command.REPORT_OPPORTUNITY_BY_COUNTRY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedLostByCountry() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_LOST,1L);
        System.setOut(new PrintStream(outContent));
        String result = "Country name | Number of Closed-Lost Opportunities"+ System.lineSeparator() + "España|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_LOST_BY_COUNTRY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedWonByCountry() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "Country name | Number of Closed-Won Opportunities"+ System.lineSeparator() + "Italia|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_WON_BY_COUNTRY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpenByCountry() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "Country name | Number of Open Opportunities"+ System.lineSeparator() + "España|1" +
                System.lineSeparator() + "Italia|1";
        crmMenu.menuActions(Command.REPORT_OPEN_BY_COUNTRY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpportunityByCity() throws InterruptedException {
        String result = "City name | Number of Opportunities"+ System.lineSeparator() + "Barcelona|1" +
                System.lineSeparator() + "Roma|2";
        crmMenu.menuActions(Command.REPORT_OPPORTUNITY_BY_CITY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedLostByCity() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_LOST,1L);
        System.setOut(new PrintStream(outContent));
        String result = "City name | Number of Closed-Lost Opportunities"+ System.lineSeparator() + "Barcelona|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_LOST_BY_CITY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedWonByCity() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "City name | Number of Closed-Won Opportunities"+ System.lineSeparator() + "Roma|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_WON_BY_CITY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpenByCity() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "City name | Number of Open Opportunities"+ System.lineSeparator() + "Barcelona|1" +
                System.lineSeparator() + "Roma|1";
        crmMenu.menuActions(Command.REPORT_OPEN_BY_CITY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpportunityByIndustry() throws InterruptedException {
        String result = "Industry name | Number of Opportunities"+ System.lineSeparator() + "ECOMMERCE|1" +
                System.lineSeparator() + "OTHER|2";
        crmMenu.menuActions(Command.REPORT_OPPORTUNITY_BY_INDUSTRY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedLostByIndustry() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_LOST,1L);
        System.setOut(new PrintStream(outContent));
        String result = "Industry name | Number of Closed-Lost Opportunities"+ System.lineSeparator() + "ECOMMERCE|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_LOST_BY_INDUSTRY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportClosedWonByIndustry() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "Industry name | Number of Closed-Won Opportunities"+ System.lineSeparator() + "OTHER|1";
        crmMenu.menuActions(Command.REPORT_CLOSED_WON_BY_INDUSTRY,-1L);
        assertEquals(result, outContent.toString().trim());
    }

    @Test
    void menuActions_ReportOpenByIndustry() throws InterruptedException {
        System.setOut(originalOut);
        crmMenu.menuActions(Command.CLOSE_WON,2L);
        System.setOut(new PrintStream(outContent));
        String result = "Industry name | Number of Open Opportunities"+ System.lineSeparator() + "ECOMMERCE|1" +
                System.lineSeparator() + "OTHER|1";
        crmMenu.menuActions(Command.REPORT_OPEN_BY_INDUSTRY,-1L);
        assertEquals(result, outContent.toString().trim());
    }
}
