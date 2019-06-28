package com.myproject.e_book.ebook.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.e_book.ebook.dto.UserDTO;
import com.myproject.e_book.ebook.entity.Authority;
import com.myproject.e_book.ebook.entity.Category;
import com.myproject.e_book.ebook.entity.User;
import com.myproject.e_book.ebook.services.AuthorityServiceInterface;
import com.myproject.e_book.ebook.services.CategoryServiceInterface;
import com.myproject.e_book.ebook.services.UserServiceInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "ebook/users")
public class UserController {
	
	@Autowired
	private UserServiceInterface userServiceIterface;
	
	@Autowired
	private AuthorityServiceInterface asi; 
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Autowired
	private CategoryServiceInterface categoryServiceInterface;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id){
    	logger.info("GET metoda, zahtev za user sa id:"+id );

		User user= userServiceIterface.getOne(id);
		if(user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<UserDTO>(new UserDTO(user),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(){
    	logger.info("GET metoda, zahtev za sve usere" );

		List<User> users = userServiceIterface.getAll();
		List<UserDTO> userDTO = new ArrayList<UserDTO>();
		for (User u : users) {
			userDTO.add(new UserDTO(u));
		}
		return new ResponseEntity<List<UserDTO>>(userDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "get/{username}")
	public ResponseEntity<UserDTO> getUserbyUsername(@PathVariable("username") String username){
    	logger.info("GET metoda, zahtev za user sa username: "+ username );

		User user= userServiceIterface.getByUsername(username);
		if(user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<UserDTO>(new UserDTO(user),HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDto){
		logger.info("POST metoda, dodavanje usera:" + userDto );
		User username=userServiceIterface.getByUsername(userDto.getUsername());
		if(username != null) {
			return new ResponseEntity<UserDTO>(HttpStatus.FORBIDDEN);
		}
		User user= new User();
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setUsername(userDto.getUsername());
		user.setUser_password(passwordencoder.encode(userDto.getPassword()));
		
		Authority a = asi.getByName(userDto.getAutority());
		
		Set<Authority> aa= new HashSet<>();
		aa.add(a);
		user.setUser_authorities(aa);
		Category c  =categoryServiceInterface.getByName(userDto.getCategory());
		user.setCategory(c);
		
		userServiceIterface.save(user);
		return new ResponseEntity<UserDTO>(new UserDTO(user),HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/update/{id}" ,consumes = "application/json" )
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto,@PathVariable("id")int id){
		logger.info("PUT metoda, update usera sa id: "+id );
		User user=userServiceIterface.getOne(id);
		if(user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		}
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		System.out.println("1. "+userDto.getPassword());
		System.out.println("2. "+passwordencoder.encode(userDto.getPassword()));
		
		user.setUser_password(passwordencoder.encode(userDto.getPassword()));
		
		Authority a = asi.getByName(userDto.getAutority());
		
		Set<Authority> aa= new HashSet<>();
		aa.add(a);
		user.setUser_authorities(aa);
		
		Category c= categoryServiceInterface.getByName(userDto.getCategory());
		user.setCategory(c);
		userServiceIterface.save(user);
		return new ResponseEntity<UserDTO>(new UserDTO(user),HttpStatus.OK);
		
	}
	@PutMapping(value = "/edit/{id}" ,consumes = "application/json" )
	public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDto,@PathVariable("id")int id){
		logger.info("PUT metoda, edit usera sa id: "+id );
		User user=userServiceIterface.getOne(id);
		if(user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		}
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		userServiceIterface.save(user);
		return new ResponseEntity<UserDTO>(new UserDTO(user),HttpStatus.OK);
		
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") int id){
		logger.info("Delete metoda, zahtev za brisanje usera sa id: "+ id );
		User user= userServiceIterface.getOne(id);
		if(user == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		userServiceIterface.remove(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
}
