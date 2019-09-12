package com.cursoudemy.springboot.app.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	@Transient
	private String DEFAULT_IMAGE = "https://res.cloudinary.com/ikeiala/image/upload/v1568284998/user.png";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30, unique = true)
	private String username;

	@Column(length = 60)
	private String password;

	private Boolean enabled;
	
	private String image;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Role> roles;
	
	@PrePersist
	private void setDefaults() {
		if(getEnabled() == null) {
			setEnabled(true);
		}
		if(getImage().isEmpty() || getImage() == null) {
			setImage(DEFAULT_IMAGE);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<Role> parseRoles(List<String> roles) {
		
		List<Role> newRoles = new ArrayList<Role>();
		
		if(roles.size() == 1 && roles.get(0).equals("ROLE_ADMIN")) {
			roles.add("ROLE_USER");
		}
		
		for(String stringRole : roles) {
			Role role = new Role();
			role.setAuthority(stringRole);
			newRoles.add(role);
		}
		
		return newRoles;
	}

	private static final long serialVersionUID = -6919043484230305815L;
}
