package com.myproject.e_book.ebook.lucene.search;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import com.myproject.e_book.ebook.lucene.model.Metadata;

public class SearchMetadata {

		public static Metadata getMetadatas(File file) {
			Metadata d= new Metadata();
			try {
				
				PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
				parser.parse();
				PDDocument pdf = parser.getPDDocument();
				PDDocumentInformation info = pdf.getDocumentInformation();
				
				String title = ""+info.getTitle();
				d.setTitle(title);
				
				String keywords = ""+info.getKeywords();
				if(keywords != null){
					String[] splittedKeywords = keywords.split(" ");
					d.setKeywords(new ArrayList<String>(Arrays.asList(splittedKeywords)));
				}
				d.setFilename(file.getCanonicalPath());
				System.out.println("Putanja koja ce biti prikaza u metadata: " +file.getCanonicalPath());
				String author=""+info.getAuthor();
				d.setAuthor(author);
				
				String modificationDate=DateTools.dateToString(new Date(file.lastModified()),DateTools.Resolution.DAY);
				d.setFiledate(modificationDate);
				
				pdf.close();
				
			} catch (Exception e) {
				System.out.println("Greksa pri konvertovanju dokumenta u pdf");
			}
			return d;
		}

		
}
