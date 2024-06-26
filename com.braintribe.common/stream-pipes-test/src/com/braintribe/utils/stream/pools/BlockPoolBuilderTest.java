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
import java.io.FileWriter;
import java.nio.file.Files;

import org.junit.Test;

import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;
import com.braintribe.utils.stream.blocks.FileBlock;
import com.braintribe.utils.stream.pools.CompoundBlockPool;
import com.braintribe.utils.stream.pools.CompoundBlockPoolBuilder;

public class BlockPoolBuilderTest {
	/**
	 * Asserts that files within the provided rootDir for a FileBlock backed pool are recycled
	 */
	@Test
	public void testFileBlocks() throws Exception {
		final String CONTENT = "I exist";
		File rootDir = Files.createTempDirectory(BlockPoolBuilderTest.class.getSimpleName()).toFile();

		File subDir = new File(rootDir, "1");
		subDir.mkdirs();
		File orphanedBlockFile = new File(subDir, "orphan");

		try (FileWriter fileWriter = new FileWriter(orphanedBlockFile)) {
			fileWriter.write(CONTENT);
		}

		CompoundBlockPool pool = CompoundBlockPoolBuilder.start().appendDynamicFileBlockPool(rootDir).build();

		// The first Block's file should be our orphaned one
		FileBlock block = (FileBlock) pool.blockSupplier().get();

		File file = block.getFile();
		Assertions.assertThat(file) //
				.hasSameAbsolutePathAs(orphanedBlockFile) //
				.hasContent(CONTENT); //

		// The second Block's file should have been generated newly
		block = (FileBlock) pool.blockSupplier().get();

		Assertions.assertThat(block.getFile()) //
				.isNotNull() //
				.doesNotHaveSameAbsolutePathAs(orphanedBlockFile) //
				.doesNotExist() //
				.hasAncestor(rootDir);
	}
}
