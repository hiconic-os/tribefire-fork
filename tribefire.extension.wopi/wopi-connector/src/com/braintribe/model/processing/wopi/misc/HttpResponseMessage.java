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
package com.braintribe.model.processing.wopi.misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.EnglishReasonPhraseCatalog;

import com.braintribe.logging.Logger;
import com.braintribe.utils.IOTools;

/**
 * 
 * Streaming of document (resource) itself
 */
public class HttpResponseMessage implements HttpResponse {

	private static final Logger logger = Logger.getLogger(HttpResponseMessage.class);

	private final Map<String, String> headers;
	private final int sc;

	private String ct;
	private InputStream cs;
	private Integer cl;

	@Override
	public final void close() throws Exception {
		if (cs != null)
			cs.close();
	}

	@Override
	public void write(HttpServletResponse resp) throws IOException {
		int status = getStatus();
		String reason = EnglishReasonPhraseCatalog.INSTANCE.getReason(status, Locale.ENGLISH);
		logger.debug(() -> String.format("response: status: '%d' reason: '%s'", status, reason));
		resp.setStatus(getStatus());
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			resp.setHeader(entry.getKey(), entry.getValue());
		}
		resp.setContentType(getContentType());
		resp.setContentLength(getContentLength());
		try (InputStream in = getContentStream(); OutputStream out = resp.getOutputStream()) {
			if (in != null && out != null)
				IOTools.inputToOutput(in, out);
		}
	}

	public HttpResponseMessage(int statusCode) {
		this.headers = new HashMap<String, String>();
		this.sc = statusCode;
	}

	public int getStatus() {
		return sc;
	}

	public void setContentType(String contentType) {
		this.ct = contentType;
	}

	public String getContentType() {
		return ct;
	}

	public void setContentStream(InputStream contentStream) {
		this.cs = contentStream;
	}

	public InputStream getContentStream() {
		return cs;
	}

	public void setContentLength(int contentLength) {
		this.cl = contentLength;
	}

	public int getContentLength() throws IOException {
		return cl != null ? cl : cs == null ? 0 : cs.available();
	}

	public String addHeader(String name, String value) {
		return value == null ? headers.remove(name) : headers.put(name, value);
	}

}
