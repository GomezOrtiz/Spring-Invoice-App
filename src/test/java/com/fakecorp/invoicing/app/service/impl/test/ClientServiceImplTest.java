package com.fakecorp.invoicing.app.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fakecorp.invoicing.app.model.entity.Client;
import com.fakecorp.invoicing.app.service.ClientService;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;

@SpringBootTest
@DBRider
@DataSet(value="dataset.xml", cleanBefore = true, disableConstraints = true)
public class ClientServiceImplTest {
    
	@Autowired
	private ClientService clientService;

	@Test
    public void shouldFindAllClients() {
		
		// GIVEN 
			
		//WHEN
		List<Client> result = clientService.findAll();
		
		//THEN
		assertNotNull(result, "Debería haber encontrado algún cliente");
		assertEquals(5, result.size(), "El número de clientes debería ser el esperado");
	}
	
	@Test
	public void shouldFindClientById() {
		
		//GIVEN
		Long id = 5L;
		Client expected = new Client("José Luis", "Avilés", "peplu@gmail.com");
		
		//WHEN
		Client result = clientService.findById(id);
		
		//THEN
		assertNotNull(result, "Debería haber encontrado un cliente");
		assertEquals(expected.getName(), result.getName(), "El nombre del cliente debería ser el esperado");
		assertEquals(expected.getSurname(), result.getSurname(), "El apellido del cliente debería ser el esperado");
		assertEquals(expected.getEmail(), result.getEmail(), "El email del cliente debería ser el esperado");
	}
	
	@Test 
	public void shouldNotFindClientByIdIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		
		//WHEN
		Client result = clientService.findById(id);
		
		//THEN
		assertNull(result, "No debe encontrar cliente por una ID que no existe");
	}
	
	@Test
	public void shouldCreateClient() {
		
		//GIVEN
		Client newClient = new Client("Carlos", "Martínez", "carlitos@gmail.com");
		
		//WHEN
		clientService.create(newClient);
		List<Client> result = clientService.findAll();
		
		//THEN
		assertEquals(6, result.size(), "El número de clientes debería ser el esperado");
        assertEquals(newClient.getName(), result.get(result.size() -1).getName(), "El nombre del último cliente debería ser el esperado");
        assertNotNull(result.get(result.size() -1).getCreatedAt(), "La fecha de creación no debería estar vacía");
	}
	
	@Test
	public void shouldUpdateClient() {
		
		//GIVEN
		Long id = 4L;
		Client oldClient = clientService.findById(id);
		Client newClient = new Client("Pepe", "Ocaña", "pepuchis@gmail.com");
		newClient.setId(id);
		
		//WHEN
		clientService.update(newClient);
		Client updatedClient = clientService.findById(id);
		
		//THEN
		assertNotEquals(updatedClient.getName(), oldClient.getName(), "El nombre del cliente debería haber cambiado");
		assertNotEquals(updatedClient.getSurname(), oldClient.getSurname(), "El apellido del cliente debería haber cambiado");
		assertNotEquals(updatedClient.getEmail(), oldClient.getEmail(), "El email del cliente debería haber cambiado");
		assertEquals(newClient.getName(), updatedClient.getName(), "El nombre del cliente debería ser el esperado");
		assertEquals(newClient.getSurname(), updatedClient.getSurname(), "El apellido del cliente debería ser el esperado");
		assertEquals(newClient.getEmail(), updatedClient.getEmail(), "El email del cliente debería ser el esperado");
        assertNull(oldClient.getUpdatedAt(), "La fecha de actualización debería estar vacía antes del cambio");
        assertNotNull(updatedClient.getUpdatedAt(), "La fecha de actualización no debería estar vacía después del cambio");
	}
	
	@Test 
	public void shouldNotUpdateClientIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		Client oldClient = clientService.findById(id);
		int oldSize = clientService.findAll().size();
		
		Client newClient = new Client("Pepe", "Ocaña", "pepuchis@gmail.com");
		newClient.setId(id);
				
		//WHEN
		clientService.update(newClient);
		Client updatedClient = clientService.findById(id);		
		
		//THEN
		assertNull(oldClient, "El cliente no debe existir antes del update");
		assertNull(updatedClient, "El cliente no debe existir después del update");
		assertEquals(oldSize, clientService.findAll().size(), "No debe haber creado el cliente");
	}
	
	@Test
	public void shouldDeleteClient() {
		
		//GIVEN
		Long id = 4L;
		Client beforeDeleted = clientService.findById(id);
		Client expected = new Client("José Luis", "Avilés", "peplu@gmail.com");

		//WHEN
		clientService.delete(id);
		List<Client> result = clientService.findAll();
		
		//THEN
		assertEquals(4, result.size(), "El número de clientes debería ser el esperado");
        assertEquals("Enrique", beforeDeleted.getName(), "El nombre del cliente antes de ser borrado debería ser el esperado");
        assertEquals(expected.getName(), result.get(result.size() -1).getName(), "El nombre del último cliente tras la eliminación debería ser el esperado");
        assertNull(clientService.findById(id), "No debería encontrar el cliente borrado");
        
        clientService.create(beforeDeleted);
	}
	
	@Test 
	public void shouldNotDeleteClientByIdIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		int oldSize = clientService.findAll().size();
		
		//WHEN
		clientService.delete(id);
		
		//THEN
		assertEquals(oldSize, clientService.findAll().size(), "El número de clientes no debe haber cambiado");
	}

}
