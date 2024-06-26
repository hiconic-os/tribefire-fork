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
package com.braintribe.utils.stream.stats;

public class StaticBlockStats implements StreamPipeBlockStats {

	private final int numUnused;
	private final int numTotal;
	private final long bytesUnused;
	private final long bytesTotal;
	private final BlockKind blockKind;
	private final String location;
	private PoolKind poolKind;
	private int blockSize;
	private int maxBlocksAllocatable;
	private long maxBytesAllocatable;

	public StaticBlockStats(int numUnused, int numTotal, long bytesUnused, long bytesTotal, int maxBlocksAllocatable, long maxBytesAllocatable, int blockSize,
			BlockKind blockKind, PoolKind poolKind, String location) {
		this.numUnused = numUnused;
		this.numTotal = numTotal;
		this.bytesUnused = bytesUnused;
		this.bytesTotal = bytesTotal;
		this.maxBlocksAllocatable = maxBlocksAllocatable;
		this.maxBytesAllocatable = maxBytesAllocatable;
		this.blockSize = blockSize;
		this.blockKind = blockKind;
		this.poolKind = poolKind;
		this.location = location;
	}

	public StaticBlockStats(StreamPipeBlockStats blockStats) {
		this( //
				blockStats.getNumUnused(), //
				blockStats.getNumTotal(), //
				blockStats.getBytesUnused(), //
				blockStats.getBytesTotal(), //
				blockStats.getMaxBlocksAllocatable(), //
				blockStats.getMaxBytesAllocatable(), //
				blockStats.getBlockSize(), //
				blockStats.getBlockKind(), //
				blockStats.getPoolKind(), //
				blockStats.getLocation() //
		);

	}

	public static StaticBlockStats empty() {
		return new StaticBlockStats(0, 0, 0, 0, 0, 0, 0, null, null, null);
	}

	@Override
	public int getNumUnused() {
		return numUnused;
	}

	@Override
	public int getNumTotal() {
		return numTotal;
	}

	@Override
	public long getBytesUnused() {
		return bytesUnused;
	}

	@Override
	public long getBytesTotal() {
		return bytesTotal;
	}

	@Override
	public BlockKind getBlockKind() {
		return blockKind;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public int getBlockSize() {
		return blockSize;
	}
	
	@Override
	public int getMaxBlocksAllocatable() {
		return maxBlocksAllocatable;
	}

	@Override
	public long getMaxBytesAllocatable() {
		return maxBytesAllocatable;
	}
	
	static long addPositive(long a, long b) {
		if (a < 0 || b < 0) {
			return -1;
		}

		return a + b;
	}

	@Override
	public PoolKind getPoolKind() {
		return poolKind;
	}
}
