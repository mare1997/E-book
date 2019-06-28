package com.myproject.e_book.ebook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.e_book.ebook.entity.Authority;
import com.myproject.e_book.ebook.repository.AuthorityRepository;




@Service
public class AuthoritySercive implements AuthorityServiceInterface {

	@Autowired
	AuthorityRepository authorityRepository;
	
	@Override
	public Authority getByName(String name) {
		
		return authorityRepository.getByName(name);
	}

}
