package com.myproject.e_book.ebook.dto;

import java.io.Serializable;

import com.myproject.e_book.ebook.entity.Category;

public class CategoryDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String name;
	
    
    public CategoryDTO() {
		super();
	}


	public CategoryDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public CategoryDTO(Category c) {
		this(c.getId(),c.getName());
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
    
    
}
