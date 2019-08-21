package com.cursoudemy.springboot.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "clients")
public class Client implements Serializable {
	
	private static final long serialVersionUID = -3435528331847915849L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String surname;

	private String email;

	@Column(name = "created_at")
	@Temporal(value = TemporalType.DATE)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(value = TemporalType.DATE)
	private Date updatedAt;
	
	@OneToMany(mappedBy="client", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Invoice> invoices;
	
	public Client() {
		invoices = new ArrayList<Invoice>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	public void addInvoice(Invoice invoice) {
		invoices.add(invoice);
	}
	
}
