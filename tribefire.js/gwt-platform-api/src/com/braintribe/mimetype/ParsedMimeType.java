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

import java.util.Map;

import com.braintribe.utils.lcd.CommonTools;

/**
 * <p>
 * This class represents a parsed qualified mime type. A qualified mime type is a mime type that may be further
 * specialized, for example:
 * <ul>
 * <li>text/html;spec=module-checks-response</li>
 * <li>text/html;charset=UTF-8</li>
 * <li>text/yaml;abc=def</li>
 * </ul>
 * 
 * <p>
 * This further specialization is optional, but the purpose of this class and its related {@link MimeTypeParser}
 * implementation is to handle exactly such specifications.
 * 
 * @see MimeTypeParser
 * 
 */
public class ParsedMimeType {
	private String mimeType;
	private String mediaType;
	private String subType;
	private Map<String, String> params;

	public ParsedMimeType(String mimeType, String mediaType, String subType, Map<String, String> params) {
		this.mimeType = mimeType;
		this.mediaType = mediaType;
		this.subType = subType;
		this.params = params;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getMediaType() {
		return mediaType;
	}

	public String getSubType() {
		return subType;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean equalsPlain(ParsedMimeType other) {
		if (other == null) {
			return false;
		}
		return CommonTools.equalsOrBothNull(this.mediaType, other.mediaType) && CommonTools.equalsOrBothNull(this.subType, other.subType);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mimeType);

		if (!params.isEmpty()) {
			for (Map.Entry<String, String> p : params.entrySet()) {
				builder.append(';');
				builder.append(p.getKey());
				builder.append('=');
				builder.append(p.getValue());
			}
		}

		return builder.toString();
	}

}
