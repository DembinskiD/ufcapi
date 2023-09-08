package com.dembinski.ufcapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UfcApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void failTest() {
		assertTrue(1 == 0);
	}

}
