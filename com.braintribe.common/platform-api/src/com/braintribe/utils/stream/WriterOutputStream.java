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
package com.braintribe.utils.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class WriterOutputStream extends OutputStream {

	protected Writer writer;
	protected String encoding = "UTF-8";
	protected int maxBufferSize = 100000;

	protected ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	protected int bufferSize = 0;

	public WriterOutputStream(Writer writer, String encoding) {
		this.writer = writer;
		if (encoding != null) {
			this.encoding = encoding;
		}
	}

	@Override
	public void write(byte[] b) throws IOException {
		this.buffer.write(b);
		this.bufferSize += b.length;
		this.flushBufferConditionally();
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		this.buffer.write(b, off, len);
		this.bufferSize += len;
		this.flushBufferConditionally();
	}

	@Override
	public void write(int b) throws IOException {
		this.buffer.write(b);
		this.bufferSize++;
		this.flushBufferConditionally();
	}

	@Override
	public void close() throws IOException {
		this.flush();
		this.writer.close();
	}

	@Override
	public void flush() throws IOException {
		this.writer.write(this.buffer.toString(encoding));
		this.writer.flush();
		this.buffer.reset();
		this.bufferSize = 0;
	}

	protected void flushBufferConditionally() throws IOException {
		if (this.bufferSize >= this.maxBufferSize) {
			this.flush();
		}
	}

	public void setMaxBufferSize(int maxBufferSize) {
		this.maxBufferSize = maxBufferSize;
	}

}
