package com.myproject.e_book.ebook.dto;

import java.io.Serializable;
import java.util.Iterator;

import org.springframework.security.core.GrantedAuthority;

import com.myproject.e_book.ebook.entity.User;






public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String autority;
	private String category;
	
	
	
	
	public UserDTO() {
		super();
	}
	public UserDTO(Integer id, String firstname, String lastname, String username, String password,String autority,String category) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.autority = autority;
		this.category = category;
	}
	
	
	public UserDTO(User user) {
		this(user.getId(),user.getFirstname(),user.getLastname(),user.getUsername(),user.getUser_password(),user.getauthorities(),user.getCategory().getName());
    }
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAutority() {
		return autority;
	}
	public void setAutority(String autority) {
		this.autority = autority;
	}
	
	
}
