package com.ironhack.Homework3.creatingObjects;


import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.Repository.*;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.enums.Product;
import com.ironhack.Homework3.model.Account;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CreatingAccountTest {
    @Autowired
    CreatingAccount creatingAccount;

    @BeforeEach
    void setUp() {
        creatingAccount = new CreatingAccount();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void selectIndustryCondition_False_BadStament() {
        assertFalse(creatingAccount.selectIndustryCondition("String product"));
        assertFalse(creatingAccount.selectIndustryCondition("Hel1lo"));
        assertFalse(creatingAccount.selectIndustryCondition("1246%"));
    }

    @Test
    void selectIndustryCondition_True_GoodStament() {
        assertTrue(creatingAccount.selectIndustryCondition("ProducE"));
        assertTrue(creatingAccount.selectIndustryCondition(" eCoMMerce"));
        assertTrue(creatingAccount.selectIndustryCondition("MANUFACTURING"));
        assertTrue(creatingAccount.selectIndustryCondition("Medical"));
        assertTrue(creatingAccount.selectIndustryCondition("other"));
    }

    @Test
    void employeeCountCondition_False_BadStament() {
        assertFalse(creatingAccount.employeeCountCondition("String product"));
        assertFalse(creatingAccount.employeeCountCondition("-12"));
        assertFalse(creatingAccount.employeeCountCondition("!1246%"));
    }

    @Test
    void employeeCountCondition_True_GoodStament() {
        assertTrue(creatingAccount.employeeCountCondition(" 4"));
        assertTrue(creatingAccount.employeeCountCondition("1234 "));
    }

    @Test
    void writeCityCondition_False_BadStament() {
        assertFalse(creatingAccount.writeCityCondition("String1product"));
        assertFalse(creatingAccount.writeCityCondition("!Hola%"));
    }

    @Test
    void writeCityyCondition_True_GoodStament() {
        assertTrue(creatingAccount.writeCityCondition("Micronizados Naturales"));
        assertTrue(creatingAccount.writeCityCondition(" LacKar"));
    }

    @Test
    void writeCountryCondition_False_BadStament() {
        assertFalse(creatingAccount.writeCountryCondition("String1product"));
        assertFalse(creatingAccount.writeCountryCondition("!Hola%"));
    }

    @Test
    void writeCountryCondition_True_GoodStament() {
        assertTrue(creatingAccount.writeCountryCondition(" Micronizados Naturales"));
        assertTrue(creatingAccount.writeCountryCondition("LacKar"));

    }
}
