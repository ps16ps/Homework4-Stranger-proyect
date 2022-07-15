package com.ironhack.opportunityservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.opportunityservice.controller.dto.OpportunityDTO;
import com.ironhack.opportunityservice.enums.Product;
import com.ironhack.opportunityservice.enums.Status;
import com.ironhack.opportunityservice.model.Opportunity;
import com.ironhack.opportunityservice.repository.OpportunityRepository;
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
class OpportunityControllerImplTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OpportunityRepository opportunityRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Opportunity opportunity1, opportunity2,opportunity3;

    @BeforeEach
    void setUp() {
        opportunity1 = new Opportunity(Product.BOX,10,1L,1L,1L);
        opportunity2 = new Opportunity(Product.FLATBED,20,1L,1L,2L);
        opportunity2.setStatus(Status.CLOSED_WON);
        opportunity3 = new Opportunity(Product.FLATBED,30,1L,1L,2L);
        opportunity3.setStatus(Status.CLOSED_LOST);

        opportunityRepository.saveAll(List.of(opportunity1,opportunity2,opportunity3));
    }

    @AfterEach
    void tearDown() {
        opportunityRepository.deleteAll();
    }

    @Test
    void getAllOpportunities() throws Exception {
        // Llamar con el GET a /opportunities
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BOX"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("30"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
    }

    @Test
    void getOpportunityById_CorrectId_Opportunity() throws Exception {
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
    }

    @Test
    void getOpportunityById_WrongId_Error() throws Exception {
        // Llamar con el GET a /opportunities/{id}
        // Comprobamos que el status code de  respuesta sea NOT_FOUND
        MvcResult mvcResult = mockMvc.perform(get("/opportunities/-1"))
                .andExpect(status().isNotFound())
                .andReturn(); //Para cerrar la petición
    }

    @Test
    void getAvgQuantity() throws Exception {
        // Llamar con el GET a /opportunity-quantity/avg
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/avg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("20.0"));
    }

    @Test
    void getMaxQuantity() throws Exception {
        // Llamar con el GET a /opportunity-quantity/max
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/max"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("30"));
    }

    @Test
    void getMinQuantity() throws Exception {
        // Llamar con el GET a /opportunity-quantity/min
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/min"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("10"));
    }

    @Test
    void getMedQuantity() throws Exception {
        // Llamar con el GET a /opportunity-quantity/med
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/med"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("20.0"));
    }

    @Test
    void getAllOpportunitiesByProduct() throws Exception {
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
        assertTrue(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));
    }

    @Test
    void getOpportunitiesClosedWonByProduct() throws Exception {
        // Llamar con el GET a /opportunity-products/closed-won
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-products/closed-won"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertFalse(mvcResult.getResponse().getContentAsString().contains("BOX"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
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
    void createOpportunity_CorrectDTO_Opportunity() throws Exception {
        // Llamar con el POST a /opportunities
        // Comprobamos que el status code de respuesta sea CREATED
        // Comprobamos que la respuesta está en formato JSON
        // Comprobamos que el resultado es el que toca

        // Preparo la account que voy a insertar
        OpportunityDTO opportunityDTO = new OpportunityDTO("BOX",15,1L,
                1L,1L);
        String body = objectMapper.writeValueAsString(opportunityDTO);

        // Hago la llamada HTTP
        MvcResult mvcResult = mockMvc.perform(
                        post("/opportunities")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        // Compruebo el formato de la respuesta
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BOX"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("15"));
        // Compruebo que se haya guardado en la base de datos AKA compruebo que el size haya aumentado
        assertTrue(opportunityRepository.count()==4);
    }

    @Test
    void updateStatus() {
    }
}