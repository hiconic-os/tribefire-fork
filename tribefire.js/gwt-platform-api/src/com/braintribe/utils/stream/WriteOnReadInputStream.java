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
import java.io.OutputStream;

/**
 * <p>
 * A {@link InputStream} wrapper which writes the read bytes to an optionally given {@link OutputStream}.
 * 
 */
public class WriteOnReadInputStream extends InputStream {

	private InputStream in;
	private OutputStream out;
	private long writeCount = 0;
	boolean closeIn = true;
	boolean closeOut;

	public WriteOnReadInputStream(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
	}

	public WriteOnReadInputStream(InputStream in, OutputStream out, boolean closeInputStreamOnClose, boolean closeOutputStreamOnClose) {
		this.in = in;
		this.out = out;
		this.closeIn = closeInputStreamOnClose;
		this.closeOut = closeOutputStreamOnClose;
	}

	public long getWriteCount() {
		return this.writeCount;
	}

	@Override
	public int read() throws IOException {
		int b = in.read();
		if (out != null && b > -1) {
			out.write(b);
			writeCount++;
		}
		return b;
	}

	@Override
	public int read(byte b[]) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte b[], int off, int len) throws IOException {

		if (len < 1) {
			return 0;
		}

		int r = in.read(b, off, len);

		if (out != null && r > 0) {
			out.write(b, off, r);
			writeCount += r;
		}

		return r;

	}

	@Override
	public long skip(long n) throws IOException {
		throw new IOException("Seek not supported");
	}

	@Override
	public int available() throws IOException {
		return in.available();
	}

	@Override
	public void close() throws IOException {

		IOException error = null;

		if (closeIn) {
			try {
				in.close();
			} catch (IOException e) {
				error = e;
			}
		}

		if (out != null) {

			try {
				out.flush();
			} catch (IOException e) {
				if (error != null) {
					e.addSuppressed(error);
				}
				error = e;
			}

			if (closeOut) {
				try {
					out.close();
				} catch (IOException e) {
					if (error != null) {
						e.addSuppressed(error);
					}
					error = e;
				}
			}
		}

		if (error != null) {
			throw error;
		}

	}

	@Override
	public synchronized void mark(int readlimit) {
		// Ignored.
	}

	@Override
	public synchronized void reset() throws IOException {
		throw new IOException("Mark not supported");
	}

	@Override
	public boolean markSupported() {
		return false;
	}

	public long consume() throws IOException {
		return consume(new byte[8192]);
	}

	public long consume(byte[] b) throws IOException {
		int count;
		long totalCount = 0;
		while ((count = read(b)) != -1) {
			totalCount += count;
		}
		return totalCount;
	}

}
