package com.ironhack.SalesRep.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.SalesRep.model.SalesRep;
import com.ironhack.SalesRep.repository.SalesRepRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SalesRepControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SalesRepRepository salesRepRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    SalesRep salesRep1, salesRep2,salesRep3;

    @BeforeEach
    void setUp() {
        salesRep1 = new SalesRep("Mario");
        salesRep2 = new SalesRep("Felipe");
        salesRep3 = new SalesRep("Pedro");
        salesRepRepository.saveAll(List.of(salesRep1, salesRep2, salesRep3));
    }

    @AfterEach
    void tearDown() { salesRepRepository.deleteAll(); }

    @Test
    void getAllSalesRep() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sales-rep"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Mario"));
    }

    @Test
    void getSalesRepById_correctId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sales-rep/" +salesRep1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Mario"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Felipe"));
    }

    @Test
    void getSalesRepById_wrongId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sales-rep/-1"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void postSalesRep() throws Exception {
        SalesRep salesRep = new SalesRep("Juan");
        String json = objectMapper.writeValueAsString(salesRep);
        MvcResult mvcResult = mockMvc.perform(post("/sales-rep")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Juan"));
        assertTrue(salesRepRepository.count()==4);
    }
}