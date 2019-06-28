package com.myproject.e_book.ebook.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.ResourceBundle;


import org.apache.lucene.index.IndexableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.e_book.ebook.dto.EBookDTO;
import com.myproject.e_book.ebook.dto.MetadataDTO;

import com.myproject.e_book.ebook.entity.Category;
import com.myproject.e_book.ebook.entity.EBook;
import com.myproject.e_book.ebook.entity.Language;
import com.myproject.e_book.ebook.entity.User;
import com.myproject.e_book.ebook.lucene.indexing.Indexer;
import com.myproject.e_book.ebook.lucene.model.IndexUnit;
import com.myproject.e_book.ebook.lucene.model.Metadata;
import com.myproject.e_book.ebook.lucene.model.UploadModel;
import com.myproject.e_book.ebook.lucene.search.SearchMetadata;
import com.myproject.e_book.ebook.services.CategoryServiceInterface;
import com.myproject.e_book.ebook.services.EBookServiceInterface;
import com.myproject.e_book.ebook.services.LanguageServiceInterface;
import com.myproject.e_book.ebook.services.UserServiceInterface;


@RestController
@RequestMapping(value = "ebook/books")
public class EBookController {
	
	@Autowired
	private EBookServiceInterface esi;
	
	@Autowired
	private CategoryServiceInterface csi;
	
	@Autowired
	private UserServiceInterface usi;
	
	@Autowired
	private LanguageServiceInterface lsi;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String DATA_DIR_PATH;
	
	static {
		ResourceBundle rb=ResourceBundle.getBundle("application");
		DATA_DIR_PATH=rb.getString("dataDir");
	}
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<EBookDTO> getEbook(@PathVariable("id") int id){
    	logger.info("GET metoda, zahtev za kljigu sa id:"+id );
    	EBook ebook=esi.getOne(id);
        if(ebook == null)
            return new ResponseEntity<EBookDTO>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<EBookDTO>(new EBookDTO(ebook),HttpStatus.OK);
    }
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
    public ResponseEntity<List<EBookDTO>> getEbooks(){
    	logger.info("GET metoda, zahtev za sve kljige");
    	List<EBook> ebooks=esi.getAll();
        List<EBookDTO> ebookDto=new ArrayList<>();
        for (EBook e:ebooks) {
            ebookDto.add(new EBookDTO(e));
            
        }
        return new ResponseEntity<List<EBookDTO>>(ebookDto,HttpStatus.OK);
    }
	@RequestMapping(value="/ebooksbycategory/{category}", method = RequestMethod.GET)
    public ResponseEntity<List<EBookDTO>> getEbooksByCategory(@PathVariable("category")String category){
    	logger.info("GET metoda, zahtev za sve kljige");
    	List<EBook> ebooks=esi.getEBooksByCategory(category);
        List<EBookDTO> ebookDto=new ArrayList<>();
        for (EBook e:ebooks) {
            ebookDto.add(new EBookDTO(e));
            
        }
        return new ResponseEntity<List<EBookDTO>>(ebookDto,HttpStatus.OK);
    }
	
	@DeleteMapping(value = "/index/delete/{filename}")
    private ResponseEntity<Void> deleteIndex(@PathVariable("filename")String filename){
		
		System.out.println("1. "+filename);
		ClassLoader cl=Thread.currentThread().getContextClassLoader();
		URL urlpath= cl.getResource(DATA_DIR_PATH+"/"+filename + ".pdf");
		System.out.println("2. "+urlpath);
		String path = getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + "\\" + filename+ ".pdf";
		System.out.println("3. "+path);
		Indexer.getInstance().delete(path);
		System.out.println("4. "+Indexer.getInstance().delete(path));
		File file = new File(urlpath.getFile());
		System.out.println("5. "+urlpath.getFile());
		System.out.println("6. "+file);
		/*if(file.delete()) {
			System.out.println("Usao");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			System.out.println("Izasao");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}*/
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteEbook(@PathVariable("id") int id){
    	logger.info("DELETE metoda, zahtev za brisanje knjige sa id: "+id );
    	EBook ebook= esi.getOne(id);
        if(ebook == null) {
        	return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        esi.remove(id);
       
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
	
	@PostMapping(value = "/file")
	public ResponseEntity<MetadataDTO> getMetadata(@RequestParam("file") MultipartFile file) throws IOException{
		Metadata m = SearchMetadata.getMetadatas(convert(file));
		logger.info("Metapodati: "+m );
		MetadataDTO mdto=new MetadataDTO(m);
		return new ResponseEntity<MetadataDTO>(mdto,HttpStatus.OK);
	}
	
	public File convert(MultipartFile file) throws IOException
    {    
		
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile(); 
        FileOutputStream fos = new FileOutputStream(convFile); 
        fos.write(file.getBytes());
        fos.close(); 
        return convFile;
    }
	@PostMapping(value = "/add")
	public ResponseEntity<EBookDTO> addEbook(@RequestBody EBookDTO ebookDto) throws Exception{
		logger.info("POST metoda, dodavanje ebook:" + ebookDto.toString() );
		EBook e =new EBook();
		e.setTitle(ebookDto.getTitle());
		e.setAuthor(ebookDto.getAuthor());
		e.setKeywords(ebookDto.getKeywords());
		e.setFilename(ebookDto.getFilename());
		e.setMIME(ebookDto.getMIME());
		e.setPublication_year(ebookDto.getPublication_year());
		Category category=csi.getByName(ebookDto.getCategory().getName());
		e.setCategory(category);
		Language language= lsi.getByName(ebookDto.getLanguage().getName());
		e.setLanguage(language);
		User user= usi.getOne(ebookDto.getUser().getId());
		e.setUser(user);
		esi.save(e);
		return new ResponseEntity<EBookDTO>(new EBookDTO(e),HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/edit/{id}/{filename}" ,consumes = "application/json" )
	public ResponseEntity<EBookDTO> editEbook(@RequestBody EBookDTO ebookDto,@PathVariable("id")int id,@PathVariable("filename")String filename) throws Exception{
		logger.info("Put metoda, update ebook:" + ebookDto.toString() );
		
		EBook e =esi.getOne(id);
		if(e == null) {
			return new ResponseEntity<EBookDTO>(HttpStatus.NOT_FOUND);
		}
		String fileName = getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + "\\" +filename + ".pdf";
		
		IndexUnit indexUnit = Indexer.getInstance().getHandler(fileName).getIndexUnit(new File(fileName));
		System.out.println("ebook title: "+ebookDto.getTitle());
		indexUnit.setTitle(ebookDto.getTitle());
		System.out.println("indexunit title: "+indexUnit.getTitle());
		indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(ebookDto.getKeywords().split(" "))));
		indexUnit.setAuthor(ebookDto.getAuthor());
		
		List<IndexableField> fields = new ArrayList<>(Arrays.asList(indexUnit.getLuceneDocument().getField("title"),
				indexUnit.getLuceneDocument().getField("keyword"),
				indexUnit.getLuceneDocument().getField("author")));
		
		Indexer.getInstance().updateDocument(fileName, fields);
		System.out.println("Putanja za update indeksiranje: "+fileName);
		System.out.println("Update indeksiranje :"+ Indexer.getInstance().updateDocument(fileName, fields)); 
		
		e.setTitle(ebookDto.getTitle());
		e.setAuthor(ebookDto.getAuthor());
		e.setKeywords(ebookDto.getKeywords());
		e.setFilename(ebookDto.getFilename());
		e.setMIME(ebookDto.getMIME());
		e.setPublication_year(ebookDto.getPublication_year());
		Category category=csi.getByName(ebookDto.getCategory().getName());
		e.setCategory(category);
		Language language= lsi.getByName(ebookDto.getLanguage().getName());
		e.setLanguage(language);
		User user= usi.getOne(ebookDto.getUser().getId());
		e.setUser(user);
		esi.save(e);
		return new ResponseEntity<EBookDTO>(new EBookDTO(e),HttpStatus.OK);
	}
	
	
	@PostMapping("/index/add")
    public ResponseEntity<String> multiUploadFileModel(@ModelAttribute UploadModel model) {
    	logger.info("POST metoda, indeksiranje" );
    	
        try {
        	indexUploadedFile(model);
        	} catch (IOException e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
    }
	
	@PostMapping("/downloadPdf/{filename}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("filename")String filename) {
    	logger.info("GET metoda, download" );
    	
        try {
        	 System.out.println(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + "\\" +filename + ".pdf");
        	 File file = new File(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + "\\" +filename + ".pdf");
             HttpHeaders respHeaders = new HttpHeaders();
             respHeaders.setContentType(MediaType.APPLICATION_PDF);
             respHeaders.setContentLength(file.length());
             respHeaders.setContentDispositionFormData("attachment", file.getName());
             InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
             return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        	
        	} catch (Exception e) {
        		return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

       
    }
	
	
    private File getResourceFilePath(String path) {
		
		URL url = this.getClass().getClassLoader().getResource(path);
	    
	    File file = null;
	    try {
	        file = new File(url.toURI());
	    } catch (URISyntaxException e) {
	        file = new File(url.getPath());
	    }   
	    return file;
	}
    //save file
    private String saveUploadedFile(MultipartFile file) throws IOException {
    	String retVal = null;
        if (! file.isEmpty()) {
            byte[] bytes = file.getBytes();
           
            Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
            System.out.println("Putanja gde bi trebalo da se sacuva fajl: "+path);
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }
    
    private void indexUploadedFile(UploadModel model) throws IOException{
    	
    	for (MultipartFile file : model.getFiles()) {

            if (file.isEmpty()) {
                continue; //next please
            }
            String fileName = saveUploadedFile(file);
            if(fileName != null){
            	IndexUnit indexUnit = Indexer.getInstance().getHandler(fileName).getIndexUnit(new File(fileName));
            	indexUnit.setTitle(model.getTitle());
            	indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(model.getKeywords().split(" "))));
            	indexUnit.setAuthor(model.getAuthor());
            	Indexer.getInstance().add(indexUnit.getLuceneDocument());
            }
    	}
    }
    
	
    
    
	
	
}
