package com.myproject.e_book.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.e_book.ebook.entity.Authority;





public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	Authority getByName(String name);
}
