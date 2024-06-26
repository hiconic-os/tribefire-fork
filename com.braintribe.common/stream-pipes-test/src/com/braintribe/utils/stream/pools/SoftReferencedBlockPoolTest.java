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
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.braintribe.utils.stream.blocks.Block;
import com.braintribe.utils.stream.pools.CompoundBlockPoolBuilder.InMemoryBlockSupplier;

public class SoftReferencedBlockPoolTest extends AbstractBlockPoolTest {
	ReferenceQueue<Block> blockRefs;

	@Override
	BlockPool blockPool() {
		blockRefs = new ReferenceQueue<>();
		return new SoftReferencingBlockPool(new InMemoryBlockSupplier(1), poolSize(), blockRefs);
	}

	/**
	 * Assert that after high memory load all contained blocks are in the reference pool
	 */
	@Test
	public void testGarbageCollecting() throws IOException, Exception {
		BlockPool pool = blockPool();
		Set<Block> blocks = new HashSet<>();

		for (int i = 0; i < poolSize(); i++) {
			Block block = pool.get();
			blocks.add(block);

			// We need to write to make sure the resources are acquired (i.e. file is created)
			try (OutputStream out = block.openOutputStream()) {
				out.write(0);
			}
		}

		blocks.forEach(Block::free);
		blocks.clear();
		byte[][] b;
		try {
			b = new byte[1_000][];

			for (int i = 0; i < b.length; i++) {
				b[i] = new byte[1_000_000_000];
			}

			fail("Failed to simulate OutOfMemoryError");
		} catch (OutOfMemoryError e) {
			// simulate high memory load
			System.out.println("Simulated OutOfMemoryError");
			Thread.sleep(1000);
			System.out.println("Blocks should have been freed by now. Checking...");
		}

		// Assert that the blocks have been garbage collected by asserting the size of the reference queue
		for (int i = 0; i < poolSize(); i++) {
			Reference<? extends Block> ref = blockRefs.poll();

			assertThat(ref).isNotNull();
			Block block = ref.get();

			assertThat(block).isNull();
		}

		// Assert that blocks get generated again after being removed by garbage collection
		for (int i = 0; i < poolSize(); i++) {
			Block block = pool.get();
			assertThat(block).isNotNull();
			blocks.add(block);
		}

		assertThat(pool.get()).isNull();
	}

	@Override
	@Ignore
	public void testShutdown() throws IOException {
		// This test does not make sense so ignore it
		// As this pool only holds soft references to blocks it can't guarantee to destroy blocks anyway
	}

	@Override
	int poolSize() {
		return 10;
	}
}
