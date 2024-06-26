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

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.braintribe.utils.FileTools;
import com.braintribe.utils.stream.blocks.Block;
import com.braintribe.utils.stream.pools.CompoundBlockPoolBuilder.FileBlockSupplier;
import com.braintribe.utils.stream.pools.CompoundBlockPoolBuilder.InMemoryBlockSupplier;
import com.braintribe.utils.stream.stats.BlockKind;
import com.braintribe.utils.stream.stats.PoolKind;


/**
 * A {@link BlockPool} that is initially empty and grows until a certain maximum number of blocks which will never be destroyed until {@link #shutDown()} 
 * 
 * @author Neidhart.Orlich
 *
 */
public class GrowingBlockPool extends BlockPool {
	protected final int maxBlocks;

	private final Queue<Block> blocks = new ConcurrentLinkedQueue<>();
	private final Supplier<Block> blockSupplier;

	protected final SafeCounterWithoutLock totalBlockCounter = new SafeCounterWithoutLock();
	
	/**
	 * @param blockSupplier used to construct a new block
	 * @param maxNumBlocks maximum total number of blocks that can be <i>created</i> by this pool. This is not related to the number of blocks currently <i>contained</i>
	 */
	public GrowingBlockPool(Supplier<Block> blockSupplier, int maxNumBlocks) {
		
		this.blockSupplier = blockSupplier;
		this.maxBlocks = maxNumBlocks;
		
	}

	@Override
	public Block get() {
		Block block = blocks.poll();
		
		if (block == null && !shutDown) {
			if (maxBlocks > 0 && totalBlockCounter.getValue() >= maxBlocks) {
				return null;
			}
			
			block = blockSupplier.get();
			block.setReturnConsumer(this::giveBack);
			totalBlockCounter.increment();
		}
		
		return block;
	}
	
	@Override
	protected void giveBackImpl(Block block) {
		blocks.add(block);
	}
	
	@Override
	protected void shutDownImpl() {
		blocks.forEach(block -> {
			block.destroy(); 
			blocks.remove(block);
		});
	}

	@Override
	public int getNumUnused() {
		return blocks.size();
	}

	@Override
	public int getNumTotal() {
		return totalBlockCounter.getValue();
	}

	@Override
	public long getBytesUnused() {
		return blocks.stream().collect(Collectors.summingLong(Block::getBytesAllocated));
	}

	@Override
	public long getBytesTotal() {
		if (blockSupplier instanceof FileBlockSupplier) {
			File blockFileRootDir = ((FileBlockSupplier) blockSupplier).getBlockFileRootDir();
			return FileTools.getFolderSizeInBytes(blockFileRootDir.toPath());
		} 
		else if (blockSupplier instanceof InMemoryBlockSupplier) {
			return getNumTotal() * getBlockSize();
		}
		
		return 0;
	}
	
	@Override
	public int getMaxBlocksAllocatable() {
		return maxBlocks;
	}
	
	@Override
	public int getBlockSize() {
		switch (getBlockKind()) {
			case file:
				return -1;
			case inMemory:
				return ((InMemoryBlockSupplier) blockSupplier).getBlockSize();
			default:
				throw new IllegalStateException("Unexpected block type: " + getBlockKind());
		}
	}
	
	@Override
	public BlockKind getBlockKind() {
		if (blockSupplier instanceof FileBlockSupplier) {
			return BlockKind.file;
		} 
		else if (blockSupplier instanceof InMemoryBlockSupplier) {
			return BlockKind.inMemory;
		}
		
		throw new IllegalStateException("Unexpected block supplier: " + blockSupplier);
	}

	@Override
	public String getLocation() {
		if (blockSupplier instanceof FileBlockSupplier) {
			return ((FileBlockSupplier) blockSupplier).getBlockFileRootDir().getAbsolutePath();
		} 
		
		return null;
	}

	@Override
	public PoolKind getPoolKind() {
		return PoolKind.growing;
	}
	
}
