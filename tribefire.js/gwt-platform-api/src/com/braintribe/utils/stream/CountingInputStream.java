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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream extends InputStream {

	protected InputStream delegate = null;
	protected long count = 0;
	protected long mark = -1;

	public CountingInputStream(InputStream delegate, boolean wrapBuffer) {
		if (wrapBuffer) {
			this.delegate = new BufferedInputStream(delegate);
		} else {
			this.delegate = delegate;
		}
	}

	public long getCount() {
		return this.count;
	}

	@Override
	public int read() throws IOException {
		int result = this.delegate.read();
		if (result != -1) {
			this.count++;
		}
		return result;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int result = this.delegate.read(b);
		if (result != -1) {
			this.count += result;
		}
		return result;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int result = this.delegate.read(b, off, len);
		if (result != -1) {
			this.count += result;
		}
		return result;
	}

	@Override
	public long skip(long n) throws IOException {
		long result = this.delegate.skip(n);
		this.count += result;
		return result;
	}

	@Override
	public synchronized void mark(int readlimit) {
		this.delegate.mark(readlimit);
		mark = this.count;
	}

	@Override
	public synchronized void reset() throws IOException {
		if (!this.delegate.markSupported()) {
			throw new IOException("Mark not supported");
		}
		if (mark == -1) {
			throw new IOException("Mark not set");
		}

		this.delegate.reset();
		this.count = mark;
	}

	@Override
	public int available() throws IOException {
		return this.delegate.available();
	}

	@Override
	public void close() throws IOException {
		this.delegate.close();
	}

	@Override
	public boolean markSupported() {
		return this.delegate.markSupported();
	}

}
