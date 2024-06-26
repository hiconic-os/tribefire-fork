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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class FakeServletOutputStream extends ServletOutputStream {

	protected ByteArrayOutputStream baos = new ByteArrayOutputStream();
	protected boolean isClosed = false;
	
	public byte[] getData() {
		return baos.toByteArray();
	}
	
	public boolean isClosed() {
		return this.isClosed;
	}
	
	@Override
	public void write(int b) throws IOException {
		baos.write(b);
	}
	
	@Override
	public void write(byte b[], int off, int len) throws IOException {
		baos.write(b, off, len);
	}

	@Override
	public void write(byte b[]) throws IOException {
		baos.write(b);
	}

	@Override
	public void flush() throws IOException {
		baos.flush();
	}

	@Override
	public void close() throws IOException {
		this.isClosed = true;
		baos.close();
	}
}
