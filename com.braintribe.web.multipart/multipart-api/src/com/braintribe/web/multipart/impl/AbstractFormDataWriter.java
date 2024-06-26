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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import com.braintribe.web.multipart.api.FormDataWriter;
import com.braintribe.web.multipart.api.PartHeader;
import com.braintribe.web.multipart.api.PartWriter;

public abstract class AbstractFormDataWriter implements FormDataWriter, FormDataMultipartConstants {
	protected final OutputStream out;
	private PartWriter currentPartWriter;


	protected AbstractFormDataWriter(OutputStream outputStream) {
		this.out = outputStream;
		
	}
	
	@Override
	public PartWriter openPart(PartHeader header) throws IOException {
		if (currentPartWriter == null) {
			currentPartWriter = openPartImpl(header);
			return currentPartWriter;
		} else {
			throw new IllegalStateException("Cannot open another part as long as the previous part is still open");
		}
	}

	protected abstract PartWriter openPartImpl(PartHeader header) throws IOException;

	@Override
	public void close() throws Exception {
		if (currentPartWriter != null) {
			throw new IllegalStateException("Can't close FormDataWriter because a part is still open: " + currentPartWriter.getFormDataContentDisposition());
		}
		
		out.write(MULTIPART_HYPHENS);
		out.write(HTTP_LINEBREAK);
		out.flush();
	}

	protected void writeHeader(PartHeader partHeader) throws IOException {
		out.write(HTTP_LINEBREAK);
		for (String headerName : partHeader.getHeaderNames()) {
			Collection<String> headerValues = partHeader.getHeaders(headerName);
			for (String headerValue : headerValues) {
				writeHeaderLine(headerName, headerValue);
			}
		}
		writeLineBreak();
	}

	protected void writeHeaderLine(String headerName, String headerValue) throws IOException {
		String line = headerName + ": " + headerValue;
		writeLine(line);
	}

	protected void writeLine(String s) throws IOException {
		byte[] bytes = s.getBytes("ISO-8859-1");
		out.write(bytes);
		writeLineBreak();
	}

	protected void writeLineBreak() throws IOException {
		out.write(HTTP_LINEBREAK);
	}

	
	
	protected void freeCurrentPartWriter() {
		currentPartWriter = null;
	}
	
	
	
}
