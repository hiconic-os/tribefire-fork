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
package com.braintribe.mimetype;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Parses a qualified mime type into a {@link ParsedMimeType}.
 * 
 * @see ParsedMimeType
 *
 */
public class MimeTypeParser {

	public static ParsedMimeType getParsedMimeType(String qualifiedMimeType) {
		StringTokenizer tokens = new StringTokenizer(qualifiedMimeType, ";,");
		Map<String, String> params = new HashMap<String, String>();

		String mimeType = null;
		String mediaType = null;
		String subType = null;

		boolean first = true;
		
		while(tokens.hasMoreElements()) {
			String token = tokens.nextToken();
			if (first) {
				first = false;
				mimeType = token.trim();

				int index = token.indexOf('/');
				mediaType = index != -1 ? token.substring(0, index) : token;
				subType = index != -1 ? token.substring(index + 1) : "";

			} else {
				token = token.trim();
				int index = token.indexOf('=');
				if (index != -1) {
					String key = token.substring(0, index);
					String value = token.substring(index + 1);
					
					params.put(key, value);
				}
			}
		}

		return new ParsedMimeType(mimeType, mediaType, subType, params);
	}

}
