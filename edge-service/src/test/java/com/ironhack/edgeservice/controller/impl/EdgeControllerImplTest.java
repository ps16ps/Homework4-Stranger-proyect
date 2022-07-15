package com.ironhack.edgeservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.controller.dto.ConvertDTO;
import com.ironhack.edgeservice.controller.dto.StatusDTO;
import com.ironhack.edgeservice.controller.interfaces.EdgeController;
import com.ironhack.edgeservice.enums.Industry;
import com.ironhack.edgeservice.enums.Product;
import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.model.*;
import com.ironhack.edgeservice.repository.AccountRepository;
import com.ironhack.edgeservice.repository.EdgeRepository;
import com.ironhack.edgeservice.service.interfaces.EdgeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class EdgeControllerImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ApplicationContext context;

    @MockBean
    private LeadClient mockLeadClient;
    @MockBean
    private SalesRepClient mockSalesRepClient;
    @MockBean
    private ContactClient mockContactClient;
    @MockBean
    private OpportunityClient mockOpportunityClient;
    @MockBean
    private AccountClient mockAccountClient;

    @Autowired
    private EdgeRepository edgeRepository;
    @Autowired
    private AccountRepository accountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    SalesRep salesRep1, salesRep2;
    Lead lead1, lead2;
    Contact contact1, contact2;
    Opportunity opportunity1, opportunity2, opportunity3;
    Account account1, account2;
    @BeforeEach
    void setUp() {
        salesRep1 = new SalesRep("Lia");
        salesRep2 = new SalesRep("Pep");

        lead1 = new Lead("Steve", "+34 6585698", "steve@gamil.com",
                "Accenture", 1L);
        lead2 = new Lead("Jeff", "+34 6899987", "jeff@gamil.com",
                "Accenture", 2L);

        account1 = new Account(Industry.MEDICAL, 10,"Barcelona", "Spain");
        account2 = new Account(Industry.ECOMMERCE, 20,"Roma", "Italy");

        contact1 = new Contact("Alba","666666999","alba@gmail.com",
                "albaCompany",1L);
        contact2 = new Contact("Iñaki","666666888","iñaki@gmail.com",
                "IñakiCompany",2L);

        opportunity1 = new Opportunity(Product.BOX,10,1L,1L,1L);
        opportunity2 = new Opportunity(Product.FLATBED,20,1L,1L,2L);
        opportunity2.setStatus(Status.CLOSED_WON);
        opportunity3 = new Opportunity(Product.FLATBED,30,1L,2L,2L);
        opportunity3.setStatus(Status.CLOSED_LOST);

        accountRepository.saveAll(List.of(account1,account2));
        edgeRepository.saveAll(List.of(opportunity1,opportunity2,opportunity3));
    }

    @AfterEach
    void tearDown() {
        edgeRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void getAllLeads() {
        Mockito.when(mockLeadClient.getAllLead()).thenReturn(List.of(lead1,lead2));

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Lead> leads = edgeController.getAllLeads();

        assertEquals(List.of(lead1,lead2), leads);
        Mockito.verify(mockLeadClient).getAllLead();
    }

    @Test
    void getAllSalesRep() {
        Mockito.when(mockSalesRepClient.getAllSalesRep()).thenReturn(List.of(salesRep1,salesRep2));

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<SalesRep> salesReps = edgeController.getAllSalesRep();

        assertEquals(List.of(salesRep1,salesRep2), salesReps);
        Mockito.verify(mockSalesRepClient).getAllSalesRep();
    }

    @Test
    void getAllContacts() {
        Mockito.when(mockContactClient.findAll()).thenReturn(List.of(contact1,contact2));

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Contact> contacts = edgeController.getAllContacts();

        assertEquals(List.of(contact1,contact2), contacts);
        Mockito.verify(mockContactClient).findAll();
    }

    @Test
    void getAllOpportunities() {
        Mockito.when(mockOpportunityClient.getAllOpportunities())
                .thenReturn(List.of(opportunity1,opportunity2,opportunity3));

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Opportunity> opportunities = edgeController.getAllOpportunities();

        assertEquals(List.of(opportunity1,opportunity2,opportunity3), opportunities);
        Mockito.verify(mockOpportunityClient).getAllOpportunities();
    }

    @Test
    void getAllAccounts() {
        Mockito.when(mockAccountClient.getAllAccount()).thenReturn(List.of(account1,account2));

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Account> accounts = edgeController.getAllAccounts();

        assertEquals(List.of(account1,account2), accounts);
        Mockito.verify(mockAccountClient).getAllAccount();
    }

    @Test
    void getLeadsById() {
        Mockito.when(mockLeadClient.getLeadById(lead1.getId())).thenReturn(lead1);

        EdgeController edgeController = context.getBean(EdgeController.class);
        Lead lead = edgeController.getLeadsById(lead1.getId());

        assertEquals(lead1, lead);
        Mockito.verify(mockLeadClient).getLeadById(lead1.getId());
    }

    @Test
    void getSalesRepsById() {
        Mockito.when(mockSalesRepClient.getSalesRepById(salesRep1.getId())).thenReturn(salesRep1);

        EdgeController edgeController = context.getBean(EdgeController.class);
        SalesRep salesRep = edgeController.getSalesRepsById(salesRep1.getId());

        assertEquals(salesRep1, salesRep);
        Mockito.verify(mockSalesRepClient).getSalesRepById(salesRep1.getId());
    }

    @Test
    void getContactsById() {
        Mockito.when(mockContactClient.findById(contact1.getId())).thenReturn(contact1);

        EdgeController edgeController = context.getBean(EdgeController.class);
        Contact contact = edgeController.getContactsById(contact1.getId());

        assertEquals(contact1, contact);
        Mockito.verify(mockContactClient).findById(contact1.getId());
    }

    @Test
    void getOpportunitiesById() {
        Mockito.when(mockOpportunityClient.getOpportunityById(opportunity1.getId()))
                .thenReturn(opportunity1);

        EdgeController edgeController = context.getBean(EdgeController.class);
        Opportunity opportunity = edgeController.getOpportunitiesById(opportunity1.getId());

        assertEquals(opportunity1, opportunity);
        Mockito.verify(mockOpportunityClient).getOpportunityById(opportunity1.getId());
    }

    @Test
    void getAccountsById() {
        Mockito.when(mockAccountClient.getAccountById(account1.getId())).thenReturn(account1);

        EdgeController edgeController = context.getBean(EdgeController.class);
        Account account = edgeController.getAccountsById(account1.getId());

        assertEquals(account1, account);
        Mockito.verify(mockAccountClient).getAccountById(account1.getId());
    }

    @Test
    void getAvgQuantity() {
        Mockito.when(mockOpportunityClient.getAvgQuantity())
                .thenReturn(20.0);

        EdgeController edgeController = context.getBean(EdgeController.class);
        Double avg = edgeController.getAvgQuantity();
        Double mean = ((double) opportunity1.getQuantity() + (double)  opportunity2.getQuantity()
                + (double) opportunity3.getQuantity())/3;

        assertEquals(mean, avg);
        Mockito.verify(mockOpportunityClient).getAvgQuantity();
    }

    @Test
    void getMaxQuantity() {
        Mockito.when(mockOpportunityClient.getMaxQuantity())
                .thenReturn(30);

        EdgeController edgeController = context.getBean(EdgeController.class);
        int max = edgeController.getMaxQuantity();

        assertEquals(opportunity3.getQuantity(), max);
        Mockito.verify(mockOpportunityClient).getMaxQuantity();
    }

    @Test
    void getMinQuantity() {
        Mockito.when(mockOpportunityClient.getMinQuantity())
                .thenReturn(10);

        EdgeController edgeController = context.getBean(EdgeController.class);
        int min = edgeController.getMinQuantity();

        assertEquals(opportunity1.getQuantity(), min);
        Mockito.verify(mockOpportunityClient).getMinQuantity();
    }

    @Test
    void getMedQuantity() {
        Mockito.when(mockOpportunityClient.getMedQuantity())
                .thenReturn(20.0);

        EdgeController edgeController = context.getBean(EdgeController.class);
        double med = edgeController.getMedQuantity();

        assertEquals((double) opportunity2.getQuantity(), med);
        Mockito.verify(mockOpportunityClient).getMedQuantity();
    }

    @Test
    void getAllOpportunitiesByProduct() {
        List<Object[]> resultMock = List.of(new Object[]{"BOX", 1}, new Object[]{"FLATBED", 2});
        Mockito.when(mockOpportunityClient.getAllOpportunitiesByProduct())
                .thenReturn(resultMock);

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Object[]> result = edgeController.getAllOpportunitiesByProduct();

        assertEquals(resultMock, result);
        Mockito.verify(mockOpportunityClient).getAllOpportunitiesByProduct();
    }

    @Test
    void getOpportunitiesClosedWonByProduct() {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{"FLATBED", 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedWonByProduct())
                .thenReturn(resultMock);

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Object[]> result = edgeController.getOpportunitiesClosedWonByProduct();

        assertEquals(resultMock, result);
        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedWonByProduct();
    }

    @Test
    void getOpportunitiesClosedLostByProduct() {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{"FLATBED", 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedLostByProduct())
                .thenReturn(resultMock);

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Object[]> result = edgeController.getOpportunitiesClosedLostByProduct();

        assertEquals(resultMock, result);
        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedLostByProduct();
    }

    @Test
    void getOpportunitiesOpenByProduct() {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{"BOX", 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedWonByProduct())
                .thenReturn(resultMock);

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Object[]> result = edgeController.getOpportunitiesClosedWonByProduct();

        assertEquals(resultMock, result);
        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedWonByProduct();
    }


    @Test
    void getOpportunitiesByCountry() throws Exception {
        // Llamar con el GET a /opportunities-country
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-country"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Spain"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Italy"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getClosedWonOpportunitiesByCountry() throws Exception {
        // Llamar con el GET a /opportunities-country/closed-won
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-country/closed-won"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Spain"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Italy"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getClosedLostOpportunitiesByCountry() throws Exception {
        // Llamar con el GET a /opportunities-country/closed-lost
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-country/closed-lost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Spain"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Italy"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getOpenOpportunitiesByCountry() throws Exception {
        // Llamar con el GET a /opportunities-country/open
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-country/open"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Spain"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Italy"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));

    }

    @Test
    void getOpportunitiesByCity() throws Exception {
        // Llamar con el GET a /opportunities-city
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-city"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Barcelona"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Roma"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getClosedWonOpportunitiesByCity() throws Exception {
        // Llamar con el GET a /opportunities-city/closed-won
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-city/closed-won"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Barcelona"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Roma"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getClosedLostOpportunitiesByCity() throws Exception {
        // Llamar con el GET a /opportunities-city/closed-lost
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-city/closed-lost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Barcelona"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Roma"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getOpenOpportunitiesByCity() throws Exception {
        // Llamar con el GET a /opportunities-city/open
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-city/open"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Barcelona"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Roma"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getOpportunitiesByIndustry() throws Exception {
        // Llamar con el GET a /opportunities-industry
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-industry"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("MEDICAL"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));
    }

    @Test
    void getClosedWonOpportunitiesByIndustry() throws Exception {
        // Llamar con el GET a /opportunities-industry/closed-won
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-industry/closed-won"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertFalse(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("MEDICAL"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getClosedLostOpportunitiesByIndustry() throws Exception {
        // Llamar con el GET a /opportunities-industry/closed-lost
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-industry/closed-lost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("MEDICAL"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getOpenOpportunitiesByIndustry() throws Exception {
        // Llamar con el GET a /opportunities-industry/open
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities-industry/open"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("MEDICAL"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getAvgAccount() {
        Mockito.when(mockAccountClient.getAvgEmployeeCount())
                .thenReturn(15.0);

        EdgeController edgeController = context.getBean(EdgeController.class);
        double avg = edgeController.getAvgAccount();

        assertEquals(15.0, avg);
        Mockito.verify(mockAccountClient).getAvgEmployeeCount();
    }

    @Test
    void getMaxAccount() {
        Mockito.when(mockAccountClient.getMaxEmployeeCount())
                .thenReturn(20);

        EdgeController edgeController = context.getBean(EdgeController.class);
        int max = edgeController.getMaxAccount();

        assertEquals(account2.getEmployeeCount(), max);
        Mockito.verify(mockAccountClient).getMaxEmployeeCount();
    }

    @Test
    void getMinAccount() {
        Mockito.when(mockAccountClient.getMinEmployeeCount())
                .thenReturn(10);

        EdgeController edgeController = context.getBean(EdgeController.class);
        int min = edgeController.getMinAccount();

        assertEquals(account1.getEmployeeCount(), min);
        Mockito.verify(mockAccountClient).getMinEmployeeCount();
    }

    @Test
    void getMedAccount() {
        Mockito.when(mockAccountClient.getMedEmployeeCount())
                .thenReturn(15.0);

        EdgeController edgeController = context.getBean(EdgeController.class);
        double med = edgeController.getMedAccount();

        assertEquals(15.0, med);
        Mockito.verify(mockAccountClient).getMedEmployeeCount();
    }

    @Test
    void getOpportunitiesBySalesRep() {
        List<Object[]> resultMock = List.of(new Object[]{1L, 1}, new Object[]{2L,2});
        Mockito.when(mockOpportunityClient.getOpportunitiesBySalesRep())
                .thenReturn(resultMock);

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Object[]> result = edgeController.getOpportunitiesBySalesRep();

        assertEquals(resultMock, result);
        Mockito.verify(mockOpportunityClient).getOpportunitiesBySalesRep();
    }

    @Test
    void getOpportunitiesClosedWonBySalesRep() {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{2L, 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedWonBySalesRep())
                .thenReturn(resultMock);

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Object[]> result = edgeController.getOpportunitiesClosedWonBySalesRep();

        assertEquals(resultMock, result);
        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedWonBySalesRep();
    }

    @Test
    void getOpportunitiesClosedLostBySalesRep() {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{2L, 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedLostBySalesRep())
                .thenReturn(resultMock);

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Object[]> result = edgeController.getOpportunitiesClosedLostBySalesRep();

        assertEquals(resultMock, result);
        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedLostBySalesRep();
    }

    @Test
    void getOpportunitiesOpenBySalesRep() {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{1L, 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesOpenBySalesRep())
                .thenReturn(resultMock);

        EdgeController edgeController = context.getBean(EdgeController.class);
        List<Object[]> result = edgeController.getOpportunitiesOpenBySalesRep();

        assertEquals(resultMock, result);
        Mockito.verify(mockOpportunityClient).getOpportunitiesOpenBySalesRep();
    }

    @Test
    void postLead() {
        Mockito.when(mockLeadClient.postLead(lead1))
                .thenReturn(lead1);

        EdgeController edgeController = context.getBean(EdgeController.class);
        Lead result = edgeController.postLead(lead1);

        assertEquals(lead1, result);
        Mockito.verify(mockLeadClient).postLead(lead1);
    }

    @Test
    void postSalesRep() {
        Mockito.when(mockSalesRepClient.postSalesRep(salesRep1))
                .thenReturn(salesRep1);

        EdgeController edgeController = context.getBean(EdgeController.class);
        SalesRep result = edgeController.postSalesRep(salesRep1);

        assertEquals(salesRep1, result);
        Mockito.verify(mockSalesRepClient).postSalesRep(salesRep1);
    }

//    @Test
//    void convertLead() {
//        Mockito.when(mockLeadClient.postLead(lead1))
//                .thenReturn(lead1);
//
//        ConvertDTO convertDTO = new ConvertDTO("BOX",15,1L);
//
//        EdgeController edgeController = context.getBean(EdgeController.class);
//        String result = edgeController.convertLead(lead1.getId(),convertDTO);
//
//        assertEquals(lead1, result);
//        Mockito.verify(mockLeadClient).postLead(lead1);
//    }

//    @Test
//    void closeLostOpportunity() {
//        doCallRealMethod().when(mockOpportunityClient.updateStatus(opportunity1.getId(), new StatusDTO("CLOSED_LOST")))
////                .thenCallRealMethod(opportunity1.setStatus(Status.CLOSED_LOST));
//
//        OpportunityClient opportunityClientFromContext = context.getBean(OpportunityClient.class);
//        List<Object[]> result = opportunityClientFromContext.getOpportunitiesClosedLostBySalesRep();
//
//        assertEquals(resultMock, result);
//        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedLostBySalesRep();
//    }
//
//    @Test
//    void closeWonOpportunity() {
//    }
}