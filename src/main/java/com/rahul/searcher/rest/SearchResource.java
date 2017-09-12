package com.rahul.searcher.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.searcher.service.SearchService;

@RestController
public class SearchResource {
	@Autowired
	private SearchService searchService;

}
