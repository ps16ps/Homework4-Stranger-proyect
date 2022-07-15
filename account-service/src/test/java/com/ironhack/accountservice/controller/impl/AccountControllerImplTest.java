package com.ironhack.accountservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.accountservice.controller.dto.AccountDTO;
import com.ironhack.accountservice.enums.Industry;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    Account account1, account2;

    @BeforeEach
    void setUp() {
        account1 = new Account(Industry.MEDICAL, 10,"Barcelona", "Spain");
        account2 = new Account(Industry.ECOMMERCE, 20,"Roma", "Italy");
        accountRepository.saveAll(List.of(account1,account2));
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void getAllAccount() throws Exception {
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
    }

    @Test
    void getAccountById_CorrectId_Account() throws Exception {
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
    }

    @Test
    void getAccountById_WrongId_Error() throws Exception {
        // Llamar con el GET a /accounts/{id}
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/accounts/-1"))
                .andExpect(status().isNotFound())
                .andReturn(); //Para cerrar la petición
    }

    @Test
    void getAvgEmployeeCount() throws Exception {
        // Llamar con el GET a /employee-count/avg
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/avg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().equals("15.0"));
    }

    @Test
    void getMedEmployeeCount() throws Exception {
        // Llamar con el GET a /employee-count/med
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/med"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().equals("15.0"));
    }

    @Test
    void getMaxEmployeeCount() throws Exception {
        // Llamar con el GET a /employee-count/max
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/max"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().equals("20"));
    }

    @Test
    void getMinEmployeeCount() throws Exception {
        // Llamar con el GET a /employee-count/min
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/min"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().equals("10"));
    }

    @Test
    void createAccount() throws Exception {
        // Llamar con el POST a /accounts
        // Comprobamos que el status code de respuesta sea CREATED
        // Comprobamos que la respuesta está en formato JSON
        // Comprobamos que el resultado es el que toca

        // Preparo la account que voy a insertar
        AccountDTO accountDTO = new AccountDTO("PRODUCE",25,"Paris","France");
        String body = objectMapper.writeValueAsString(accountDTO);

        // Hago la llamada HTTP
        MvcResult mvcResult = mockMvc.perform(
                        post("/accounts")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        // Compruebo el formato de la respuesta
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Paris"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("PRODUCE"));
        // Compruebo que se haya guardado en la base de datos AKA compruebo que el size haya aumentado
        assertTrue(accountRepository.count()==3);
    }

    @Test
    void createAccount_WrongIndustry_Error() throws Exception {
        // Llamar con el POST a /accounts
        // Comprobamos que el status code de respuesta sea CREATED
        // Comprobamos que la respuesta está en formato JSON
        // Comprobamos que el resultado es el que toca

        // Preparo la account que voy a insertar
        AccountDTO accountDTO = new AccountDTO("PRODUE",25,"Paris","France");
        String body = objectMapper.writeValueAsString(accountDTO);

        // Hago la llamada HTTP
        MvcResult mvcResult = mockMvc.perform(
                        post("/accounts")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();
    }
}