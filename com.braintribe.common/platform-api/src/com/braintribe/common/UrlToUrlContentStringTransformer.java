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
package com.braintribe.common;

import java.io.UncheckedIOException;
import java.net.URL;

import com.braintribe.common.lcd.transformer.Transformer;
import com.braintribe.common.lcd.transformer.TransformerException;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.lcd.Arguments;
import com.braintribe.utils.lcd.Not;

/**
 * Transforms a <code>URL</code> to a <code>String</code> containing the content read from the <code>URL</code>.
 *
 * @author michael.lafite
 */
public class UrlToUrlContentStringTransformer implements Transformer<URL, String, Object> {

	private String encoding;

	public UrlToUrlContentStringTransformer() {
		// nothing to do
	}

	public String getEncoding() {
		return this.encoding;
	}

	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String transform(final URL url, final Object transformationContext) throws TransformerException {
		Arguments.notNull(url, "The passed URL must not be null!");
		try {
			return IOTools.urlToString(Not.Null(url), getEncoding());
		} catch (final UncheckedIOException e) {
			throw new TransformerException("Error while reading from URL " + url + "!", e);
		}
	}
}
