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

import java.io.IOException;
import java.io.InputStream;

import com.braintribe.logging.Logger;
import com.braintribe.utils.lcd.IOTools;

public class ByteStreamBuffer extends InputStream {

	private static final Logger logger = Logger.getLogger(ByteStreamBuffer.class);

	protected MemoryThresholdBuffer buffer = null;
	protected InputStream in = null;

	public ByteStreamBuffer(InputStream outerIn, int bufferSize, boolean closeInputStream) throws IOException {
		buffer = new MemoryThresholdBuffer(bufferSize);
		try {
			IOTools.pump(outerIn, buffer);
			in = new AutoCloseInputStream(buffer.openInputStream(true));
		} finally {
			if (closeInputStream && outerIn != null) {
				com.braintribe.utils.IOTools.closeCloseable(outerIn, logger);
			}
		}
	}

	@Override
	public int hashCode() {
		return in.hashCode();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return in.read(b);
	}

	@Override
	public boolean equals(Object obj) {
		return in.equals(obj);
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return in.read(b, off, len);
	}

	@Override
	public long skip(long n) throws IOException {
		return in.skip(n);
	}

	@Override
	public int available() throws IOException {
		return in.available();
	}

	@Override
	public boolean markSupported() {
		return in.markSupported();
	}

	@Override
	public void mark(int readAheadLimit) {
		in.mark(readAheadLimit);
	}

	@Override
	public void reset() throws IOException {
		in.reset();
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

}
