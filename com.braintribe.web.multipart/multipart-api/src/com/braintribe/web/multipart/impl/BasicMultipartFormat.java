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

import java.util.HashMap;
import java.util.Map;

import com.braintribe.web.multipart.api.MultipartFormat;

public class BasicMultipartFormat implements MultipartFormat {
	private Map<String, String> parameters;
	private String mimeType;
	private MultipartSubFormat subFormat;
	
	public BasicMultipartFormat(String mimeType, MultipartSubFormat subFormat, Map<String, String> parameters) {
		this.mimeType = mimeType;
		this.subFormat = subFormat;
		this.parameters = parameters;
	}
	
	public BasicMultipartFormat(String mimeType, MultipartSubFormat subFormat) {
		this(mimeType, subFormat, new HashMap<>());
	}
	
	public BasicMultipartFormat setParameter(String name, String value) {
		this.parameters.put(name, value);
		return this;
	}

	@Override
	public MultipartSubFormat getSubFormat() {
		return subFormat;
	}
	
	@Override
	public String getMimeType() {
		return mimeType;
	}
	
	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	@Override
	public boolean matches(MultipartFormat format) {
		if (!mimeType.equals(format.getMimeType()))
			return false;
		
		for (Map.Entry<String, String> entry: parameters.entrySet()) {
			if (!format.getParameter(entry.getKey()).equals(entry.getValue()))
				return false;
		}
		
		return true;
	}
}
