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

import static com.braintribe.utils.stream.stats.StaticBlockStats.addPositive;

public interface StreamPipeBlockStats {

	int getNumUnused();
	int getNumTotal();
	long getBytesUnused();
	long getBytesTotal();
	int getBlockSize();  // -1 means that the amount of allocatable bytes is only limited by hardware space
	int getMaxBlocksAllocatable(); // -1 means that the amount of allocatable bytes is only limited by hardware space
	
	default long getMaxBytesAllocatable() { 	// -1 means that the amount of allocatable bytes is only limited by hardware space  
		if (getMaxBlocksAllocatable() == -1 || getBlockSize() == -1) {
			return -1;
		}
		
		return getBlockSize() * getMaxBlocksAllocatable();
	}

	BlockKind getBlockKind();
	PoolKind getPoolKind();
	String getLocation();

	static StreamPipeBlockStats merge(StreamPipeBlockStats original, StreamPipeBlockStats toAdd) {
		if (original == null)
			return toAdd;

		PoolKind mergedPoolKind = original.getPoolKind() == toAdd.getPoolKind() ? original.getPoolKind() : null;
		
		return new StaticBlockStats( //
				original.getNumUnused() + toAdd.getNumUnused(), //
				original.getNumTotal() + toAdd.getNumTotal(), //
				original.getBytesUnused() + toAdd.getBytesUnused(), //
				original.getBytesTotal() + toAdd.getBytesTotal(), //
				(int) addPositive(original.getMaxBlocksAllocatable(), toAdd.getMaxBlocksAllocatable()), //
				addPositive(original.getMaxBytesAllocatable(), toAdd.getMaxBytesAllocatable()), //
				0, //
				original.getBlockKind(), mergedPoolKind, original.getLocation());
	}
	
}
