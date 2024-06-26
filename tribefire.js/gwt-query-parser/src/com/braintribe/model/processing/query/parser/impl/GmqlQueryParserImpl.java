// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.model.processing.query.parser.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.atn.PredictionMode;

import com.braintribe.model.generic.value.Variable;
import com.braintribe.model.processing.query.parser.api.GmqlParsingError;
import com.braintribe.model.processing.query.parser.api.GmqlQueryParser;
import com.braintribe.model.processing.query.parser.api.GmqlQueryParserException;
import com.braintribe.model.processing.query.parser.api.GmqlSemanticParsingError;
import com.braintribe.model.processing.query.parser.api.ParsedQuery;
import com.braintribe.model.processing.query.parser.impl.autogenerated.GmqlLexer;
import com.braintribe.model.processing.query.parser.impl.autogenerated.GmqlParser;
import com.braintribe.model.processing.query.parser.impl.listener.GmqlParserListener;
import com.braintribe.model.processing.query.parser.impl.listener.error.GmqlErrorListener;
import com.braintribe.model.query.Query;
import com.braintribe.model.query.Source;

public class GmqlQueryParserImpl implements GmqlQueryParser {

	@Override
	public ParsedQuery parse(String queryString) {
		// Initialise Antlr
		ANTLRInputStream charStream = new ANTLRInputStream(queryString);
		GmqlLexer lexer = new GmqlLexer(charStream);
		lexer.removeErrorListeners();
		GmqlParser parser = new GmqlParser(new CommonTokenStream(lexer));
		GmqlParserListener customListener = new GmqlParserListener();
		parser.setBuildParseTree(true);
		boolean attemptGetResult = true;
		// add parserListeners
		parser.addParseListener(customListener);
		parser.removeErrorListeners(); // remove ConsoleErrorListener
		GmqlErrorListener customErrorListener = new GmqlErrorListener(lexer);
		parser.addErrorListener(customErrorListener);
		// To detect ambiguity
		parser.addErrorListener(new DiagnosticErrorListener());
		parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);

		GmqlSemanticParsingError semanticError = GmqlSemanticParsingError.T.create();
		try {
			parser.evaluate();
		} catch (Exception e) {
			attemptGetResult = false;
			if (customListener.getCustomParsingExcpetion() != null) {
				semanticError.setMessage(customListener.getCustomParsingExcpetion().getMessage());
			} else {
				semanticError.setMessage("Antlr parsing exception: " + e.getMessage());
				// TODO add logging to log the entire stack trace
			}
		}

		List<GmqlParsingError> errorList = new ArrayList<GmqlParsingError>();
		if (semanticError.getMessage() == null) {
			errorList = customErrorListener.getErrorList();
		} else {
			errorList.add(semanticError);
		}

		// If the query parsed without throwing an exception, but there were
		// some errors caught by the error listener, then one should not
		// continue to get the result
		if (!customErrorListener.getErrorList().isEmpty()) {
			attemptGetResult = false;
		}
		Query query = null;
		
		try {
			if (attemptGetResult) {
				query = (Query) customListener.getResult();
			} else {
				customListener.processRegistry(false);
			}

		} catch (GmqlQueryParserException e) {
			semanticError.setMessage(e.getMessage());
			errorList.add(semanticError);
		}
		Map<String, Source> sourceRegistry = customListener.getSourcesRegistry();
		Map<String, Variable> variablesMap = customListener.getVariablesMap();

		return getParsedQueryInstance(query, errorList, sourceRegistry, variablesMap);
	}

	private static ParsedQuery getParsedQueryInstance(Query query, List<GmqlParsingError> errorList, Map<String, Source> sourceRegistry, Map<String, Variable> variablesMap) {
		ParsedQuery parsedQuery = ParsedQuery.T.create();
		parsedQuery.setQuery(query);
		parsedQuery.setErrorList(errorList);
		parsedQuery.setSourcesRegistry(sourceRegistry);
		parsedQuery.setVariablesMap(variablesMap);
		if (errorList.isEmpty()) {
			parsedQuery.setIsValidQuery(true);
		} else {
			parsedQuery.setIsValidQuery(false);
		}
		return parsedQuery;
	}

	public static List<String> getKeywords() {
		// TODO find a way that utilises Antlr4 to construct this list, rather
		// than relying on hardcoded content
		List<String> result = new ArrayList<String>();
		Collections.addAll(result, keywords);
		return result;
	}

	// Whenever the list of keywords in GmqlLexer is updated, this list has to be synchronized with it.
	final static String[] keywords = { //
			"reference", //
			"typeSignature", //
			"enum", //
			"where", //
			"from", //
			"limit", //
			"offset", //
			"asc", //
			"desc", //
			"distinct", //
			"property", //
			"of", //
			"select", //
			"join", //
			"full", //
			"right", //
			"left", //
			"count", //
			"avg", //
			"min", //
			"max", //
			"sum", //
			"having", //
			"listIndex", //
			"mapKey", //
			"lower", //
			"upper", //
			"toString", //
			"concatenation", //
			"localize", //
			"username", //
			"fullText", //
			"now", //
			"and", //
			"not", //
			"or" //
	};

}
