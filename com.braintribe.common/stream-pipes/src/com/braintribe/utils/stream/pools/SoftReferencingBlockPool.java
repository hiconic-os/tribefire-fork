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

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import com.braintribe.utils.stream.blocks.Block;
import com.braintribe.utils.stream.blocks.InMemoryBlock;
import com.braintribe.utils.stream.pools.CompoundBlockPoolBuilder.InMemoryBlockSupplier;
import com.braintribe.utils.stream.stats.BlockKind;
import com.braintribe.utils.stream.stats.PoolKind;

/**
 * This {@link BlockPool} uses {@link SoftReference}s to reference the blocks it currently holds. Thus these blocks can
 * be freed if there are a lot of unused blocks and the memory experiences high load. Note that this pool only makes
 * sense in combination with {@link InMemoryBlock}s because unlike other BlockPool implementations this pool does not destroy its
 * blocks upon shutdown or removal and will cause resource leaks.
 * 
 * @author Neidhart.Orlich
 *
 */
public class SoftReferencingBlockPool extends BlockPool {
	protected final int maxBlocks;

	private final Queue<SoftReference<Block>> blockRefs = new ConcurrentLinkedQueue<>();
	private final InMemoryBlockSupplier blockSupplier;
	private final ReferenceQueue<Block> referenceQueue;

	protected final SafeCounterWithoutLock totalBlockCounter = new SafeCounterWithoutLock();

	protected SoftReferencingBlockPool(InMemoryBlockSupplier blockSupplier, int maxNumBlocks, ReferenceQueue<Block> referenceQueue) {
		this.blockSupplier = blockSupplier;
		this.maxBlocks = maxNumBlocks;
		this.referenceQueue = referenceQueue;
	}

	/**
	 * @param blockSupplier Used to create a new block, either initially or to recreate it after it was garbage collected
	 * @param maxNumBlocks Maximum number of blocks that can be created by this pool. Note that blocks still might have to be re-created after it was garbage collected. Re-creation is of course not affected by this number.
	 */
	public SoftReferencingBlockPool(InMemoryBlockSupplier blockSupplier, int maxNumBlocks) {
		this(blockSupplier, maxNumBlocks, null);
	}

	@Override
	public Block get() {
		if (shutDown) {
			return null;
		}

		SoftReference<Block> blockRef = blockRefs.poll();

		if (blockRef == null) {
			if (totalBlockCounter.getValue() >= maxBlocks) {
				return null;
			}

			return createNewBlock();
		}

		Block block = blockRef.get();

		if (block == null) {
			return createNewBlock();
		}

		return block;
	}

	private Block createNewBlock() {
		Block block = blockSupplier.get();
		block.setReturnConsumer(this::giveBack);
		totalBlockCounter.increment();

		return block;
	}

	@Override
	protected void giveBackImpl(Block block) {
		blockRefs.add(new SoftReference<>(block, referenceQueue));
	}

	@Override
	protected void shutDownImpl() {
		blockRefs.clear();
	}

	@Override
	public int getNumUnused() {
		return blockRefs.size();
	}

	@Override
	public int getNumTotal() {
		return totalBlockCounter.getValue();
	}

	@Override
	public long getBytesUnused() {
		return blockRefs.stream().map(SoftReference::get).filter(Objects::nonNull).collect(Collectors.summingLong(Block::getBytesAllocated));
	}

	@Override
	public long getBytesTotal() {
		int blockSize = blockSupplier.getBlockSize();
		return getNumTotal() * blockSize;
	}
	
	@Override
	public BlockKind getBlockKind() {
		return BlockKind.inMemory;
	}
	
	@Override
	public String getLocation() {
		return null;
	}
	
	@Override
	public int getMaxBlocksAllocatable() {
		return maxBlocks;
	}

	@Override
	public int getBlockSize() {
		return blockSupplier.getBlockSize();
	}

	@Override
	public PoolKind getPoolKind() {
		return PoolKind.softReferencing;
	}
}
