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
package com.braintribe.devrock.artifactcontainer.quickImport.ui;


/**
 * helper class to expand the input into search patterns
 * 
 * @author pit
 *
 */
public class CamelCasePatternExpander {

	private static final String WILDCARD = ".*";
	public CamelCasePatternExpander() {
		
	}
	
	/**
	 * checks whether a string has a leading "'" and a trailing "'", in this case, it returns the string without the "'". If not, it returns null 
	 * @param input - a string in either with leading and trailing single quotes  
	 * @return - null if the input doesn't have a leading and trailing single quote or the input string without single quotes 
	 */
	public boolean isPrecise( String input) {
		if (
				input.startsWith( "'") &&
				input.endsWith( "'")
		    ) {
			return true;
		} 		
		return false;
	}
	
	public String sanitize( String input) {
		if (input.contains( "'") == false)
			return input;		
		return input.replace("'", "");				
	}
	
	/**
	 * expanding the input into a "likeable" string for the query - handles camel casing etc   
	 * @param input - the string as entered by the user 
	 * @return - {@link String} formatted for the query 
	 */
	public String expand( String input) {
		if (input.length() == 0)
			return WILDCARD;
		String pattern = input;
		if (isAllLowerCase(input) && hasNoHyphen(input)) {
			if (input.contains( "*") == false) {
				pattern = "*" + input +"*";			
			}
		} else {
			StringBuilder builder = new StringBuilder();
			boolean prefixAsterics = input.startsWith("*") ? true : false;
			boolean upperCase=false;
			for (int i = 0; i < input.length(); i++) {
				String c = input.substring(i, i+1);
				
				if (c.matches( "-")) {
					if (builder.length() > 0)
						builder.append( WILDCARD);
					builder.append( c);
					upperCase = false;
					continue;
				}
				
				if (c.matches( "[A-Z]")) {
					upperCase = true;
					if (builder.length() > 0)
						builder.append(WILDCARD);
					if (i == 0) {
						builder.append( c.toLowerCase());
					}					
					else {
						builder.append( "-" + c.toLowerCase());
					}
					continue;
				} 
				if (c.matches( "[a-z0-9-_\\.]")) {
					/*
					if (upperCase) {
						builder.append(WILDCARD + "-");
					}
					*/
					builder.append( c);
					upperCase = false;
				}
			}
			pattern = builder.toString() + WILDCARD;
			if (prefixAsterics)
				pattern = WILDCARD + pattern;
		}
		return pattern;
	}
	
	/**
	 * returns whether there are only lower case characters in the string 
	 * @param suspect - the {@link String} to check 
	 * @return - true if lowercase, false otherwise 
	 */
	private boolean isAllLowerCase( String suspect) {
		if (suspect.matches( "[a-z0-9\\*\\?\\.]*"))
			return true;
		return false;
	}
	
	private boolean hasNoHyphen( String suspect) {
		if (suspect.matches( "^[-]*"))
			return true;
		return false;
	}
	public static void main( String args[]) {
		CamelCasePatternExpander expander = new CamelCasePatternExpander();
		for (String arg : args) {
			System.out.println( arg + "\t\t" + expander.expand(arg));
		}
	}

}
