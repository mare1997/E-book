package com.myproject.e_book.ebook.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.e_book.ebook.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

	Language getByName(String name);
}
