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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A {@link Block} that persists in a fixed in-memory buffer of given size.
 * 
 * @author Neidhart.Orlich
 *
 */
public class InMemoryBlock extends Block {
	private byte[] buffer;
	
	public InMemoryBlock(int size) {
		buffer = new byte[size];
	}

	@Override
	public InputStream openRawInputStream() {
		return new ByteArrayInputStream(buffer);
	}

	@Override
	public OutputStream openOutputStream() {
		return new ExistingByteArrayOutputStream(buffer);
	}

	@Override
	public int getTreshold() {
		return buffer.length;
	}
	
	@Override
	public void destroy() {
		buffer = null; 
	}
	
	@Override
	public void autoBufferInputStreams(int bufferSize) {
		// Ignore - no additional buffering needed.
	}
	
	public byte[] getBuffer() {
		return buffer;
	}

	@Override
	public boolean isAutoBuffered() {
		return false;
	}

	@Override
	public long getBytesAllocated() {
		return buffer.length;
	}
}