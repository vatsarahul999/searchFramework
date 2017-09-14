package com.rahul.searcher.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.rahul.searcher.service.dto.SearchDTO;
import com.rahul.searcher.service.parser.CLexer;
import com.rahul.searcher.service.parser.CParser;
import com.rahul.searcher.service.parser.CParsers;

public class CReader implements Callable<SearchDTO> {
	
	private String cFile;
	
	private String cName;
	
	private String path;

	@Override
	public SearchDTO call() throws Exception {
		SearchDTO searchDTO = new SearchDTO();
		InputStream is = null;
		if(cFile==null)
			throw new Exception("The contents have not been set.");
        ANTLRInputStream inputStream = new ANTLRInputStream(cFile);
        CLexer markupLexer = new CLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(markupLexer);
        CParser markupParser = new CParser(commonTokenStream);
        markupParser.setBuildParseTree(true);
        ParseTreeWalker walker = new ParseTreeWalker();
        CParsers reader = new CParsers();
        walker.walk(reader, markupParser.functionDefinition());
        searchDTO.setFileName(cName);
        searchDTO.setFunctionNames(reader.getFunctionNames());
        searchDTO.setPath(path);
		return searchDTO;
	}

	public String getcFile() {
		return cFile;
	}

	public void setcFile(String cFile) {
		this.cFile = cFile;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}
