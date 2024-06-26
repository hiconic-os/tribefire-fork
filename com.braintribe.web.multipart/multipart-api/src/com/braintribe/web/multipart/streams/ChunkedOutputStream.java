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
import java.util.function.Consumer;

import com.braintribe.utils.ArrayTools;
import com.braintribe.web.multipart.impl.FormDataMultipartConstants;

public abstract class ChunkedOutputStream extends OutputStream implements FormDataMultipartConstants {

	protected final OutputStream delegate;
	protected final int chunkTotalSize;
	protected int currentChunkSize;
	private Consumer<OutputStream> closeHandler;
	private final boolean proprietaryMode;
	private final byte[] linebreak;
	
	static final protected int MAX_CHUNK_SIZE_IN_MEMORY = 0x10000;

	public ChunkedOutputStream(OutputStream delegate, int chunkSize, boolean proprietaryMode) {
		super();

		if (chunkSize < 0) {
			throw new IllegalArgumentException("Please provide a positive chunk size. You provided: " + chunkSize);
		}

		this.delegate = delegate;
		this.chunkTotalSize = chunkSize;
		this.proprietaryMode = proprietaryMode;
		
		if (proprietaryMode) {
			linebreak = new byte[]{LF};
		} else {
			linebreak = HTTP_LINEBREAK;
		}
	}

	private boolean canBeNormalized(int number) {
		return number >= Dechunker.NORMALIZED_CHUNK_SIZE_BASE
				&& number < Dechunker.NORMALIZED_CHUNK_SIZE_BASE*15
				&& ((number & (number - 1)) == 0);
	}
	
	private String getNormalizedSize(int number) {
		int shiftedNumber = number;
		for (short i=0; i<0xF; i++) {
			if (shiftedNumber == Dechunker.NORMALIZED_CHUNK_SIZE_BASE) {
				return Integer.toHexString(i);
			}
			
			shiftedNumber >>= 1;
		}
		
		throw new IllegalArgumentException("Argument '" + number + "' can't be encoded in a normalized size declaration");
	}
	
	protected void writeOutCurrentChunk() throws IOException {
		writeOutCurrentChunk(false);
	}
	
	protected void writeOutCurrentChunk(boolean lastChunk) throws IOException {
		if (currentChunkSize == 0) {
			throw new RuntimeException("Cannot write out current chunk as there is nothing to be written");
		}

		byte[] chunkSizeAsByteArr;
		String chunkSizeAsString = Integer.toHexString(currentChunkSize).toUpperCase();
		
		if (proprietaryMode) {
			if (lastChunk) {
				chunkSizeAsString = (char)Dechunker.LAST_CHUNK_ANNOUNCEMENT + chunkSizeAsString;
			}
			if (!lastChunk && canBeNormalized(currentChunkSize)) {
				String hexString =  getNormalizedSize(currentChunkSize);
				byte normalizedChunkSizeByte = hexString.getBytes()[0];
				chunkSizeAsByteArr = new byte[] {Dechunker.NORMALIZED_CHUNK_SIZE_INTRODUCTION, normalizedChunkSizeByte};
			} else {
				byte chunkSizeSizeChar = (byte) (chunkSizeAsString.length() - 1 + (byte)'a');
				chunkSizeAsByteArr = (byte[]) ArrayTools.merge(new byte[] {chunkSizeSizeChar}, chunkSizeAsString.getBytes());
			}
			delegate.write(linebreak);
		} else {
			chunkSizeAsByteArr = chunkSizeAsString.getBytes();
		}

		delegate.write(chunkSizeAsByteArr);

		delegate.write(linebreak);
		writeOutCurrentChunkContent();

		if (!proprietaryMode) {
			delegate.write(linebreak);
		}
		
		currentChunkSize = 0;
	}

	protected abstract void writeOutCurrentChunkContent() throws IOException;

	protected abstract void addBytesToCurrentChunk(byte[] b, int off, int numOfBytesToWriteInTotal);

	@Override
	public void write(int b) throws IOException {
		write(new byte[] { (byte) b });

		// if (chunk.remaining() == 0)
		// writeOutCurrentChunk();
		//
		// chunk.put((byte) b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public void write(byte[] b, int off, int numOfBytesToWriteInTotal) throws IOException {
		final int numBytesLeftInCurrentChunk = chunkTotalSize - currentChunkSize;

		if (numOfBytesToWriteInTotal > numBytesLeftInCurrentChunk) {
			write(b, off, numBytesLeftInCurrentChunk);
			writeOutCurrentChunk();
			write(b, off + numBytesLeftInCurrentChunk, numOfBytesToWriteInTotal - numBytesLeftInCurrentChunk);
		} else {
			addBytesToCurrentChunk(b, off, numOfBytesToWriteInTotal);
			currentChunkSize += numOfBytesToWriteInTotal;
		}
	}

	@Override
	public void close() throws IOException {
		if (currentChunkSize > 0) {
			writeOutCurrentChunk(true);
		} else if (proprietaryMode){
			delegate.write("\na0\n".getBytes());
		}
		
		if (!proprietaryMode) {
			delegate.write('0');
			delegate.write(linebreak);
		}
		
		if (closeHandler != null) {
			closeHandler.accept(delegate);
		}
		delegate.flush();
	}

	@Override
	public void flush() throws IOException {
		if (currentChunkSize > 0) {
			writeOutCurrentChunk();
		}

		delegate.flush();
	}

	public void setCloseHandler(Consumer<OutputStream> closeHandler) {
		this.closeHandler = closeHandler;
	}

	public static ChunkedOutputStream instance(OutputStream delegate, int chunkSize, boolean proprietaryMode) {
		if (chunkSize > MAX_CHUNK_SIZE_IN_MEMORY)
			return new BackedUpChunkedOutputStream(delegate, chunkSize, proprietaryMode);
		else
			return new InMemoryChunkedOutputStream(delegate, chunkSize, proprietaryMode);
	}
}