package com.myproject.e_book.ebook.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity                 
@Table(name="users") 
public class User implements Serializable,UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	
	@Id                                 
	@GeneratedValue(strategy=IDENTITY) 
	@Column(name="user_id", unique=true, nullable=false) 
	private Integer id;
	
	@Column(name="firstname", unique=false, nullable=false, length = 30)
	private String firstname;
	
	@Column(name="lastname", unique=false, nullable=false, length = 30)
	private String lastname;
	
	@Column(name="username", unique=true, nullable=false, length = 30)
	private String username;
	  
	@Column(name="user_pasword", unique=false, nullable=false, length = 100)
	private String password;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="user_authority",
			joinColumns=@JoinColumn(name="user_id",referencedColumnName="user_id"),
			inverseJoinColumns = @JoinColumn(name="authority_id",referencedColumnName="id"))
	private Set<Authority> user_authorities = new HashSet<>();

	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "user")
	private Set<EBook> ebooks=new HashSet<EBook>();
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = true)
	private Category category;
	
	
	public User() {
		super();
	}
	
	
	public User(Integer id, String firstname, String lastname, String username, String user_password,
			Set<EBook> ebooks, Category category) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = user_password;
		
		this.ebooks = ebooks;
		this.category = category;
	}
	


	

	public void setPassword(String password) {
		this.password = password;
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

	public String getUser_password() {
		return password;
	}

	public void setUser_password(String user_password) {
		this.password = user_password;
	}

	


	public Set<EBook> getEbooks() {
		return ebooks;
	}


	public void setEbooks(Set<EBook> ebooks) {
		this.ebooks = ebooks;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}

	

	public Set<Authority> getUser_authorities() {
		return user_authorities;
	}
	
	public String getauthorities() {
		String autority= null;
        for (GrantedAuthority s : user_authorities ) {
            
            autority = s.getAuthority();
            
        }
		
		return autority;
	}


	public void setUser_authorities(Set<Authority> user_authorities) {
		this.user_authorities = user_authorities;
	}


	

	@Override
	 public Collection<? extends GrantedAuthority> getAuthorities() {
		 return this.user_authorities;
	 }


	


	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	
	
	
	
	
}
