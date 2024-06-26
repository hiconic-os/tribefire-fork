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

import java.io.File;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.function.Supplier;

import com.braintribe.common.lcd.UnreachableCodeException;
import com.braintribe.exception.Exceptions;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.lcd.CommonTools;
import com.braintribe.utils.lcd.Not;

/**
 * Simple provider class that provides a <code>String</code> by reading it from the configured {@link #getUrl() url}.
 *
 * @author michael.lafite
 */
public class SimpleUrlReader implements Supplier<String> {

	private URL url;

	private String encoding;

	public SimpleUrlReader(final URL url) {
		init(url, null);
	}

	public SimpleUrlReader(final URL url, final String encoding) {
		init(url, encoding);
	}

	public SimpleUrlReader(final String urlString) {
		init(urlString, null);
	}

	public SimpleUrlReader(final String urlString, final String encoding) {
		init(urlString, encoding);
	}

	public SimpleUrlReader(final File file) {
		init(file, null);
	}

	public SimpleUrlReader(final File file, final String encoding) {
		init(file, encoding);
	}

	private void init(final Object objectUsedToCreateURL, final String encoding) {
		this.encoding = encoding;

		if (objectUsedToCreateURL instanceof URL) {
			this.url = (URL) objectUsedToCreateURL;
		} else if (objectUsedToCreateURL instanceof String) {
			this.url = IOTools.newUrl((String) objectUsedToCreateURL);
		} else if (objectUsedToCreateURL instanceof File) {
			this.url = FileTools.toURL((File) objectUsedToCreateURL);
		} else {
			throw new UnreachableCodeException(CommonTools.getParametersString("objectUsedToCreateURL", objectUsedToCreateURL));
		}
	}

	public String getEncoding() {
		return this.encoding;
	}

	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	public URL getUrl() {
		return Not.Null(this.url);
	}

	public void setUrl(final URL url) {
		this.url = url;
	}

	@Override
	public String get() throws RuntimeException {
		try {
			return IOTools.urlToString(getUrl(), getEncoding());
		} catch (final UncheckedIOException e) {
			throw Exceptions.unchecked(e,  "Error while reading from URL " + getUrl() + "!");
		}
	}

}
