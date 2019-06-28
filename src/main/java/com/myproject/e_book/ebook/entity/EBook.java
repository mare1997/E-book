package com.myproject.e_book.ebook.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.myproject.e_book.ebook.dto.CategoryDTO;

@Entity                 
@Table(name="categories") 
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id                                 
	@GeneratedValue(strategy=IDENTITY) 
	@Column(name="category_id", unique=true, nullable=false) 
	private Integer id;

	@Column(name="name", unique=true, nullable=false, length = 30)
	private String name;

	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "category")
	private Set<User> users=new HashSet<User>();
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "category")
	private Set<EBook> ebooks=new HashSet<EBook>();
	
	public Category() {
		super();
	}

	
	
	public Category(Integer id, String name, Set<User> users, Set<EBook> ebooks) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
		this.ebooks = ebooks;
	}
	public Category(CategoryDTO categoryDto) {
		super();
		this.id = categoryDto.getId();
		this.name = categoryDto.getName();
		
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Set<User> getUsers() {
		return users;
	}



	public void setUsers(Set<User> users) {
		this.users = users;
	}



	public Set<EBook> getEbooks() {
		return ebooks;
	}



	public void setEbooks(Set<EBook> ebooks) {
		this.ebooks = ebooks;
	}
	
	
	
	
}
