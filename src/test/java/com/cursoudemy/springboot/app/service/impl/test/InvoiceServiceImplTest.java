package com.cursoudemy.springboot.app.service.impl.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cursoudemy.springboot.app.model.entity.Client;
import com.cursoudemy.springboot.app.model.entity.Invoice;
import com.cursoudemy.springboot.app.service.InvoiceService;

@SpringBootTest
class InvoiceServiceImplTest {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	void shouldFindByClient() {
		
		//GIVEN
		Long clientId = 1L;
		String expected = "Factura equipos de oficina";
		
		//WHEN
		List<Invoice> result = invoiceService.findByClient(clientId);
		
		//THEN
		assertNotNull(result, "Debería encontrar facturas");
		assertEquals(expected, result.get(0).getDescription(), "La descripción debería ser la esperada");
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
