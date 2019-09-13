package com.fakecorp.invoicing.app.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fakecorp.invoicing.app.model.entity.Invoice;
import com.fakecorp.invoicing.app.service.ClientService;
import com.fakecorp.invoicing.app.service.InvoiceService;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;

@SpringBootTest
@DBRider
@DataSet(value="dataset.xml", cleanBefore = true)
class InvoiceServiceImplTest {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ClientService clientService;

	@Test
	void shouldFindInvoicesByClient() {
		
		//GIVEN
		Long clientId = 1L;
		
		//WHEN
		List<Invoice> result = invoiceService.findByClient(clientId);
		
		//THEN
		assertNotNull(result, "Debería encontrar facturas");
		assertThat(result).isNotEmpty();
		assertEquals("Factura equipos de oficina", result.get(0).getDescription(), "La descripción debería ser la esperada");
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
}
