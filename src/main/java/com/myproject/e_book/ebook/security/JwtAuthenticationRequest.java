package com.myproject.e_book.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.e_book.ebook.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	User getByUsername(String username);
	User getByUsernameAndPassword(String username,String password);
}
