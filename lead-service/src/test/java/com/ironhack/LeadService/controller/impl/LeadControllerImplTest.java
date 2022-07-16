package com.ironhack.LeadService.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.LeadService.model.Lead;
import com.ironhack.LeadService.repository.LeadRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
public class LeadControllerImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LeadRepository leadRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Lead lead1, lead2;

    @BeforeEach
    void setUp() {
        lead1 = new Lead("Juana","678463728","juana@gmail.com",
                "juanaTacos",1L);
        lead2 = new Lead("Mariana","742974922","mariana@gmail.com",
                "uñasMariana",2L);
        leadRepository.saveAll(List.of(lead1,lead2));
    }

    @AfterEach
    void tearDown() {
        leadRepository.deleteAll();
    }

    @Test
    void getAllLeads() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/leads"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Juana"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("juanaTacos"));
    }

    @Test
    void getLeadById_correctId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/leads/" +lead1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Juana"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("uñasMariana"));
    }

    @Test
    void getLeadById_wrongId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/leads/-1"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void postLead() throws Exception {
        Lead lead3 = new Lead("Paulina","754890423","paulina@gmail.com",
                "paulinaMusic",3L);
        String body = objectMapper.writeValueAsString(lead3);
        MvcResult mvcResult = mockMvc.perform(
                        post("/leads")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Paulina"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("paulinaMusic"));
        assertTrue(leadRepository.count()==3);
    }

//    @Test
//    void delete() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(delete("/delete/"+lead1.getId())
//                .andExpect(status().isNoContent())
//                .andReturn());
//        assertFalse(leadRepository.existsById(lead1.getId()));
//    }
}
