package com.myproject.e_book.ebook.services;

import java.util.List;

import com.myproject.e_book.ebook.entity.EBook;



public interface EBookServiceInterface {

	List<EBook> getAll();
	EBook getOne(Integer 	EBookId);
	EBook save(	EBook 	eBook);
	void remove(Integer id);
	EBook getByTitle(EBook title);
	List<EBook> getEBooksByCategory(String category);
}
