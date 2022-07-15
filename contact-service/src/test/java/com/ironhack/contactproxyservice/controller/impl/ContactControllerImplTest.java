package com.ironhack.contactproxyservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.contactproxyservice.model.Contact;
import com.ironhack.contactproxyservice.repository.ContactRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ContactRepository contactRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Contact contact1, contact2;

    @BeforeEach
    void setUp() {
        contact1 = new Contact("Alba","666666999","alba@gmail.com",
                "albaCompany",1L);
        contact2 = new Contact("Iñaki","666666888","iñaki@gmail.com",
                "IñakiCompany",2L);
        contactRepository.saveAll(List.of(contact1,contact2));
    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Alba"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("albaCompany"));
    }

    @Test
    void findById_correctId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/contacts/" +contact1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Alba"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("iñakiCompany"));
    }

    @Test
    void findById_wrongId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/contacts/-1"))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @Test
    void saveContact() throws Exception {
        Contact contact3 = new Contact("Asier","666666444","asier@gmail.com",
                "asierCompany",3L);
        String body = objectMapper.writeValueAsString(contact3);
        MvcResult mvcResult = mockMvc.perform(
                        post("/contacts")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Asier"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("asierCompany"));
        assertTrue(contactRepository.count()==3);
    }

}
