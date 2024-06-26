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
package jsinterop.context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author peter.gazdik
 */
public class JsKeywords {

	/** Actually JS reserved words, but keywords is shorter and cooler */
	public static List<String> jsKeywords = Arrays.asList("arguments", "await", "break", "case", "catch", "class", "const", "continue", "debugger",
			"default", "delete", "do", "else", "enum", "eval", "export", "extends", "false", "finally", "for", "function", "if", "implements",
			"import", "in", "instanceof", "interface", "let", "new", "null", "package", "private", "protected", "prototype", "public", "return",
			"static", "super", "switch", "this", "throw", "true", "try", "typeof", "var", "void", "while", "with", "yield");

	/** Converts java class Name to a valid JS class name within a namespace. Escapes JS reserved words in every part of the package if needed. */
	public static String classNameToJs(String className) {
		int i = className.lastIndexOf('.');
		String packageName = className.substring(0, i);
		String simpleName = className.substring(i + 1);

		return packageToJsNamespace(packageName) + "." + simpleName;
	}

	/** Converts java package name to valid JS namespace name. Escapes JS reserved words in every part of the package if needed. */
	public static String packageToJsNamespace(String p) {
		return Stream.of(p.split("\\.")) //
				.map(JsKeywords::javaIdentifierToJs) //
				.collect(Collectors.joining("."));
	}

	public static String javaIdentifierToJs(String s) {
		String nameBase = removeTrailingUnderscores(s);

		return jsKeywords.contains(nameBase) ? s + "_" : s;
	}

	private static String removeTrailingUnderscores(String s) {
		while (s.endsWith("_"))
			s = s.substring(0, s.length() - 1);

		return s;
	}

}
