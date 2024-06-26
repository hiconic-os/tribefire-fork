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
package com.braintribe.web.multipart.streams;

import java.io.IOException;
import java.io.OutputStream;

import com.braintribe.utils.IOTools;

public class InMemoryChunkedOutputStream extends ChunkedOutputStream {
	private byte[] chunkBuffer;
	
	public InMemoryChunkedOutputStream(OutputStream delegate, boolean proprietaryMode) {
		this(delegate, IOTools.SIZE_8K, proprietaryMode);
	}

	public InMemoryChunkedOutputStream(OutputStream delegate, int chunkSize, boolean proprietaryMode) {
		super(delegate, chunkSize, proprietaryMode);

		chunkBuffer = new byte[chunkSize];
	}

	@Override
	protected void addBytesToCurrentChunk(byte[] b, int off, int numOfBytesToWriteInTotal) {
		System.arraycopy(b, off, chunkBuffer, currentChunkSize, numOfBytesToWriteInTotal);
	}
	

	@Override
	protected void writeOutCurrentChunkContent() throws IOException {
		delegate.write(chunkBuffer, 0, currentChunkSize);
	}
}
