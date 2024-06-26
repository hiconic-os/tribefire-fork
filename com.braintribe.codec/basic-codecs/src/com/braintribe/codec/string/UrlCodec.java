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
/**
 * 
 */
package com.braintribe.codec.string;

import java.net.MalformedURLException;
import java.net.URL;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

public class UrlCodec implements Codec<URL, String> {
	private URL base;

	public UrlCodec(URL base) {
		if (base==null) throw new NullPointerException("base url must not be null");
		this.base = base;
	}
	
	@Override
	public URL decode(String strValue) throws CodecException {
		if (strValue == null || strValue.trim().length() == 0)
			return null;

		try {
			return new URL(base, strValue);
		} catch (MalformedURLException e) {
			throw new CodecException(e);
		}
	}
	
	@Override
	public String encode(URL obj) throws CodecException {
		if (obj==null) return null;
		
		return obj.toExternalForm();
	}
	
	@Override
	public Class<URL> getValueClass() {
	    return URL.class;
	}
}
