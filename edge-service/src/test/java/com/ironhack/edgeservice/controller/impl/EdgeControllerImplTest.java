package com.ironhack.edgeservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.repository.EdgeRepository;
import com.ironhack.edgeservice.service.interfaces.EdgeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EdgeControllerImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LeadClient leadClient;

    @Autowired
    private SalesRepClient salesRepClient;

    @Autowired
    private ContactClient contactClient;

    @Autowired
    private OpportunityClient opportunityClient;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private EdgeRepository edgeRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllLeads() {
    }

    @Test
    void getAllSalesRep() {
    }

    @Test
    void getAllContacts() {
    }

    @Test
    void getAllOpportunities() {
    }

    @Test
    void getAllAccounts() {
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
}