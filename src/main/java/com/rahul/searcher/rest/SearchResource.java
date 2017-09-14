package com.rahul.searcher.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.searcher.service.SearchService;
import com.rahul.searcher.service.dto.SearchDTO;
import com.rahul.searcher.service.dto.SearchResultDTO;

@RestController
public class SearchResource {
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/app/search/files/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<SearchResultDTO> search(@PathVariable String query) {
		return searchService.searchFiles(query);
	}
	
	@RequestMapping(value = "/app/search/cFunctions/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<SearchDTO> methodSearch(@PathVariable String query) {
		return searchService.searchMethodsInCCode(query);
	}

}
