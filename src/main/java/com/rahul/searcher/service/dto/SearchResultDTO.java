package com.rahul.searcher.service.dto;

public class SearchResultDTO {
	
	private String fileName;
	
	private String codeSnippet;
	
	private String path;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCodeSnippet() {
		return codeSnippet;
	}

	public void setCodeSnippet(String codeSnippet) {
		this.codeSnippet = codeSnippet;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "SearchResultDTO [fileName=" + fileName + ", codeSnippet=" + codeSnippet + ", path=" + path + "]";
	}
	
	
}
