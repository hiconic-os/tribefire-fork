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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.braintribe.web.multipart.api.FormDataWriter;
import com.braintribe.web.multipart.api.PartHeader;
import com.braintribe.web.multipart.api.PartWriter;

public class MultiplexingFormDataWriter implements FormDataWriter {
	private final FormDataWriter formDataWriter;
	private LogicalPart lastActiveLogicalPart;
	private OutputStream currentPartOut;
	private final List<LogicalPart> logicalParts = new ArrayList<>();

	public MultiplexingFormDataWriter(FormDataWriter formDataWriter) {
		super();
		this.formDataWriter = formDataWriter;
	}

	@Override
	public void close() throws Exception {

		for (LogicalPart logicalPart: logicalParts) {
			logicalPart.outputStream().close();
		}

		currentPartOut.close();
		formDataWriter.close();
	}

	@Override
	public PartWriter openPart(PartHeader header) throws IOException {
		LogicalPart logicalPart = new LogicalPart(header);
		logicalParts.add(logicalPart);
		return logicalPart;
	}

	private void logicalWrite(LogicalPart logicalPart, byte[] b, int off, int len) throws IOException {
		synchronized(this) {
			// continue in block or open new block?
			if (lastActiveLogicalPart != logicalPart) {
				if (lastActiveLogicalPart != null) {
					currentPartOut.close();
				}

				lastActiveLogicalPart = logicalPart;
				PartWriter partWriter = formDataWriter.openPart(logicalPart);
				currentPartOut = partWriter.outputStream();

			}

			currentPartOut.write(b, off, len);
		}
	}

	private class LogicalPart extends DelegatingPartHeader implements PartWriter {
		private final OutputStream out; 
		public LogicalPart(PartHeader partHeader) {
			super(partHeader);
			this.out = new BufferedOutputStream(new LogicalOutputStream(this));
		}

		@Override
		public OutputStream outputStream() {
			return out;
		}
	}

	private class LogicalOutputStream extends OutputStream {

		private final LogicalPart logicalPart;

		public LogicalOutputStream(LogicalPart logicalPart) {
			this.logicalPart = logicalPart;
		}

		@Override
		public void write(int b) throws IOException {
			write(new byte[]{(byte)b}, 0, 1);
		}

		@Override
		public void write(byte[] b) throws IOException {
			write(b, 0, b.length);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			logicalWrite(logicalPart, b, off, len);
		}

		@Override
		public void flush() throws IOException {
		}

		@Override
		public void close() throws IOException {
		}
	}

}