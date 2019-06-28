package com.myproject.e_book.ebook.services;

import java.util.List;


import com.myproject.e_book.ebook.entity.Language;

public interface LanguageServiceInterface {

	List<Language> getAll();
	Language getOne(Integer languageId);
	Language save(Language language);
	void remove(Integer id);
	Language getByName(String name);
}
