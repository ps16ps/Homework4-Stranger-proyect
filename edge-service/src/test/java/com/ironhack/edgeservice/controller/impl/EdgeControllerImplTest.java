package com.ironhack.edgeservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.edgeservice.client.*;
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
        contact1 = new Contact("Steve", "+34 6585698", "steve@gamil.com",
                "Accenture", 1L);
        contact2 = new Contact("Jeff", "+34 6899987", "jeff@gamil.com",
                "Accenture", 2L);
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

        LeadClient leadClientFromContext = context.getBean(LeadClient.class);
        List<Lead> leads = leadClientFromContext.getAllLead();

        assertEquals(List.of(lead1,lead2), leads);
        Mockito.verify(mockLeadClient).getAllLead();
    }

    @Test
    void getAllSalesRep() {
        Mockito.when(mockSalesRepClient.getAllSalesRep()).thenReturn(List.of(salesRep1,salesRep2));

        SalesRepClient salesRepClientFromContext = context.getBean(SalesRepClient.class);
        List<SalesRep> salesReps = salesRepClientFromContext.getAllSalesRep();

        assertEquals(List.of(salesRep1,salesRep2), salesReps);
        Mockito.verify(mockSalesRepClient).getAllSalesRep();
    }

    @Test
    void getAllContacts() {
        Mockito.when(mockContactClient.findAll()).thenReturn(List.of(contact1,contact2));

        ContactClient contactClientFromContext = context.getBean(ContactClient.class);
        List<Contact> contacts = contactClientFromContext.findAll();

        assertEquals(List.of(contact1,contact2), contacts);
        Mockito.verify(mockContactClient).findAll();
    }

    @Test
    void getAllOpportunities() {
        Mockito.when(mockOpportunityClient.getAllOpportunities())
                .thenReturn(List.of(opportunity1,opportunity2,opportunity3));

        OpportunityClient opportunityClientFromContext = context.getBean(OpportunityClient.class);
        List<Opportunity> opportunities = opportunityClientFromContext.getAllOpportunities();

        assertEquals(List.of(opportunity1,opportunity2,opportunity3), opportunities);
        Mockito.verify(mockOpportunityClient).getAllOpportunities();
    }

    @Test
    void getAllAccounts() {
        Mockito.when(mockAccountClient.getAllAccount()).thenReturn(List.of(account1,account2));

        AccountClient accountClientFromContext = context.getBean(AccountClient.class);
        List<Account> accounts = accountClientFromContext.getAllAccount();

        assertEquals(List.of(account1,account2), accounts);
        Mockito.verify(mockAccountClient).getAllAccount();
    }

    @Test
    void getLeadsById() {
    }

    @Test
    void getSalesRepsById() {
    }

    @Test
    void getContactsById() {
    }

    @Test
    void getOpportunitiesById() {
    }

    @Test
    void getAccountsById() {
    }

    @Test
    void getAvgQuantity() {
    }

    @Test
    void getMaxQuantity() {
    }

    @Test
    void getMinQuantity() {
    }

    @Test
    void getMedQuantity() {
    }

    @Test
    void getAllOpportunitiesByProduct() {
    }

    @Test
    void getOpportunitiesClosedWonByProduct() {
    }

    @Test
    void getOpportunitiesClosedLostByProduct() {
    }

    @Test
    void getOpportunitiesOpenByProduct() {
    }

    @Test
    void getOpportunitiesBySalesRep() {
    }

    @Test
    void getOpportunitiesClosedWonBySalesRep() {
    }

    @Test
    void getOpportunitiesClosedLostBySalesRep() {
    }

    @Test
    void getOpportunitiesOpenBySalesRep() {
    }

    @Test
    void findOpportunitiesByCountry() {
    }

    @Test
    void postLead() {
    }

    @Test
    void postSalesRep() {
    }

    @Test
    void convertLead() {
    }

    @Test
    void closeLostOpportunity() {
    }

    @Test
    void closeWonOpportunity() {
    }

    @Test
    void testGetAllLeads() {
    }

    @Test
    void testGetAllSalesRep() {
    }

    @Test
    void testGetAllContacts() {
    }

    @Test
    void testGetAllOpportunities() {
    }

    @Test
    void testGetAllAccounts() {
    }

    @Test
    void testGetLeadsById() {
    }

    @Test
    void testGetSalesRepsById() {
    }

    @Test
    void testGetContactsById() {
    }

    @Test
    void testGetOpportunitiesById() {
    }

    @Test
    void testGetAccountsById() {
    }

    @Test
    void getAvgAccount() {
    }

    @Test
    void getMaxAccount() {
    }

    @Test
    void getMinAccount() {
    }

    @Test
    void getMedAccount() {
    }

    @Test
    void testGetAvgQuantity() {
    }

    @Test
    void testGetMaxQuantity() {
    }

    @Test
    void testGetMinQuantity() {
    }

    @Test
    void testGetMedQuantity() {
    }

    @Test
    void testGetAllOpportunitiesByProduct() {
    }

    @Test
    void testGetOpportunitiesClosedWonByProduct() {
    }

    @Test
    void testGetOpportunitiesClosedLostByProduct() {
    }

    @Test
    void testGetOpportunitiesOpenByProduct() {
    }

    @Test
    void testGetOpportunitiesBySalesRep() {
    }

    @Test
    void testGetOpportunitiesClosedWonBySalesRep() {
    }

    @Test
    void testGetOpportunitiesClosedLostBySalesRep() {
    }

    @Test
    void testGetOpportunitiesOpenBySalesRep() {
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
    void testPostLead() {
    }

    @Test
    void testPostSalesRep() {
    }

    @Test
    void testConvertLead() {
    }

    @Test
    void testCloseLostOpportunity() {
    }

    @Test
    void testCloseWonOpportunity() {
    }
}