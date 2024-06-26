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

public class BasicDelegateInputStream extends InputStream {
	protected InputStream delegate;

	public static final int EOF = -1;

	public BasicDelegateInputStream(InputStream delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public int read() throws IOException {
		beforeRead(1);
		final int b = delegate.read();
		afterRead(b != EOF ? 1 : EOF);
		return b;
	}

	@Override
	public int read(byte[] b) throws IOException {
		try {
			beforeRead(b != null ? b.length : 0);
			final int n = delegate.read(b);
			afterRead(n);
			return n;
		} catch (final IOException e) {
			handleIOException(e);
			return EOF;
		}
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		try {
			beforeRead(len);
			final int n = delegate.read(b, off, len);
			afterRead(n);
			return n;
		} catch (final IOException e) {
			handleIOException(e);
			return EOF;
		}
	}

	/**
	 * @param n
	 *            number of bytes to be read
	 * @throws IOException
	 *             in case there's an error
	 */
	protected void beforeRead(final int n) throws IOException {
		// no-op
	}

	/**
	 * @param n
	 *            number of bytes read
	 * @throws IOException
	 *             in case there's an error
	 */
	protected void afterRead(final int n) throws IOException {
		// no-op
	}
	protected void handleIOException(final IOException e) throws IOException {
		throw e;
	}

	@Override
	public long skip(long n) throws IOException {
		try {
			return delegate.skip(n);
		} catch (final IOException e) {
			handleIOException(e);
			return 0;
		}
	}

	@Override
	public int available() throws IOException {
		try {
			return delegate.available();
		} catch (final IOException e) {
			handleIOException(e);
			return 0;
		}
	}
	@Override
	public void close() throws IOException {
		if (delegate != null) {
			try {
				delegate.close();
			} catch (final IOException e) {
				handleIOException(e);
			}
		}
	}

	@Override
	@SuppressWarnings("sync-override")
	public void mark(int readlimit) {
		delegate.mark(readlimit);
	}

	@Override
	@SuppressWarnings("sync-override")
	public void reset() throws IOException {
		try {
			delegate.reset();
		} catch (final IOException e) {
			handleIOException(e);
		}
	}

	@Override
	public boolean markSupported() {
		return delegate.markSupported();
	}

}
