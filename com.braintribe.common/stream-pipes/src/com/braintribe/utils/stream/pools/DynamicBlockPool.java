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
package com.braintribe.utils.stream.pools;

import java.util.Objects;

import com.braintribe.utils.stream.blocks.Block;
import com.braintribe.utils.stream.stats.BlockKind;
import com.braintribe.utils.stream.stats.PoolKind;

/**
 * This {@link BlockPool} uses a {@link GrowingBlockPool} as a delegate but might decide to {@link Block#destroy()} a block upon
 * {@link #push(Block)} instead of returning it to its delegate if it decides that there are too many unused blocks.
 * That way unused resources can be freed and acquired again later if needed.
 * 
 * @author Neidhart.Orlich
 *
 */
public class DynamicBlockPool extends BlockPool {

	private final SafeCounterWithoutLock usedBlockCounter = new SafeCounterWithoutLock();
	private final GrowingBlockPool delegate;

	public DynamicBlockPool(GrowingBlockPool delegate) {
		Objects.requireNonNull(delegate, "DynamicBlockPool can't be created without a delegate.");
		this.delegate = delegate;
	}

	@Override
	public Block get() {
		Block block = delegate.get();

		if (block != null)
			usedBlockCounter.increment();

		return block;
	}

	@Override
	protected void giveBackImpl(Block block) {
		if (tooManyUnusedBlocks()) {
			block.destroy();
			delegate.totalBlockCounter.increment(-1);
		} else {
			delegate.giveBackImpl(block);
		}

		usedBlockCounter.increment(-1);
	}

	private boolean tooManyUnusedBlocks() {
		int total = delegate.totalBlockCounter.getValue();
		int used = usedBlockCounter.getValue();

		return used < total / 2;
	}

	@Override
	protected void shutDownImpl() {
		delegate.shutDown();
	}

	@Override
	public int getNumUnused() {
		return delegate.getNumUnused();
	}

	@Override
	public int getNumTotal() {
		return delegate.getNumTotal();
	}

	@Override
	public long getBytesUnused() {
		return delegate.getBytesUnused();
	}

	@Override
	public long getBytesTotal() {
		return delegate.getBytesTotal();
	}
	
	@Override
	public int getMaxBlocksAllocatable() {
		return delegate.getMaxBlocksAllocatable();
	}
	
	@Override
	public int getBlockSize() {
		return delegate.getBlockSize();
	}
	
	@Override
	public BlockKind getBlockKind() {
		return delegate.getBlockKind();
	}
	
	@Override
	public String getLocation() {
		return delegate.getLocation();
	}

	@Override
	public PoolKind getPoolKind() {
		return PoolKind.dynamic;
	}

}
