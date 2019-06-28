package com.myproject.e_book.ebook.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.e_book.ebook.dto.CategoryDTO;
import com.myproject.e_book.ebook.entity.Category;
import com.myproject.e_book.ebook.services.CategoryServiceInterface;





@RestController
@RequestMapping(value = "ebook/category")
public class CategoryController {

	@Autowired
	private CategoryServiceInterface categoryServiceInterface;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value = "/{name}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("name") String name){
    	logger.info("GET metoda, zahtev za kategorije za ime: "+name );

		Category category= categoryServiceInterface.getByName(name);
		if(category == null) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<CategoryDTO>(new CategoryDTO(category),HttpStatus.OK);
	}
	@GetMapping()
	public ResponseEntity<List<CategoryDTO>> getAllCategory(){
    	logger.info("GET metoda, zahtev za sve kategorije. ");

		List<Category> categories = categoryServiceInterface.getAll();
		List<CategoryDTO> categoriesDTO= new ArrayList<CategoryDTO>();
		for(Category c: categories) {
			categoriesDTO.add(new CategoryDTO(c));
		}
		
		return new ResponseEntity<List<CategoryDTO>>(categoriesDTO,HttpStatus.OK);
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){
		logger.info("POST metoda, dodavanje kategorija" );
		Category category= categoryServiceInterface.getByName(categoryDTO.getName());
		if(category != null) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.FORBIDDEN);
		}
		Category newcategory= new Category();
		newcategory.setName(categoryDTO.getName());
		categoryServiceInterface.save(newcategory);
		
		
		return new ResponseEntity<CategoryDTO>(new CategoryDTO(newcategory),HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/edit/{id}")
	public ResponseEntity<CategoryDTO> editCategoy(@RequestBody CategoryDTO categoryDTO, @PathVariable("id")int id){
		logger.info("PUT metoda, edit kategorije sa id: "+id );
		Category category = categoryServiceInterface.getOne(id);
		if(category == null) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND);
		}
		category.setName(categoryDTO.getName());
		categoryServiceInterface.save(category);
		
		return new ResponseEntity<CategoryDTO>(new CategoryDTO(category),HttpStatus.OK);
	}
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") int id){
		logger.info("Delete metoda, zahtev za brisanje kategorije sa id: "+ id );
		Category category= categoryServiceInterface.getOne(id);
		if(category == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		categoryServiceInterface.remove(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
}
