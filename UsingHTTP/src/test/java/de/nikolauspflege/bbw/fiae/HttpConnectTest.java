package de.nikolauspflege.bbw.fiae;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.nikolauspflege.bbw.fiae.http.HttpConnect;

class HttpConnectTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRun() {
		
		String[] args = { "http://httpbin.org/get?name=Hugo" };
		HttpConnect.run(args );
	}
	@Test
	void testMain() {
		
		String[] args = { "httpbin.org/get?name=Hugo" };
	    Throwable exception = assertThrows(IllegalArgumentException.class, () -> HttpConnect.main(args ));
	    assertEquals("URI with undefined scheme", exception.getMessage());

		
	
	}

}
