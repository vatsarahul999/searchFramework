package com.rahul.searcher.service.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.rahul.searcher.service.parser.CParser.FunctionSpecifierContext;

public class CParsers extends CBaseListener{


	private List<String> functionNames;
	
	public void exitFunctionSpecifier(FunctionSpecifierContext ctx) {
		functionNames.add(ctx.toString());
	}

	public CParsers(File file) {
		functionNames = new ArrayList<>();
	}
	public List<String> getFunctionNames() {
		return functionNames;
	}

	public void setFunctionNames(List<String> functionNames) {
		this.functionNames = functionNames;
	}

	

}
