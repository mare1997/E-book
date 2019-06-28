package com.myproject.e_book.ebook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.e_book.ebook.entity.EBook;
import com.myproject.e_book.ebook.repository.EBookRepository;

@Service
public class EBookService implements EBookServiceInterface{
	@Autowired
	EBookRepository ebr;

	@Override
	public EBook getOne(Integer EBookId) {
		// TODO Auto-generated method stub
		return ebr.getOne(EBookId);
	}

	@Override
	public EBook save(EBook eBook) {
		// TODO Auto-generated method stub
		return ebr.save(eBook);
	}

	@Override
	public void remove(Integer id) {
		// TODO Auto-generated method stub
		ebr.deleteById(id);
	}

	@Override
	public EBook getByTitle(EBook title) {
		// TODO Auto-generated method stub
		return ebr.getByTitle(title) ;
	}

	@Override
	public List<EBook> getAll() {
		// TODO Auto-generated method stub
		return ebr.findAll();
	}

	@Override
	public List<EBook> getEBooksByCategory(String category) {
		// TODO Auto-generated method stub
		return ebr.getEbooksByCategory(category);
	}
}
