package com.fakecorp.invoicing.app.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.fakecorp.invoicing.app.model.entity.User;

public interface UserDao extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
}
