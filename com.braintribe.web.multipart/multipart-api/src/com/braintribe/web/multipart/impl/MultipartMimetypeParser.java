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
package com.braintribe.web.multipart.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.braintribe.web.multipart.api.MultipartFormat;

public class MultipartMimetypeParser {
	public static MultipartFormat parse(String mimeType) {
		String parts[] = mimeType.split("(,|;)");
		Map<String, String> params = null;
		
		String simpleMimeType = null;
		MultipartSubFormat subFormat = null;
		
		boolean first = true;
		for (String part : parts) {
			if (first) {
				first = false;
				simpleMimeType = part.trim();

				int index = simpleMimeType.indexOf('/');
				String subType = index != -1? simpleMimeType.substring(index + 1): "";
				
				switch (subType) {
					case "form-data":
						subFormat = MultipartSubFormat.formData;
						break;
					case "chunked":
						subFormat = MultipartSubFormat.chunked;
						break;
					default:
						subFormat = MultipartSubFormat.none;
						break;
				}
			}
			else {
				part = part.trim();
				int index = part.indexOf('=');
				if (index != -1) {
					String key = part.substring(0, index);
					String value = part.substring(index + 1);
					
					if (params == null)
						params = new HashMap<String, String>();
					params.put(key, value);
				}
			}
		}
		
		return new BasicMultipartFormat(simpleMimeType, subFormat, params != null? params: Collections.emptyMap());
	}
}
