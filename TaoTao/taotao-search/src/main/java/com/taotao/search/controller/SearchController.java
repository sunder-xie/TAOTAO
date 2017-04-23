package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.search.bean.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "{keyWords}/{page}/{ROWS}", method = RequestMethod.GET)
	public ResponseEntity<SearchResult> queryItemListByKeyWords(@PathVariable(value = "keyWords") String keyWords,
			@PathVariable(value = "page") Integer page, @PathVariable(value = "ROWS") Integer ROWS) {
		try {
			SearchResult searchResult = searchService.queryItemListByKeyWords(keyWords, page, ROWS);
			if (searchResult != null) {
				return new ResponseEntity<SearchResult>(searchResult, HttpStatus.OK);
			} else
				return new ResponseEntity<SearchResult>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SearchResult>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
