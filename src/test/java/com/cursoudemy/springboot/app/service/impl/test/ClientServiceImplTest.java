package com.cursoudemy.springboot.app.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cursoudemy.springboot.app.SpringBootDataJpaApplication;
import com.cursoudemy.springboot.app.model.entity.Client;
import com.cursoudemy.springboot.app.service.ClientService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDataJpaApplication.class)
public class ClientServiceImplTest {
	
	@Autowired
	private ClientService clientService;

	@Test
	public void shouldFindAllClients() {
		
		// GIVEN 
		@SuppressWarnings("serial")
		List<Client> expected = new ArrayList<Client>() {{ 
			add(new Client("David", "Gómez", "dagor@gmail.com")); 
			add(new Client("Lucía", "Astray", "lulas@gmail.com"));
			add(new Client("Alfonso", "Cerezuela", "fons@gmail.com"));
			add(new Client("Alfonso", "Cerezuela", "fons@gmail.com"));
			add(new Client("Enrique", "Álvarez de Toledo", "henry@gmail.com"));
			add(new Client("José Luis", "Avilés", "peplu@gmail.com"));
			}};
			
		//WHEN
		List<Client> result = clientService.findAll();
		
		
		//THEN
		assertNotNull("Debería haber encontrado algún cliente", result);
        assertThat(result).hasSize(5);
        assertEquals("El nombre del primer cliente debería ser el esperado", result.get(0).getName(),expected.get(0).getName());
        assertEquals("El nombre del último cliente debería ser el esperado", result.get(result.size() -1).getName(),expected.get(expected.size() -1).getName());
	}
	
	@Test
	public void shouldFindOneClientById() {
		
		//GIVEN
		Long id = 5L;
		Client expected = new Client("José Luis", "Avilés", "peplu@gmail.com");
		
		//WHEN
		Client result = clientService.findById(id);
		
		//THEN
		assertNotNull("Debería haber encontrado un cliente", result);
		assertEquals("El nombre del cliente debería ser el esperado", result.getName(), expected.getName());
	}
	
	@Test 
	public void shouldNotFindClientByIdIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		
		//WHEN
		Client result = clientService.findById(id);
		
		//THEN
		assertNull("No debe encontrar cliente por una ID que no existe", result);
	}
	
	@Test
	public void shouldCreateNewClient() {
		
		//GIVEN
		Client newClient = new Client("Carlos", "Martínez", "carlitos@gmail.com");
		
		//WHEN
		clientService.create(newClient);
		List<Client> result = clientService.findAll();
		
		//THEN
        assertThat(result).hasSize(6);
        assertEquals("El nombre del último cliente debería ser el esperado", result.get(result.size() -1).getName(), newClient.getName());
        assertNotNull("La fecha de creación no debería estar vacía", result.get(result.size() -1).getCreatedAt());
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
		assertNotEquals("El nombre del cliente debería haber cambiado", oldClient.getName(), updatedClient.getName());
		assertNotEquals("El apellido del cliente debería haber cambiado", oldClient.getSurname(), updatedClient.getSurname());
		assertNotEquals("El email del cliente debería haber cambiado", oldClient.getEmail(), updatedClient.getEmail());
        assertNull("La fecha de actualización debería estar vacía antes del cambio", oldClient.getUpdatedAt());
        assertNotNull("La fecha de actualización no debería estar vacía después del cambio", updatedClient.getUpdatedAt());
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
		assertNull("El cliente no debe existir antes del update", oldClient);
		assertNull("El cliente no debe existir después del update", updatedClient);
		assertEquals("No debe haber creado el cliente", oldSize, clientService.findAll().size());
	}
	
	@Test
	public void shouldDeleteClient() {
		
		//GIVEN
		Long id = 6L;
		Client beforeDeleted = clientService.findById(id);
		Client expected = new Client("José Luis", "Avilés", "peplu@gmail.com");

		//WHEN
		clientService.delete(id);
		List<Client> result = clientService.findAll();
		
		//THEN
        assertThat(result).hasSize(5);
        assertEquals("El nombre del cliente antes de ser borrado debería ser el esperado", beforeDeleted.getName(), "Carlos");
        assertEquals("El nombre del último cliente tras la eliminación debería ser el esperado", result.get(result.size() -1).getName(), expected.getName());
        assertNull("No debería encontrar el cliente borrado", clientService.findById(id));
	}
	
	@Test 
	public void shouldNotDeleteClientByIdIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		int oldSize = clientService.findAll().size();
		
		//WHEN
		clientService.delete(id);
		
		//THEN
		assertEquals("El número de clientes no debe haber cambiado", oldSize, clientService.findAll().size());
	}

}
