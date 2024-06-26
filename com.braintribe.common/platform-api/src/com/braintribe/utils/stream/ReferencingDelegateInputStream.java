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

/**
 * By simply holding an explicit reference to a resource, you make sure it won't get garbage collected as long as your InputStream is functional.
 *
 * @author Neidhart.Orlich
 *
 */
public class ReferencingDelegateInputStream extends InputStream {

	@SuppressWarnings("unused")
	private Object reference;
	private final InputStream delegate;
	private final boolean dropReferenceOnClose;

	/**
	 * @param reference
	 *            By simply holding an explicit reference to a resource, you make sure it won't get garbage collected as long as your InputStream is
	 *            functional.
	 * @param delegate
	 *            All operations are delegated to this actual {@link InputStream} implementation
	 * @param dropReferenceOnClose
	 *            On <tt>true</tt> the reference is dropped already when {@link #close()} is called, otherwise the reference lives as long as this
	 *            instance.
	 */
	public ReferencingDelegateInputStream(Object reference, InputStream delegate, boolean dropReferenceOnClose) {
		this.reference = reference;
		this.delegate = delegate;
		this.dropReferenceOnClose = dropReferenceOnClose;
	}

	/**
	 * @param reference
	 *            The object will keep being referenced until {@link #close()} is called.
	 * @param delegate
	 *            All operations are delegated to this actual {@link InputStream} implementation
	 */
	public ReferencingDelegateInputStream(Object reference, InputStream delegate) {
		this(reference, delegate, true);
	}

	@Override
	public int read() throws IOException {
		return delegate.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return delegate.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return delegate.read(b, off, len);
	}

	@Override
	public long skip(long n) throws IOException {
		return delegate.skip(n);
	}

	@Override
	public int available() throws IOException {
		return delegate.available();
	}

	@Override
	public void close() throws IOException {
		delegate.close();

		if (dropReferenceOnClose) {
			reference = null;
		}
	}

	@Override
	public void mark(int readlimit) {
		delegate.mark(readlimit);
	}

	@Override
	public void reset() throws IOException {
		delegate.reset();
	}

	@Override
	public boolean markSupported() {
		return delegate.markSupported();
	}

}
