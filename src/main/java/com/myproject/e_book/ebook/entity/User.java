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

@Entity
@Table(name="languages")
public class Language implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id                                 
	@GeneratedValue(strategy=IDENTITY) 
	@Column(name="language_id", unique=true, nullable=false) 
	private Integer id;
	
	@Column(name="name", unique=false, nullable=false, length = 30)
	private String name;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "language")
	private Set<EBook> ebooks=new HashSet<EBook>();
	
	
	public Language() {
		super();
	}
	
	public Language(Integer id, String name, Set<EBook> ebooks) {
		super();
		this.id = id;
		this.name = name;
		this.ebooks = ebooks;
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

	public Set<EBook> getEbooks() {
		return ebooks;
	}

	public void setEbooks(Set<EBook> ebooks) {
		this.ebooks = ebooks;
	}
	
	
}
