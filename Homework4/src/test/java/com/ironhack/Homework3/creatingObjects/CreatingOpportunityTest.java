package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.enums.Product;
import com.ironhack.Homework3.model.Contact;
import com.ironhack.Homework3.model.Opportunity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CreatingOpportunityTest {
    @Autowired
    CreatingOpportunity creatingOpportunity;

    @BeforeEach
    void setUp() {
        creatingOpportunity = new CreatingOpportunity();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void selectProductBoolean_notProductValue_False() {
        assertFalse(creatingOpportunity.selectProductBoolean("String product"));
        assertFalse(creatingOpportunity.selectProductBoolean("Hel1lo"));
        assertFalse(creatingOpportunity.selectProductBoolean("1246%"));
    }

    @Test
    void selectProductBoolean_productValue_True() {
        assertTrue(creatingOpportunity.selectProductBoolean("hybrid"));
        assertTrue(creatingOpportunity.selectProductBoolean("FLATBED"));
        assertTrue(creatingOpportunity.selectProductBoolean("Box"));
    }

    @Test
    void writeQuantityBoolean_notInt_False() {
        assertFalse(creatingOpportunity.writeQuantityBoolean("String product"));
        assertFalse(creatingOpportunity.writeQuantityBoolean("-12"));
        assertFalse(creatingOpportunity.writeQuantityBoolean("!1246%"));
    }

    @Test
    void writeQuantityBoolean_int_True() {
        assertTrue(creatingOpportunity.writeQuantityBoolean("4"));
        assertTrue(creatingOpportunity.writeQuantityBoolean("1234"));
    }
}