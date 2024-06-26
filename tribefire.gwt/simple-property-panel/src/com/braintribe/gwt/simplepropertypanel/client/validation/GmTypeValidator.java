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
package com.braintribe.gwt.simplepropertypanel.client.validation;

import java.util.Arrays;
import java.util.Set;

import com.braintribe.gwt.utils.client.FastSet;

public class GmTypeValidator {
	
	private static final Set<String> tfKeywords = new FastSet(Arrays.asList("partition", "globalId", "id"));
	
	private static final Set<String> javaKeywords = new FastSet(Arrays.asList(
		    "abstract",     "assert",        "boolean",      "break",           "byte",
		    "case",         "catch",         "char",         "class",           "const",
		    "continue",     "default",       "do",           "double",          "else",
		    "enum",         "extends",       "false",        "final",           "finally",
		    "float",        "for",           "goto",         "if",              "implements",
		    "import",       "instanceof",    "int",          "interface",       "long",
		    "native",       "new",           "null",         "package",         "private",
		    "protected",    "public",        "return",       "short",           "static",
		    "strictfp",     "super",         "switch",       "synchronized",    "this",
		    "throw",        "throws",        "transient",    "true",            "try",
		    "void",         "volatile",      "while"
		));

//	private static final Pattern JAVA_CLASS_NAME_PART_PATTERN = Pattern.compile("[A-Za-z_$]+[a-zA-Z0-9_$]*");
//	private static final Pattern JAVA_CLASS_ATTRIBUTE_NAME_PART_PATTERN = Pattern.compile("[A-Za-z_$]+[a-zA-Z0-9_$]*");
	
	private static final String JAVA_CLASS_NAME_PART_PATTERN = "[A-Za-z_$]+[a-zA-Z0-9_$]*";
//	private static final RegExp JAVA_CLASS_NAME_PART_PATTERN = RegExp.compile("[A-Za-z_$]+[a-zA-Z0-9_$]*", "gi");
//	private static final RegExp JAVA_CLASS_ATTRIBUTE_NAME_PART_PATTERN = RegExp.compile("[A-Za-z_$]+[a-zA-Z0-9_$]*", "gi");
	private static final String JAVA_CLASS_ATTRIBUTE_NAME_PART_PATTERN = "[A-Za-z_$]+[a-zA-Z0-9_$]*";
	
	public static boolean isValidTypeName(String text) {
	    for (String part : text.split("\\.")) {
	        if (javaKeywords.contains(part) || !text.matches(JAVA_CLASS_NAME_PART_PATTERN)) {
	            return false;
	        }
	    }
	    return text.length() > 0;
	}
	
	public static boolean isValidPropertyName(String text) {
	    for (String part : text.split("\\.")) {
	        if (tfKeywords.contains(part) || javaKeywords.contains(part) || !text.matches(JAVA_CLASS_ATTRIBUTE_NAME_PART_PATTERN)) {
	            return false;
	        }
	    }
	    return text.length() > 0;
	}

}
