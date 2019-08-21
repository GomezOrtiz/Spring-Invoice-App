package com.cursoudemy.springboot.app.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cursoudemy.springboot.app.model.Client;

public interface ClientDao extends PagingAndSortingRepository<Client, Long>{
	
}
