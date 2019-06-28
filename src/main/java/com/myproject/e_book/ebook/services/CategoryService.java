package com.myproject.e_book.ebook.services;

import com.myproject.e_book.ebook.entity.Authority;

public interface AuthorityServiceInterface {
	Authority getByName(String name);
}
