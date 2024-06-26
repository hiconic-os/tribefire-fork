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
package com.braintribe.util.servlet.stream;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * This is a wrapper around the OutputStream provided by a ServletReponse (and a HttpServletReponse)
 * which waits to open the output stream until the first byte is actually about to be written.
 * This prevents the output stream to be opened too early, thus preventing the ExceptionFilter
 * to send an exception to the client.
 */
public class OnDemandOpeningOutputStream extends ServletOutputStream {

	private ServletResponse response;
	private ServletOutputStream delegate;

	public OnDemandOpeningOutputStream(ServletResponse response) {
		this.response = response;
	}
	
	// OutputStream methods
	
	@Override
	public void write(int b) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.write(b);
	}
	
	@Override
	public void write(byte b[], int off, int len) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.write(b, off, len);
	}

	@Override
	public void write(byte b[]) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.write(b);
	}

	@Override
	public void flush() throws IOException {
		// There could be a valid argument that we should not try to open the stream when we're simply trying to flush it
		// but we don't want to change the behaviour of the underlying output stream. Hence, we open the stream
		// anyway.
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.flush();
	}

	@Override
	public void close() throws IOException {
		// There could be a valid argument that we should not try to open the stream when we're simply trying to close it
		// but we don't want to change the behaviour of the underlying output stream. Hence, we open the stream
		// anyway.
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.close();
	}

	// ServletOutputStream methods
	
	@Override
	public void print(boolean arg0) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.print(arg0);
	}

	@Override
	public void print(char c) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.print(c);
	}

	@Override
	public void print(double d) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.print(d);
	}

	@Override
	public void print(float f) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.print(f);
	}

	@Override
	public void print(int i) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.print(i);
	}

	@Override
	public void print(long l) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.print(l);
	}

	@Override
	public void print(String arg0) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.print(arg0);
	}

	@Override
	public void println() throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.println();
	}

	@Override
	public void println(boolean b) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.println(b);
	}

	@Override
	public void println(char c) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.println(c);
	}

	@Override
	public void println(double d) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.println(d);
	}

	@Override
	public void println(float f) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.println(f);
	}

	@Override
	public void println(int i) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.println(i);
	}

	@Override
	public void println(long l) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.println(l);
	}

	@Override
	public void println(String s) throws IOException {
		if (delegate == null) {
			delegate = response.getOutputStream();
		}
		delegate.println(s);
	}
}
