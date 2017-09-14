package com.rahul.searcher.service.parser;

import java.util.ArrayList;
import java.util.List;

import com.rahul.searcher.service.parser.CParser.FunctionSpecifierContext;

public class CParsers extends CBaseListener{


	private List<String> functionNames;
	
	public void exitFunctionSpecifier(FunctionSpecifierContext ctx) {
		functionNames.add(ctx.getText());
		System.out.println(ctx.getText());
	}

	public CParsers() {
		functionNames = new ArrayList<>();
	}
	public List<String> getFunctionNames() {
		return functionNames;
	}

	public void setFunctionNames(List<String> functionNames) {
		this.functionNames = functionNames;
	}

	

}
