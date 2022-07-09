package com.ironhack.Homework3;

import com.ironhack.Homework3.creatingObjects.CRMMenu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class Homework4ApplicationTests {

	@MockBean
	private CRMMenu crmMenu;

	@Test
	void contextLoads() {
	}

}
