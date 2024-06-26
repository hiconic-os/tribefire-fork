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

import java.io.PrintWriter;

/**
 * Simple Writer that delegates all invocations to the delegate Writer but also counts the number of characters written.
 */
public class CountingPrintWriter extends PrintWriter {

	private PrintWriter delegate;
	private int count;

	public CountingPrintWriter(PrintWriter delegate) {
		super(new NullOutputStream());
		this.delegate = delegate;
		this.count = 0;
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
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public void flush() {
		delegate.flush();
	}

	@Override
	public void close() {
		delegate.close();
	}

	@Override
	public boolean checkError() {
		return delegate.checkError();
	}

	@Override
	public void write(int c) {
		count++;
		delegate.write(c);
	}

	@Override
	public void write(char[] buf, int off, int len) {
		count += len;
		delegate.write(buf, off, len);
	}

	@Override
	public void write(char[] buf) {
		count += buf.length;
		delegate.write(buf);
	}

	@Override
	public void write(String s, int off, int len) {
		count += len;
		delegate.write(s, off, len);
	}

	@Override
	public void println() {
		count++;
		delegate.println();
	}

}
