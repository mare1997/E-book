package com.myproject.e_book.ebook.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.myproject.e_book.ebook.dto.LanguageDTO;

import com.myproject.e_book.ebook.entity.Language;
import com.myproject.e_book.ebook.services.LanguageServiceInterface;

@RestController
@RequestMapping(value = "ebook/languages")
public class LanguageContoller {

	@Autowired
	private LanguageServiceInterface lsi;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping()
	public ResponseEntity<List<LanguageDTO>> getAllLanguage(){
    	logger.info("GET metoda, zahtev za sve jezike. ");

		List<Language> languages = lsi.getAll();
		List<LanguageDTO> languagesDTO= new ArrayList<LanguageDTO>();
		for(Language l: languages) {
			languagesDTO.add(new LanguageDTO(l));
		}
		
		return new ResponseEntity<List<LanguageDTO>>(languagesDTO,HttpStatus.OK);
	}
	@GetMapping(value = "/{name}")
	public ResponseEntity<LanguageDTO> getCategoryById(@PathVariable("name") String name){
    	logger.info("GET metoda, zahtev za jezik za ime: "+name );

    	Language l= lsi.getByName(name);
		if(l == null) {
			return new ResponseEntity<LanguageDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<LanguageDTO>(new LanguageDTO(l),HttpStatus.OK);
	}
}
