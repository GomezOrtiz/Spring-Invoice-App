package com.cursoudemy.springboot.app.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cursoudemy.springboot.app.model.entity.Client;

public interface InvoiceDao extends PagingAndSortingRepository<Client, Long>{
	
}
