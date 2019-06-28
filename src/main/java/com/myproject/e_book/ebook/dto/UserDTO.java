package com.myproject.e_book.ebook.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.myproject.e_book.ebook.lucene.model.Metadata;

public class MetadataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String author;
	private List<String> keywords = new ArrayList<String>();
	private String filename;
	private String filedate;
	
	
	public MetadataDTO() {
		super();
	}
	public MetadataDTO(String title, String author, List<String> keywords, String filename, String filedate) {
		super();
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.filename = filename;
		this.filedate = filedate;
	}
	
	public MetadataDTO(Metadata m) {
		this(m.getTitle(),m.getAuthor(),m.getKeywords(),m.getFilename(),m.getFiledate());
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFiledate() {
		return filedate;
	}
	public void setFiledate(String filedate) {
		this.filedate = filedate;
	}
	
	
	
	
}
