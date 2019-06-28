package com.myproject.e_book.ebook.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name= "ebooks")
public class EBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id                                 
	@GeneratedValue(strategy=IDENTITY) 
	@Column(name="ebook_id", unique=true, nullable=false) 
	private Integer id;
	
	@Column(name="title", unique=false, nullable=false, length = 80)
	private String title;
	
	@Column(name="keywords", unique=false, nullable=false, length = 120)
	private String keywords;
	
	@Column(name="publication_year", unique=false, nullable=false)
	private Integer publication_year;
	
	@Column(name="filename", unique=false, nullable=false, length = 200)
	private String filename;
	
	@Column(name="mime", unique=false, nullable=false, length = 100)
	private String mime;
	
	@Column(name="author", unique=false, nullable=false, length = 80)
	private String author;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
	private Language language;
	
	
	
	public EBook() {
		super();
	}
	
	

	public EBook(Integer id, String title, String keywords, Integer publication_year, String filename, String mime,String author,
			User user, Category category, Language language) {
		super();
		this.id = id;
		this.title = title;
		this.keywords = keywords;
		this.publication_year = publication_year;
		this.filename = filename;
		this.mime = mime;
		this.author = author;
		this.user=user;
		this.category = category;
		this.language = language;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getPublication_year() {
		return publication_year;
	}

	public void setPublication_year(Integer publication_year) {
		this.publication_year = publication_year;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMIME() {
		return mime;
	}

	public void setMIME(String mime) {
		this.mime = mime;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}
	
	



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	public Language getLanguage() {
		return language;
	}



	public void setLanguage(Language language) {
		this.language = language;
	}
	
	
	
	
}
