package com.myproject.e_book.ebook.services;

import java.util.List;

import com.myproject.e_book.ebook.entity.Category;



public interface CategoryServiceInterface {

	List<Category> getAll();
	Category getOne(Integer categoryId);
	Category save(Category category);
	void remove(Integer id);
	Category getByName(String name);
}
