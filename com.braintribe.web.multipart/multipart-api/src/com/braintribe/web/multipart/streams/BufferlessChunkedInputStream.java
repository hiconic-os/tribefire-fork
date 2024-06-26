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

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import com.braintribe.web.multipart.impl.FormDataMultipartConstants;

public class BufferlessChunkedInputStream extends InputStream implements FormDataMultipartConstants {

	private final Dechunker dechunker;
	private final InputStream delegate;

	private int bytesRemainingInChunk;
	private boolean endOfChunkedStreamReached = false;

	private final byte[] oneByteBuffer = new byte[1];

	public BufferlessChunkedInputStream(InputStream delegate) {
		this.delegate = delegate;
		this.dechunker = new Dechunker(delegate, "\n", "\n");
	}

	@Override
	public int read() throws IOException {
		if (endOfChunkedStreamReached || read(oneByteBuffer) == -1) {
			return -1;
		}

		return oneByteBuffer[0] & 0xff;
	}
	
	private void calculateRemainingBytesOfCurrentChunk() throws IOException {
		if (endOfChunkedStreamReached) {
			return;
		}

		if (bytesRemainingInChunk == 0) {
			bytesRemainingInChunk = dechunker.readChunkSize();
		} else if (bytesRemainingInChunk < 0) {
			throw new IndexOutOfBoundsException("Less than 0 bytes remaining in chunk");
		}
		
		if (bytesRemainingInChunk == 0) {
			endOfChunkedStreamReached = true;
		}
		
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		calculateRemainingBytesOfCurrentChunk();
		
		if (bytesRemainingInChunk == 0) {
			return -1;
		}

		int maxBytesToRead = Math.min(bytesRemainingInChunk, len);
		int bytesRead = delegate.read(b, off, maxBytesToRead);

		if (bytesRead == -1) {
			throw new EOFException("Unexpected EOF when reading from delegate stream");
		}

		bytesRemainingInChunk -= bytesRead;

		return bytesRead;
	}

	@Override
	public int available() throws IOException {
		return bytesRemainingInChunk;
	}
	
	@Override
	public void close() throws IOException {
		super.close();

		endOfChunkedStreamReached = true;
	}

}
