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
import java.io.Writer;

/**
 * Simple Writer that delegates all invocations to the delegate Writer but also counts the number of characters written.
 */
public class CountingWriter extends Writer {

	private Writer delegate;
	private int count;

	public CountingWriter(Writer delegate) {
		super();
		this.delegate = delegate;
	}

	/**
	 * Returns the number of characters written since the initialization or the last call to {@link #resetCount()}.
	 *
	 * @return The number of characters.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Resets the internal counter to 0.
	 */
	public void resetCount() {
		count = 0;
	}

	@Override
	public void write(int c) throws IOException {
		count++;
		delegate.write(c);
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		count += cbuf.length;
		delegate.write(cbuf);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		count += len;
		delegate.write(cbuf, off, len);
	}

	@Override
	public void write(String str) throws IOException {
		count += str.length();
		delegate.write(str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		count += len;
		delegate.write(str, off, len);
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		count += csq.length();
		return delegate.append(csq);
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		count += end - start;
		return delegate.append(csq, start, end);
	}

	@Override
	public Writer append(char c) throws IOException {
		count++;
		return delegate.append(c);
	}

	@Override
	public void flush() throws IOException {
		delegate.flush();
	}

	@Override
	public void close() throws IOException {
		delegate.close();
	}
}
