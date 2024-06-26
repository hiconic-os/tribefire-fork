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
package tribefire.extension.modelling.processing.tools;

import com.braintribe.utils.StringTools;

public interface ExternalIdComputer {

	static String buildExternalId(String name) {
		String externalId = name;
		if (name != null) {
			externalId = StringTools.removeLeadingWhitespace(name);
			externalId = StringTools.removeTrailingWhitespace(name);
			externalId = StringTools.replaceWhiteSpace(externalId, "-");
			externalId = StringTools.removeDiacritics(externalId);
			externalId = StringTools.removeNonPrintableCharacters(externalId);
			externalId = replaceNonAlphaNumericOrDashCharacters(externalId);
			externalId = removeLeadingOrTrailingDashes(externalId);
			externalId = externalId.toLowerCase();
		}
		return externalId;
	}
	
	static String replaceNonAlphaNumericOrDashCharacters(String name) {
		StringBuilder b = new StringBuilder();
		name.chars()
			.forEach(c -> {
				if (isAlphaNumericCharacter(c) || isDashCharacter(c)) {
					b.append((char)c);
				} else {
					b.append('-');
				}
			});
		return b.toString();
	}
	
	static boolean containsAlphaNumericCharacters(String compare) {
		return compare.chars()
			.filter(c -> isAlphaNumericCharacter(c))
			.findAny()
			.isPresent();
	}

	static boolean isAlphaNumericCharacter(int c) {
		return Character.isLetter(c) || Character.isDigit(c);
	}
	
	static boolean isDashCharacter(int c) {
		return c == '-';
	}
	
	static String removeLeadingOrTrailingDashes(String name) {
		name = name.replaceAll("^\\-+", "");
		name = name.replaceAll("\\-$", "");
		return name;
	}
	
}
