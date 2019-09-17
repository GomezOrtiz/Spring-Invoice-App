package com.fakecorp.invoicing.app.dao.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.fakecorp.invoicing.app.model.dao.ClientDao;
import com.fakecorp.invoicing.app.model.entity.Client;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;

@SpringBootTest
@DBRider
@DataSet(value="dataset.xml", cleanBefore = true, disableConstraints = true)
public class ClientDaoTest {
    
	@Autowired
	private ClientDao clientDao;

	@Test
    public void shouldFindAllClients() {
		
		// GIVEN 
			
		//WHEN
		List<Client> result = (List<Client>) clientDao.findAll();
		
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
		Client result = clientDao.findById(id).orElse(null);
		
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
		Client result = clientDao.findById(id).orElse(null);
		
		//THEN
		assertNull(result, "No debe encontrar cliente por una ID que no existe");
	}
	
	@Test
	public void shouldCreateClient() {
		
		//GIVEN
		Client newClient = new Client("Carlos", "Martínez", "carlitos@gmail.com");
		
		//WHEN
		clientDao.save(newClient);
		List<Client> result = (List<Client>) clientDao.findAll();
		
		//THEN
		assertEquals(6, result.size(), "El número de clientes debería ser el esperado");
        assertEquals(newClient.getName(), result.get(result.size() -1).getName(), "El nombre del último cliente debería ser el esperado");
	}
	
	@Test
	public void shouldUpdateClient() {
		
		//GIVEN
		Long id = 4L;
		Client oldClient = clientDao.findById(id).orElse(null);
		Client newClient = new Client("Pepe", "Ocaña", "pepuchis@gmail.com");
		newClient.setId(id);
		
		//WHEN
		clientDao.save(newClient);
		Client updatedClient = clientDao.findById(id).orElse(null);
		
		//THEN
		assertNotEquals(updatedClient.getName(), oldClient.getName(), "El nombre del cliente debería haber cambiado");
		assertNotEquals(updatedClient.getSurname(), oldClient.getSurname(), "El apellido del cliente debería haber cambiado");
		assertNotEquals(updatedClient.getEmail(), oldClient.getEmail(), "El email del cliente debería haber cambiado");
		assertEquals(newClient.getName(), updatedClient.getName(), "El nombre del cliente debería ser el esperado");
		assertEquals(newClient.getSurname(), updatedClient.getSurname(), "El apellido del cliente debería ser el esperado");
		assertEquals(newClient.getEmail(), updatedClient.getEmail(), "El email del cliente debería ser el esperado");
	}
	
	@Test
	public void shouldDeleteClient() {
		
		//GIVEN
		Long id = 4L;
		Client beforeDeleted = clientDao.findById(id).orElse(null);
		Client expected = new Client("José Luis", "Avilés", "peplu@gmail.com");

		//WHEN
		clientDao.deleteById(id);
		List<Client> result = (List<Client>) clientDao.findAll();
		
		//THEN
		assertEquals(4, result.size(), "El número de clientes debería ser el esperado");
        assertEquals("Enrique", beforeDeleted.getName(), "El nombre del cliente antes de ser borrado debería ser el esperado");
        assertEquals(expected.getName(), result.get(result.size() -1).getName(), "El nombre del último cliente tras la eliminación debería ser el esperado");
        assertNull(clientDao.findById(id).orElse(null), "No debería encontrar el cliente borrado");
    }
	
	@Test 
	public void shouldNotDeleteClientByIdIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		int oldSize = ((List<Client>) clientDao.findAll()).size();
		
		//WHEN
		
		assertThrows(EmptyResultDataAccessException.class, ()-> {
			clientDao.deleteById(id);
		}, "Debería producirse un error");
		
		//THEN
		assertEquals(oldSize, ((List<Client>) clientDao.findAll()).size(), "El número de clientes no debe haber cambiado");
	}

}
