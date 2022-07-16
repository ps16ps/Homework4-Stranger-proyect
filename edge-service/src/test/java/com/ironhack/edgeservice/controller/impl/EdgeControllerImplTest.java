package com.ironhack.edgeservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.controller.dto.AccountDTO;
import com.ironhack.edgeservice.controller.dto.ConvertDTO;
import com.ironhack.edgeservice.controller.dto.OpportunityDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        lead1.setId(1L);
        lead2 = new Lead("Jeff", "+34 6899987", "jeff@gamil.com",
                "Accenture", 2L);
        lead2.setId(2L);

        account1 = new Account(Industry.MEDICAL, 10,"Barcelona", "Spain");
        account1.setId(1L);
        account2 = new Account(Industry.ECOMMERCE, 20,"Roma", "Italy");
        account2.setId(2L);

        contact1 = new Contact("Steve", "+34 6585698", "steve@gamil.com",
                "Accenture",1L);
        contact1.setId(1L);
        contact2 = new Contact("Iñaki","666666888","iñaki@gmail.com",
                "IñakiCompany",2L);
        contact2.setId(2L);

        opportunity1 = new Opportunity(Product.BOX,10,1L,1L,1L);
        opportunity1.setId(1L);
        opportunity2 = new Opportunity(Product.FLATBED,20,1L,1L,2L);
        opportunity2.setStatus(Status.CLOSED_WON);
        opportunity2.setId(2L);
        opportunity3 = new Opportunity(Product.FLATBED,30,1L,2L,2L);
        opportunity3.setStatus(Status.CLOSED_LOST);
        opportunity3.setId(3L);

        accountRepository.saveAll(List.of(account1,account2));
        edgeRepository.saveAll(List.of(opportunity1,opportunity2,opportunity3));
    }

    @AfterEach
    void tearDown() {
        edgeRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void getAllLeads() throws Exception {
        Mockito.when(mockLeadClient.getAllLead()).thenReturn(List.of(lead1,lead2));

        // Llamar con el GET a /leads
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/leads"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Steve"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Jeff"));

        Mockito.verify(mockLeadClient).getAllLead();
    }

    @Test
    void getAllSalesRep() throws Exception {
        Mockito.when(mockSalesRepClient.getAllSalesRep()).thenReturn(List.of(salesRep1,salesRep2));

        // Llamar con el GET a /sales-rep
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/sales-rep"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Lia"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Pep"));

        Mockito.verify(mockSalesRepClient).getAllSalesRep();
    }

    @Test
    void getAllContacts() throws Exception {
        Mockito.when(mockContactClient.findAll()).thenReturn(List.of(contact1,contact2));

        // Llamar con el GET a /contacts
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Steve"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("666666888"));

        Mockito.verify(mockContactClient).findAll();
    }

    @Test
    void getAllOpportunities() throws Exception {
        Mockito.when(mockOpportunityClient.getAllOpportunities())
                .thenReturn(List.of(opportunity1,opportunity2,opportunity3));

        // Llamar con el GET a /opportunities
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BOX"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("FLATBED"));

        Mockito.verify(mockOpportunityClient).getAllOpportunities();
    }

    @Test
    void getAllAccounts() throws Exception {
        Mockito.when(mockAccountClient.getAllAccount()).thenReturn(List.of(account1,account2));

        // Llamar con el GET a /accounts
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Barcelona"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Roma"));

        Mockito.verify(mockAccountClient).getAllAccount();
    }

    @Test
    void getLeadsById() throws Exception {
        Mockito.when(mockLeadClient.getLeadById(lead1.getId())).thenReturn(lead1);

        // Llamar con el GET a /leads/{id}
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/leads/"+lead1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Steve"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Jeff"));

        Mockito.verify(mockLeadClient).getLeadById(lead1.getId());
    }

    @Test
    void getSalesRepsById() throws Exception {
        Mockito.when(mockSalesRepClient.getSalesRepById(salesRep1.getId())).thenReturn(salesRep1);

        // Llamar con el GET a /sales-rep/{id}
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/sales-rep/"+salesRep1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Lia"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Pep"));

        Mockito.verify(mockSalesRepClient).getSalesRepById(salesRep1.getId());
    }

    @Test
    void getContactsById() throws Exception {
        Mockito.when(mockContactClient.findById(contact1.getId())).thenReturn(contact1);

        // Llamar con el GET a /contacts/{id}
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/contacts/"+contact1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Steve"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Iñaki"));

        Mockito.verify(mockContactClient).findById(contact1.getId());
    }

    @Test
    void getOpportunitiesById() throws Exception {
        Mockito.when(mockOpportunityClient.getOpportunityById(opportunity1.getId()))
                .thenReturn(opportunity1);

        // Llamar con el GET a /opportunities/{id}
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities/"+opportunity1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BOX"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("FLATBED"));

        Mockito.verify(mockOpportunityClient).getOpportunityById(opportunity1.getId());
    }

    @Test
    void getAccountsById() throws Exception {
        Mockito.when(mockAccountClient.getAccountById(account1.getId())).thenReturn(account1);

        // Llamar con el GET a /accounts/{id}
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/accounts/"+account1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Barcelona"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Roma"));
        Mockito.verify(mockAccountClient).getAccountById(account1.getId());
    }

    @Test
    void getAvgQuantity() throws Exception {
        Mockito.when(mockOpportunityClient.getAvgQuantity())
                .thenReturn(20.0);

        // Llamar con el GET a /opportunity-quantity/avg
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/avg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        Double mean = ((double) opportunity1.getQuantity() + (double)  opportunity2.getQuantity()
                + (double) opportunity3.getQuantity())/3;
        assertTrue(mvcResult.getResponse().getContentAsString().contains(mean.toString()));

        Mockito.verify(mockOpportunityClient).getAvgQuantity();
    }

    @Test
    void getMaxQuantity() throws Exception {
        Mockito.when(mockOpportunityClient.getMaxQuantity())
                .thenReturn(30);

        // Llamar con el GET a /opportunity-quantity/max
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/max"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("30"));

        Mockito.verify(mockOpportunityClient).getMaxQuantity();
    }

    @Test
    void getMinQuantity() throws Exception {
        Mockito.when(mockOpportunityClient.getMinQuantity())
                .thenReturn(10);

        // Llamar con el GET a /opportunity-quantity/min
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/min"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("10"));

        Mockito.verify(mockOpportunityClient).getMinQuantity();
    }

    @Test
    void getMedQuantity() throws Exception {
        Mockito.when(mockOpportunityClient.getMedQuantity())
                .thenReturn(20.0);

        // Llamar con el GET a /opportunity-quantity/med
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/med"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("20.0"));

        Mockito.verify(mockOpportunityClient).getMedQuantity();
    }

    @Test
    void getAllOpportunitiesByProduct() throws Exception {
        List<Object[]> resultMock = List.of(new Object[]{"BOX", 1}, new Object[]{"FLATBED", 2});
        Mockito.when(mockOpportunityClient.getAllOpportunitiesByProduct())
                .thenReturn(resultMock);

        // Llamar con el GET a /opportunity-products
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("BOX"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));

        Mockito.verify(mockOpportunityClient).getAllOpportunitiesByProduct();
    }

    @Test
    void getOpportunitiesClosedWonByProduct() throws Exception {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{"FLATBED", 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedWonByProduct())
                .thenReturn(resultMock);

        // Llamar con el GET a /opportunity-products/closed-won
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-products/closed-won"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));

        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedWonByProduct();
    }

    @Test
    void getOpportunitiesClosedLostByProduct() throws Exception {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{"FLATBED", 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedLostByProduct())
                .thenReturn(resultMock);

        // Llamar con el GET a /opportunity-products/closed-lost
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-products/closed-lost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));

        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedLostByProduct();
    }

    @Test
    void getOpportunitiesOpenByProduct() throws Exception {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{"BOX", 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedWonByProduct())
                .thenReturn(resultMock);

        // Llamar con el GET a /opportunity-products/open
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-products/open"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("BOX"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));

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
    void getAvgAccount() throws Exception {
        Mockito.when(mockAccountClient.getAvgEmployeeCount())
                .thenReturn(15.0);

        // Llamar con el GET a /account-employee/avg
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/account-employee/avg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("15.0"));

        Mockito.verify(mockAccountClient).getAvgEmployeeCount();
    }

    @Test
    void getMaxAccount() throws Exception {
        Mockito.when(mockAccountClient.getMaxEmployeeCount())
                .thenReturn(20);

        // Llamar con el GET a /account-employee/max
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/account-employee/max"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("20"));

        Mockito.verify(mockAccountClient).getMaxEmployeeCount();
    }

    @Test
    void getMinAccount() throws Exception {
        Mockito.when(mockAccountClient.getMinEmployeeCount())
                .thenReturn(10);

        // Llamar con el GET a /account-employee/min
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/account-employee/min"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("10"));

        Mockito.verify(mockAccountClient).getMinEmployeeCount();
    }

    @Test
    void getMedAccount() throws Exception {
        Mockito.when(mockAccountClient.getMedEmployeeCount())
                .thenReturn(15.0);

        // Llamar con el GET a /account-employee/med
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/account-employee/med"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("15.0"));

        Mockito.verify(mockAccountClient).getMedEmployeeCount();
    }

    @Test
    void getOpportunitiesBySalesRep() throws Exception {
        List<Object[]> resultMock = List.of(new Object[]{1L, 1}, new Object[]{2L,2});
        Mockito.when(mockOpportunityClient.getOpportunitiesBySalesRep())
                .thenReturn(resultMock);

        // Llamar con el GET a /opportunity-sales-rep
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-sales-rep"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));

        Mockito.verify(mockOpportunityClient).getOpportunitiesBySalesRep();
    }

    @Test
    void getOpportunitiesClosedWonBySalesRep() throws Exception {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{2L, 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedWonBySalesRep())
                .thenReturn(resultMock);

        // Llamar con el GET a /opportunity-sales-rep/closed-won
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-sales-rep/closed-won"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));

        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedWonBySalesRep();
    }

    @Test
    void getOpportunitiesClosedLostBySalesRep() throws Exception {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{2L, 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesClosedLostBySalesRep())
                .thenReturn(resultMock);

        // Llamar con el GET a /opportunity-sales-rep/closed-lost
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-sales-rep/closed-lost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));

        Mockito.verify(mockOpportunityClient).getOpportunitiesClosedLostBySalesRep();
    }

    @Test
    void getOpportunitiesOpenBySalesRep() throws Exception {
        List<Object[]> resultMock = List.of(new Object[][]{new Object[]{1L, 1}});
        Mockito.when(mockOpportunityClient.getOpportunitiesOpenBySalesRep())
                .thenReturn(resultMock);

        // Llamar con el GET a /opportunity-sales-rep/open
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-sales-rep/open"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición

        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("2"));

        Mockito.verify(mockOpportunityClient).getOpportunitiesOpenBySalesRep();
    }

    @Test
    void postLead() throws Exception {
        Mockito.when(mockLeadClient.postLead(lead1))
                .thenReturn(lead1);

        // Preparo la lead que voy a insertar
        String body = objectMapper.writeValueAsString(lead1);

        // Hago la llamada HTTP
        MvcResult mvcResult = mockMvc.perform(
                        post("/leads")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        // Compruebo el formato de la respuesta
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Steve"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Jeff"));

        Mockito.verify(mockLeadClient).postLead(lead1);
    }

    @Test
    void postSalesRep() throws Exception {
        Mockito.when(mockSalesRepClient.postSalesRep(salesRep1))
                .thenReturn(salesRep1);

        // Preparo la sales-rep que voy a insertar
        String body = objectMapper.writeValueAsString(salesRep1);

        // Hago la llamada HTTP
        MvcResult mvcResult = mockMvc.perform(
                        post("/sales-rep")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        // Compruebo el formato de la respuesta
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Lia"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Pep"));

        Mockito.verify(mockSalesRepClient).postSalesRep(salesRep1);
    }

    @Test
    void convertLead_ExistingAccount() throws Exception {
        ConvertDTO convertDTO = new ConvertDTO("BOX",10,1L);
        OpportunityDTO opportunityDTO = new OpportunityDTO(convertDTO.getProduct(), convertDTO.getQuantity(),
                contact1.getId(),account1.getId(), lead1.getSalesRepId());

        Mockito.when(mockLeadClient.getLeadById(lead1.getId()))
                .thenReturn(lead1);
        Mockito.doNothing().when(mockLeadClient).delete(lead1.getId());
        Mockito.when(mockAccountClient.getAccountById(convertDTO.getAccountId()))
                .thenReturn(account1);
        Mockito.when(mockContactClient.saveContact(contact1))
                .thenReturn(contact1);
        Mockito.when(mockOpportunityClient.createOpportunity(opportunityDTO))
                .thenReturn(opportunity1);

        // Preparo la convertDTO que voy a insertar
        String body = objectMapper.writeValueAsString(convertDTO);

        // Hago la llamada HTTP
        MvcResult mvcResult = mockMvc.perform(
                        post("/convert/"+lead1.getId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andReturn();
        // Compruebo el formato de la respuesta
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Lead " + lead1.getId() + " converted"));

        Mockito.verify(mockLeadClient).getLeadById(lead1.getId());
        Mockito.verify(mockLeadClient).delete(lead1.getId());
        Mockito.verify(mockAccountClient).getAccountById(convertDTO.getAccountId());
        Mockito.verify(mockContactClient).saveContact(contact1);
        Mockito.verify(mockOpportunityClient).createOpportunity(opportunityDTO);
    }

    @Test
    void convertLead_NewAccount() throws Exception {
        ConvertDTO convertDTO = new ConvertDTO("BOX",10,"MEDICAL",10,
                "Barcelona","Spain");
        AccountDTO accountDTO = new AccountDTO(convertDTO.getIndustry(), convertDTO.getEmployeeCount(),
                convertDTO.getCity(), convertDTO.getCountry());
        OpportunityDTO opportunityDTO = new OpportunityDTO(convertDTO.getProduct(), convertDTO.getQuantity(),
                contact1.getId(),account1.getId(), lead1.getSalesRepId());

        Mockito.when(mockLeadClient.getLeadById(lead1.getId()))
                .thenReturn(lead1);
        Mockito.doNothing().when(mockLeadClient).delete(lead1.getId());
        Mockito.when(mockAccountClient.createAccount(accountDTO))
                .thenReturn(account1);
        Mockito.when(mockContactClient.saveContact(contact1))
                .thenReturn(contact1);
        Mockito.when(mockOpportunityClient.createOpportunity(opportunityDTO))
                .thenReturn(opportunity1);

        // Preparo la convertDTO que voy a insertar
        String body = objectMapper.writeValueAsString(convertDTO);

        // Hago la llamada HTTP
        MvcResult mvcResult = mockMvc.perform(
                        post("/convert/"+lead1.getId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andReturn();
        // Compruebo el formato de la respuesta
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Lead " + lead1.getId() + " converted"));

        Mockito.verify(mockLeadClient).getLeadById(lead1.getId());
        Mockito.verify(mockLeadClient).delete(lead1.getId());
        Mockito.verify(mockAccountClient).createAccount(accountDTO);
        Mockito.verify(mockContactClient).saveContact(contact1);
        Mockito.verify(mockOpportunityClient).createOpportunity(opportunityDTO);
    }

    @Test
    void closeLostOpportunity() {
        Mockito.doAnswer(inputs -> {
            opportunity1.setStatus(Status.CLOSED_LOST);
            return null;
        }).when(mockOpportunityClient).updateStatus(opportunity1.getId(), new StatusDTO("CLOSED_LOST"));

        EdgeController edgeController = context.getBean(EdgeController.class);
        edgeController.closeLostOpportunity(opportunity1.getId());

        assertEquals(Status.CLOSED_LOST, opportunity1.getStatus());
        Mockito.verify(mockOpportunityClient).updateStatus(opportunity1.getId(), new StatusDTO("CLOSED_LOST"));
    }

    @Test
    void closeWonOpportunity() {
        Mockito.doAnswer(opportunity1Id -> {
            opportunity1.setStatus(Status.CLOSED_WON);
            return null;
        }).when(mockOpportunityClient).updateStatus(opportunity1.getId(), new StatusDTO("CLOSED_WON"));

        EdgeController edgeController = context.getBean(EdgeController.class);
        edgeController.closeWonOpportunity(opportunity1.getId());

        assertEquals(Status.CLOSED_WON, opportunity1.getStatus());
        Mockito.verify(mockOpportunityClient).updateStatus(opportunity1.getId(), new StatusDTO("CLOSED_WON"));
    }
}