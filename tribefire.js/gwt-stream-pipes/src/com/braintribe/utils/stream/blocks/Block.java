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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.braintribe.utils.stream.pools.BlockBackedPipe;
import com.braintribe.utils.stream.pools.BlockPool;

/**
 * A sequence of Blocks is used by a {@link BlockBackedPipe} to temporarily and internally buffer/cache streamed data. It
 * abstracts a way of writing to and reading from (a block of) data ({@link #openOutputStream()}, {@link #get()}) as
 * well as freeing eventual resources ({@link #destroy()}).
 * <p>
 * A Block is created and supplied by a {@link BlockPool}. The Block knows how to return itself to that pool during
 * {@link #free()}.
 * 
 * @author Neidhart.Orlich
 *
 */
abstract public class Block implements Supplier<InputStream> {
	private Consumer<Block> giveBack;

	private long bytesWritten = 0;

	public abstract OutputStream openOutputStream();
	protected abstract InputStream openRawInputStream();
	public abstract int getTreshold();
	public abstract void destroy();
	public abstract void autoBufferInputStreams(int bufferSize);
	public abstract boolean isAutoBuffered();

	/**
	 * Returns an {@link InputStream} to the data that is currently stored in this block. Repeatedly calling this method
	 * will return new InputStreams that all start from the beginning and can read concurrently in parallel.
	 */
	@Override
	public InputStream get() {
		return new BlockInputStream(this);
	}

	/**
	 * Returns the block to its {@link BlockPool} and makes previously persisted data unaccessible and ready to be overwritten. 
	 */
	public void free() {
		bytesWritten = 0;
		autoBufferInputStreams(0);
		giveBack.accept(this);
	}

	/**
	 * The return consumer is called by the Block to return itself to its original {@link BlockPool}.
	 */
	public void setReturnConsumer(Consumer<Block> giveBack) {
		this.giveBack = giveBack;
	}

	public void notifyBytesWritten(int numBytes) {
		bytesWritten += numBytes;
	}

	public long getBytesWritten() {
		return bytesWritten;
	}
}