package com.myproject.e_book.ebook.dto;

import java.io.Serializable;

import com.myproject.e_book.ebook.entity.EBook;






public class EBookDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String keywords;
	private Integer publication_year;
	private String filename;
	private String mime;
	private String author;
	private UserDTO user;
	private CategoryDTO category;
	private LanguageDTO language;
	
	public EBookDTO() {
		super();
	}
	public EBookDTO(Integer id, String title, String keywords, Integer publication_year, String filename, String mime,String author,
			UserDTO user, CategoryDTO category, LanguageDTO language) {
		super();
		this.id = id;
		this.title = title;
		this.keywords = keywords;
		this.publication_year = publication_year;
		this.filename = filename;
		this.mime = mime;
		this.author = author;
		this.category = category;
		this.language = language;
	}
	public EBookDTO(EBook e) {
		this(e.getId(),e.getTitle(),e.getKeywords(),e.getPublication_year(),e.getFilename(),e.getMIME(),e.getAuthor(),new UserDTO(e.getUser()),new CategoryDTO(e.getCategory()),new LanguageDTO(e.getLanguage()));
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
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public CategoryDTO getCategory() {
		return category;
	}
	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	public LanguageDTO getLanguage() {
		return language;
	}
	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}
	
	
	
}
