package com.cursoudemy.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cursoudemy.springboot.app.model.entity.Client;

public interface ClientService {
	
	public List<Client> findAll();

	public Page<Client> getClientsByPage(int numPage, int maxPages);
	
	Page<Client> getClientsByNameAndPage(String name, int numPage, int maxPages);
	
	public Client findById(Long id);
	
	public void create(Client client);
	
	public void update(Client client);
	
	public void delete(Long id);
}
