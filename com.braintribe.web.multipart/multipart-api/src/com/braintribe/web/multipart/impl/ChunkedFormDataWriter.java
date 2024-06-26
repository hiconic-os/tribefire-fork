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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import com.braintribe.exception.Exceptions;
import com.braintribe.utils.ArrayTools;
import com.braintribe.utils.IOTools;
import com.braintribe.web.multipart.api.PartHeader;
import com.braintribe.web.multipart.api.PartHeaders;
import com.braintribe.web.multipart.api.PartWriter;
import com.braintribe.web.multipart.streams.BasicDelegateOutputStream;
import com.braintribe.web.multipart.streams.ChunkedOutputStream;

public class ChunkedFormDataWriter extends AbstractFormDataWriter {

	private static final byte[] DOUBLE_DASH = "--".getBytes();
	
	public ChunkedFormDataWriter(OutputStream outputStream) {
		super(outputStream);
	}
	
	// TODO: Integrate a close handler in the class structure
	// TODO: Check for open parts before closing
	@Override
	public void close() throws Exception {
		out.write("--a0--\n".getBytes());
		out.flush();
	}
	
	public void writeProprietaryPartHeader(PartHeader header) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(header.getName());
		
		MutablePartHeaderImpl headerCopy = new MutablePartHeaderImpl(header);
		headerCopy.removeContentDispositionParameter("name");
		
		if (headerCopy.getFormDataContentDispositionParameterNames().isEmpty()) {
			headerCopy.setHeader(PartHeaders.CONTENT_DISPOSITION, null);
		}
		
		String otherHeaders = headerCopy.getHeaders()
			.flatMap(e -> e.getValue().stream().map(value -> toQueryParameter(e.getKey(), value)))
			.collect(Collectors.joining("&"));
		
		if (!otherHeaders.isEmpty()) {
			stringBuilder.append("?");
			stringBuilder.append(otherHeaders);
		}
		
		String headerString = stringBuilder.toString();
		
		String chunkSizeAsString = Integer.toHexString(headerString.length()).toUpperCase();
		byte chunkSizeSizeChar = (byte) (chunkSizeAsString.length() - 1 + (byte)'a');
		
		byte[] chunk = (byte[]) ArrayTools.merge(
				DOUBLE_DASH,
				new byte[] {chunkSizeSizeChar}, 
				chunkSizeAsString.getBytes(),
				DOUBLE_DASH,
				headerString.getBytes()
			);
		
		out.write(chunk);
	}

	private String toQueryParameter(String key, String value) {
		try {
			return key + "=" + URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e, "UTF-8 encoding not supported");
		}
	}
	
	@Override
	public PartWriter openPartImpl(PartHeader header) throws IOException {
		writeProprietaryPartHeader(header);

		if (header.getContentLength() == null) {
			return new ChunkedPartWriter(header);
		} else {
			return new DirectPartWriter(header);
		}
	}

	private class ChunkedPartWriter extends DelegatingPartHeader implements PartWriter {

		public ChunkedPartWriter(PartHeader delegate) {
			super(delegate);
		}

		@Override
		public OutputStream outputStream() {
			ChunkedOutputStream cos = ChunkedOutputStream.instance(out, IOTools.SIZE_64K, true);
			cos.setCloseHandler(internalOutputStream -> {
				freeCurrentPartWriter();
				try {
					internalOutputStream.write(LF);
				} catch (IOException e) {
					throw Exceptions.unchecked(e, "Could not write trailing linefeed after part end");
				}
			});

			return cos;
		}
	}
	
	protected class DirectPartWriter extends DelegatingPartHeader implements PartWriter {

		public DirectPartWriter(PartHeader delegate) {
			super(delegate);
			try {
				out.write(LF);
			} catch (IOException e) {
				throw Exceptions.unchecked(e, "Could not write initial linefeed to content length aware part");
			}
		}

		@Override
		public OutputStream outputStream() {
			return new BasicDelegateOutputStream(out) {
				private boolean closed;

				@Override
				public void close() throws IOException {
					if (!closed) {
						out.flush();
						freeCurrentPartWriter();
						out.write(LF);
						closed = true;
					}
				}
			};
		}

	}

	
}
