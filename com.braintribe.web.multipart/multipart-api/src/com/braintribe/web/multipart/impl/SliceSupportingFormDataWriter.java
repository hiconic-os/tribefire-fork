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
import java.util.ArrayList;
import java.util.List;

import com.braintribe.exception.Exceptions;
import com.braintribe.web.multipart.api.MutablePartHeader;
import com.braintribe.web.multipart.api.PartHeader;
import com.braintribe.web.multipart.api.PartHeaders;
import com.braintribe.web.multipart.api.PartWriter;
import com.braintribe.web.multipart.streams.BasicDelegateOutputStream;

public class SliceSupportingFormDataWriter extends FormDataWriterWithBoundary {
	private final PositionAwareOutputStream positionAwareOutputStream;
	private final List<PartAnouncement> partAnouncements = new ArrayList<>();

	private SliceSupportingFormDataWriter(PositionAwareOutputStream outputStream, String boundary) {
		super(outputStream, boundary);
		positionAwareOutputStream = outputStream;
	}
	
	public static SliceSupportingFormDataWriter create(OutputStream outputStream, String boundary) {
		PositionAwareOutputStream positionAwareOutputStream = new PositionAwareOutputStream(outputStream);
		return new SliceSupportingFormDataWriter(positionAwareOutputStream, boundary);
	}
	
	@Override
	public void close() throws Exception {
		writePartAnouncementPart();
		
		super.close();
	}

	private void writePartAnouncementPart() throws IOException {
		MutablePartHeader partHeader = new MutablePartHeaderImpl();
		partHeader.setName(PartHeaders.PART_ANNOUNCEMENT);
		
		PartWriter partWriter = openPart(partHeader);
		OutputStream outputStream = partWriter.outputStream();
		
		long beginningOfPartAnouncement = positionAwareOutputStream.pos;
		
		partAnouncements.forEach(p -> p.writeTo(outputStream));
		
		long partAnouncementLength = positionAwareOutputStream.pos - beginningOfPartAnouncement;
		
		String partAnouncementSizeAsHex = String.format("%08X", partAnouncementLength).toUpperCase();
		
		outputStream.write(partAnouncementSizeAsHex.getBytes("UTF-8"));
		outputStream.close();
	}

	@Override
	protected PartWriter openPartImpl(PartHeader header) throws IOException {
		PartHeader headerWithoutTransferEncoding;
		
		if (header.getTransferEncoding() != null) {
			MutablePartHeader mutablePartHeader = new MutablePartHeaderImpl(header);
			mutablePartHeader.setTransferEncoding(null);
			
			headerWithoutTransferEncoding = mutablePartHeader;
		}else {
			headerWithoutTransferEncoding = header;
		}
		
		writeHeader(headerWithoutTransferEncoding);
		
		return new PositionAwarePartWriter(headerWithoutTransferEncoding);
	}

	private class PositionAwarePartWriter extends DelegatingPartHeader implements PartWriter {
		private final PartAnouncement partAnouncement;
		
		public PositionAwarePartWriter(PartHeader delegate) {
			super(delegate);
			
			partAnouncement = new PartAnouncement();
			partAnouncement.name = delegate.getName();
			partAnouncement.contentType = delegate.getContentType();
		}

		@Override
		public OutputStream outputStream() {
			partAnouncement.startPos = positionAwareOutputStream.pos;
			
			return new BasicDelegateOutputStream(positionAwareOutputStream) {
				private boolean closed;
				
				@Override
				public void close() throws IOException {
					if (!closed) {
						partAnouncement.endPos = positionAwareOutputStream.pos;
						positionAwareOutputStream.flush();
						freeCurrentPartWriter();
						writePartClosingBoundary(positionAwareOutputStream);
						partAnouncements.add(partAnouncement);
						closed = true;
					}
				}
			};
		}

	}
	
	private class PartAnouncement {
		public String name;
		public String contentType;
		public Long startPos, endPos;
		
		@Override
		// TODO: return StringBuilder / StringWriter (as Appendable)
		public String toString() {
			return createPair("name", name) + "&"
					+ createPair("content-type", contentType) + "&"
					+ createPair("start", startPos) + "&"
					+ createPair("end", endPos);
		}
		
		public void writeTo(OutputStream out) {
			try {
				out.write(toString().getBytes("UTF-8"));
				out.write(HTTP_LINEBREAK);
			} catch (IOException e) {
				throw Exceptions.unchecked(e, "Could not write part anouncement to output stream");
			}
		}
		
		private String createPair(String key, Object value) {
			try {
				return key + "=" + URLEncoder.encode(value==null ? "" : value.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw Exceptions.unchecked(e, "Encoding not supported");
			}
		}
	}
	
	private static class PositionAwareOutputStream extends OutputStream {
		private final OutputStream delegate;
		private long pos = 0;

		public PositionAwareOutputStream(OutputStream delegate) {
			this.delegate = delegate;
		}

		@Override
		public void write(int b) throws IOException {
			delegate.write(b);
			pos ++;
		}
		
		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			delegate.write(b, off, len);
			pos += len;
		}
		
		@Override
		public void write(byte[] b) throws IOException {
			delegate.write(b);
			pos += b.length;
		}
		
		@Override
		public void close() throws IOException {
			delegate.close();	
		}
		
		@Override
		public void flush() throws IOException {
			delegate.flush();
		}
		
		
	}
}
