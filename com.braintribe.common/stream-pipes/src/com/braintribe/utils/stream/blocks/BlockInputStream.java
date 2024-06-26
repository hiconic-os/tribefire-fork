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
package com.braintribe.utils.stream.blocks;

import java.io.IOException;
import java.io.InputStream;

class BlockInputStream extends InputStream {
	private final Block block;
	private final InputStream delegate;
	private long pos;
	
	public BlockInputStream(Block block) {
		this.block = block;
		delegate = block.openRawInputStream();
	}
	
	@Override
	public int read() throws IOException {
		if (available() <= 0) {
			return -1;
		}
		
		pos ++;
		return delegate.read();
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (len < 0) {
			// TODO: Remove this then redundant check after bug was found (CORETS-753)
			throw new IllegalArgumentException("Attempt to read a negative amount of bytes: len=" + len + ", off=" + off + ", available=" + available());
		}
		
		if (available() == 0)
			return -1;
		
		len = Math.min(len, available());
		int readBytes = delegate.read(b, off, len);
		
		pos += readBytes;
		
		return readBytes;
	}
	
	@Override
	public int available() throws IOException {
		if (block.getBytesWritten() < pos) {
			// TODO: Remove this then redundant check after bug was found (CORETS-753)
			throw new IllegalStateException("Internal state corrupted: Pos > written bytes: pos=" + pos + ", bytes written=" + block.getBytesWritten());
		}
		return (int) Math.min(Integer.MAX_VALUE, block.getBytesWritten() - pos);
	}
	
	@Override
	public void close() throws IOException {
		delegate.close();
	}
	
}