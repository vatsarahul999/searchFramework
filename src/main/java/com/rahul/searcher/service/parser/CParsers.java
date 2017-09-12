package com.rahul.searcher.service.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;

import com.rahul.searcher.service.dto.SearchDTO;
import com.rahul.searcher.service.parser.CParser.FunctionSpecifierContext;

import antlr.ParseTree;

public class CParsers extends CBaseListener{

	
	public CParsers(File file) {
		functionNames = new ArrayList<>();
		this.file = file;
	}

	private File file;
	
	private List<String> functionNames;
	
	public void exitFunctionSpecifier(FunctionSpecifierContext ctx) {
		functionNames.add(ctx.toString());
		
	}

	public SearchDTO call() throws Exception {
		SearchDTO resultDTO = new SearchDTO();
		InputStream is = new FileInputStream(file);
		CLexer lexer = new CLexer(new ANTLRInputStream(is));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CParser parser = new CParser(tokens); 
		try{
			parser.setBuildParseTree(true); // tell ANTLR to build a parse tree
			try{
				ParseTree tree = parser.startParsing();
				ParseTreeWalker walker = new ParseTreeWalker();
		        walker.walk( tree);
		}
		return null;
	}
	

}
