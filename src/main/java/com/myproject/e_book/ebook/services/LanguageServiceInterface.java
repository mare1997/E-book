package com.myproject.e_book.ebook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.e_book.ebook.entity.Language;
import com.myproject.e_book.ebook.repository.LanguageRepository;

@Service
public class LanguageService implements LanguageServiceInterface {

	@Autowired
	LanguageRepository lr;

	@Override
	public List<Language> getAll() {
		// TODO Auto-generated method stub
		return lr.findAll();
	}

	@Override
	public Language getOne(Integer languageId) {
		// TODO Auto-generated method stub
		return lr.getOne(languageId);
	}

	@Override
	public Language save(Language language) {
		// TODO Auto-generated method stub
		return lr.save(language);
	}

	@Override
	public void remove(Integer id) {
		// TODO Auto-generated method stub
		lr.deleteById(id);
	}

	@Override
	public Language getByName(String name) {
		// TODO Auto-generated method stub
		return lr.getByName(name);
	}
}
