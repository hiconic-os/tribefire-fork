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
import java.io.Reader;

import com.braintribe.utils.LimitedStringBuilder;

/**
 * Extension of the Reader class that delegates all calls to a provided Reader object. In addition, it records the data read from the Reader in an
 * internal buffer (which can be limited by size) for later re-use.
 */
public class TeeReader extends Reader {

	private Reader delegate = null;
	private LimitedStringBuilder buffer;

	public TeeReader(Reader delegate) {
		this.delegate = delegate;
		this.buffer = new LimitedStringBuilder(Integer.MAX_VALUE);
	}
	public TeeReader(Reader delegate, int maxSize) {
		this.delegate = delegate;
		this.buffer = new LimitedStringBuilder(maxSize);
	}

	public String getBuffer() {
		return this.buffer.toString();
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public int read() throws IOException {
		int read = delegate.read();
		if (read > 0) {
			this.buffer.append((char) read);
		}
		return read;
	}

	@Override
	public int read(char[] cbuf) throws IOException {
		int read = delegate.read(cbuf);
		if (read > 0) {
			for (int i = 0; i < read; ++i) {
				this.buffer.append(cbuf[i]);
			}
		}
		return read;
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int read = delegate.read(cbuf, off, len);
		if (read > 0) {
			for (int i = off; i < (off + read); ++i) {
				this.buffer.append(cbuf[i]);
			}
		}
		return read;
	}

	@Override
	public long skip(long n) throws IOException {
		return delegate.skip(n);
	}

	@Override
	public boolean ready() throws IOException {
		return delegate.ready();
	}

	@Override
	public boolean markSupported() {
		return delegate.markSupported();
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		delegate.mark(readAheadLimit);
	}

	@Override
	public void reset() throws IOException {
		delegate.reset();
	}

	@Override
	public void close() throws IOException {
		delegate.close();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

}
