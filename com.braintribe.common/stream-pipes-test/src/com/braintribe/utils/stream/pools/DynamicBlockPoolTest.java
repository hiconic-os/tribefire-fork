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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.braintribe.utils.stream.blocks.Block;

public class DynamicBlockPoolTest extends AbstractBlockPoolTest {

	/**
	 * Assert that a part of the returned blocks were destroyed and created again
	 */
	@Test
	public void testReturning2() throws IOException {
		BlockPool pool = blockPool();

		Set<Block> recievedBlocks = new HashSet<>();

		for (int i = 0; i < poolSize(); i++) {
			Block block = pool.get();
			recievedBlocks.add(block);
		}

		recievedBlocks.forEach(pool::giveBack);

		Set<Block> recievedBlocks2 = new HashSet<>();
		for (int i = 0; i < 10; i++) {
			Block block = pool.get();
			recievedBlocks2.add(block);

			// We need to write to make sure the resources are acquired (i.e. file is created)
			try (OutputStream out = block.openOutputStream()) {
				out.write(0);
			}
		}

		assertThat(recievedBlocks).anyMatch(this::wasCleanedUp);
		assertThat(recievedBlocks).anyMatch(block -> wasCleanedUp(block) == false);
		assertThat(recievedBlocks2).allMatch(block -> wasCleanedUp(block) == false);
		assertThat(recievedBlocks).isNotEqualTo(recievedBlocks2);
	}

	@Override
	BlockPool blockPool() {
		return new DynamicBlockPool(new GrowingBlockPool(blockSupplier, poolSize()));
	}

	@Override
	int poolSize() {
		return 10;
	}
}
