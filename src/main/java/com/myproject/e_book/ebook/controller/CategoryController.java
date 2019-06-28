package com.myproject.e_book.ebook.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.e_book.ebook.entity.User;
import com.myproject.e_book.ebook.entity.UserTokenState;
import com.myproject.e_book.ebook.security.JwtAuthenticationRequest;
import com.myproject.e_book.ebook.security.TokenHelper;
import com.myproject.e_book.ebook.services.CustomUserDetailsService;



@RestController
@RequestMapping(value = "api/auth")
@CrossOrigin("*")
public class AuthenticationController {

	 @Autowired
	    TokenHelper tokenHelper;

	    @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private CustomUserDetailsService userDetailsService;


	    @RequestMapping(value = "/login", method = RequestMethod.POST)
	    public ResponseEntity<?> createAuthenticationToken(
	            @RequestBody JwtAuthenticationRequest authenticationRequest,
	            HttpServletResponse response
	    ) throws AuthenticationException, IOException {
	    	
	        // Izvrsavanje security dela
	        final Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        authenticationRequest.getUsername(),
	                        authenticationRequest.getPassword()
	                )
	        );

	        // Ubaci username + password u kontext
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        // Kreiraj token
	        User user = (User)authentication.getPrincipal();
	        String jws = tokenHelper.generateToken( user.getUsername());
	        String autority= null;
	        for (GrantedAuthority s : user.getAuthorities() ) {
	            
	            autority = s.getAuthority();
	            
	        }
	        
	        
	        // Vrati token kao odgovor na uspesno autentifikaciju
	        return ResponseEntity.ok(new UserTokenState(jws,user.getId(),autority));
	    }

	    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
	    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
	        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
	        return new ResponseEntity<>(HttpStatus.OK);
	    }

	    static class PasswordChanger {
	        public String oldPassword;
	        public String newPassword;
	    }
}
