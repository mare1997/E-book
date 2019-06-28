package com.myproject.e_book.ebook.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.e_book.ebook.lucene.model.AdvancedQuery;
import com.myproject.e_book.ebook.lucene.model.RequiredHighlight;
import com.myproject.e_book.ebook.lucene.model.ResultData;
import com.myproject.e_book.ebook.lucene.model.SearchType;
import com.myproject.e_book.ebook.lucene.model.SimpleQuery;
import com.myproject.e_book.ebook.lucene.search.QueryBuilder;
import com.myproject.e_book.ebook.lucene.search.ResultRetriever;



@RestController
@RequestMapping(value = "ebook/search")
public class SearchController {

	@PostMapping(value="/term", consumes="application/json")
	public ResponseEntity<List<ResultData>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {		
		Query query= QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	@PostMapping(value="/fuzzy", consumes="application/json")
	public ResponseEntity<List<ResultData>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
		Query query= QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	@PostMapping(value="/phrase", consumes="application/json")
	public ResponseEntity<List<ResultData>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
		Query query= QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	@PostMapping(value="/boolean", consumes="application/json")
	public ResponseEntity<List<ResultData>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
		Query query1 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
		Query query2 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());
		
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
			builder.add(query1,BooleanClause.Occur.MUST);
			builder.add(query2,BooleanClause.Occur.MUST);
		}else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
			builder.add(query1,BooleanClause.Occur.SHOULD);
			builder.add(query2,BooleanClause.Occur.SHOULD);
		}else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
			builder.add(query1,BooleanClause.Occur.MUST);
			builder.add(query2,BooleanClause.Occur.MUST_NOT);
		}
		
		Query query = builder.build();
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
		rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
}
