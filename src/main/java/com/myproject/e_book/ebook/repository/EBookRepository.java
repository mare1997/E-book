package com.myproject.e_book.ebook.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.e_book.ebook.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Category getByName(String name);
}
