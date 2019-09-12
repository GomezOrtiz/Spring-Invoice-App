package com.cursoudemy.springboot.app.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fakecorp.invoicing.app.model.entity.Invoice;
import com.fakecorp.invoicing.app.service.ClientService;
import com.fakecorp.invoicing.app.service.InvoiceService;

@SpringBootTest
class InvoiceServiceImplTest {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ClientService clientService;
	
	@Test
	void shouldFindAllInvoices() {
		fail("Not yet implemented");
	}

	@Test
	void shouldFindInvoiceById() {
		fail("Not yet implemented");
	}

	@Test
	void shouldFindInvoicesByClient() {
		
		//GIVEN
		Long clientId = 1L;
		Map<String, Object> expected = new HashMap<String, Object>();
		expected.put("description", "Factura equipos de oficina");
		expected.put("items", 2);
		
		//WHEN
		List<Invoice> result = invoiceService.findByClient(clientId);
		
		//THEN
		assertNotNull(result, "Debería encontrar facturas");
		assertEquals(expected.get("description"), result.get(0).getDescription(), "La descripción debería ser la esperada");
		assertEquals(expected.get("items"), result.get(0).getItems().size(), "La factura debería tener los items esperados");
	}
	
	@Test
	void shouldNotFindInvoices() {
		
		//GIVEN
		Long clientId = 3L;
		
		//WHEN
		List<Invoice> result = invoiceService.findByClient(clientId);
		
		//THEN
		assertNotNull(clientService.findById(clientId), "El cliente debería existir");
		assertTrue(result.isEmpty(), "La lista de facturas debería estar vacía");
	}
	
	@Test
	void shouldNotFindInvoicesIfClientNotExists() {
		
		//GIVEN
		Long clientId = 666L;
		
		//WHEN
		List<Invoice> result = invoiceService.findByClient(clientId);
		
		//THEN
		assertNull(clientService.findById(clientId), "El cliente no debería existir");
		assertNull(result, "No debería retornar facturas");
	}

	@Test
	void shouldCreateInvoice() {
		fail("Not yet implemented");
	}

	@Test
	void shouldUpdateInvoice() {
		fail("Not yet implemented");
	}

	@Test
	void shouldDeleteInvoice() {
		fail("Not yet implemented");
	}
}
