package com.myproject.e_book.ebook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.e_book.ebook.entity.Category;
import com.myproject.e_book.ebook.repository.CategoryRepository;
@Service
public class CategoryService implements CategoryServiceInterface {
	
	@Autowired
	CategoryRepository cr;
	
	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return cr.findAll();
	}

	@Override
	public Category getOne(Integer categoryId) {
		// TODO Auto-generated method stub
		return cr.getOne(categoryId);
	}

	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return cr.save(category);
	}

	@Override
	public void remove(Integer id) {
		// TODO Auto-generated method stub
		cr.deleteById(id);
	}

	@Override
	public Category getByName(String name) {
		// TODO Auto-generated method stub
		return cr.getByName(name);
	}

}
