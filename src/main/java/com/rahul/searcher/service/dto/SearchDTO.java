package com.rahul.searcher.service.dto;

import java.util.List;

public class SearchDTO {
	
	private String fileName;
	
	private String path;
	
	private List<String> functionNames;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getFunctionNames() {
		return functionNames;
	}

	public void setFunctionNames(List<String> functionNames) {
		this.functionNames = functionNames;
	}

}
