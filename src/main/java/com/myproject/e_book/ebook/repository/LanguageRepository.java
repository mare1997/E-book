package com.myproject.e_book.ebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myproject.e_book.ebook.entity.EBook;

public interface EBookRepository extends JpaRepository<EBook, Integer> {
	EBook getByTitle(EBook title);
	
	@Query(value = "SELECT * FROM ebooks JOIN categories ON ebooks.category_id = categories.category_id WHERE categories.name LIKE ? ",nativeQuery=true)
	List<EBook> getEbooksByCategory(String category);
}
