package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.ApplicationTest;
import com.ironhack.Homework3.model.Contact;
import com.ironhack.Homework3.model.Lead;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CreatingObjectTest {
    @Autowired
    private CreatingObject creatingObject;

    @BeforeEach
    void setUp() {
        creatingObject = new CreatingObject();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void objectName_first_second() {
        assertEquals(true,creatingObject.objectName("Alex Bermejo"));
        assertEquals(true,creatingObject.objectName("Alex Bermejo Garcia"));
    }


    @Test
    void objectName_not_first_second() {
     assertEquals(false,creatingObject.objectName("AlexBermejo"));
    }

    @Test
    void objectPhoneNumber_valid_format() {
        assertEquals(true,creatingObject.objectPhoneNumber("+34 652899076"));
    }

    @Test
    void objectPhoneNumber_invalid_format() {
        assertEquals(false,creatingObject.objectPhoneNumber("1987"));
    }

    @Test
    void objectEmail_valid_format() {
        assertEquals(true,creatingObject.objectEmail("alex.bermejo@gmail.com"));
    }

    @Test
    void objectEmail_invalid_format() {
        assertEquals(false,creatingObject.objectEmail("alex.bermejogmail.com"));
        assertEquals(false,creatingObject.objectEmail("alexbermejo@gmailcom"));
        assertEquals(false,creatingObject.objectEmail("alex.be rmejo@gmail.com"));
    }

    @Test
    void objectCompanyName_valid_format() {
        assertEquals(true,creatingObject.objectCompanyName("Accenture"));
    }
    @Test
    void objectCompanyName_invalid_format() {
        assertEquals(false,creatingObject.objectCompanyName(""));
    }
}
