package com.myproject.e_book.ebook.entity;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

@Entity
@Table(name="authority")
public class Authority implements GrantedAuthority{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="name")
	String name;
	
	@Override
	public String getAuthority() {
		return name;
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

	
}
