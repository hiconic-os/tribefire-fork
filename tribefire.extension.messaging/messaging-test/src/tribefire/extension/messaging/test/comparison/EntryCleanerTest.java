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
package tribefire.extension.messaging.test.comparison;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import tribefire.extension.messaging.model.comparison.CollectionType;
import tribefire.extension.messaging.service.utils.EntryCleaner;
import tribefire.extension.messaging.test.comparison.model.Complex;
import tribefire.extension.messaging.test.comparison.model.ComplexWithCollectionOfSimple;
import tribefire.extension.messaging.test.comparison.model.Simple;
import tribefire.extension.messaging.test.comparison.model.SimpleWithCollectionOfPrimitives;

public class EntryCleanerTest {
	@Test
	public void simple() {
		Simple simple = TestHelper.getSimple("1", "1");
		Set<String> path = Set.of("partition");

		Simple result = (Simple) EntryCleaner.cleanEntry(path, simple);
		assertNotNull(result.getPartition());
		assertNull(result.getGlobalId());
		assertNull(result.getId());
	}

	@Test
	public void simpleWithCollectionPrimitive() {
		SimpleWithCollectionOfPrimitives simple = TestHelper.getSimpleWithCollectionPrimitives(CollectionType.LIST, 1, 2, 3);
		Set<String> path = Set.of("name", "listPrimitive");

		SimpleWithCollectionOfPrimitives result = (SimpleWithCollectionOfPrimitives) EntryCleaner.cleanEntry(path, simple);
		assertNotNull(result.getName());
		assertNotNull(result.getListPrimitive());
		assertEquals(3, result.getListPrimitive().size());
		assertTrue(List.of(1, 2, 3).containsAll(result.getListPrimitive()));
		assertNull(result.getGlobalId());
		assertNull(result.getId());
		assertTrue(result.getMapPrimitive().isEmpty());
		assertTrue(result.getSetPrimitive().isEmpty());
		assertNull(result.getPartition());
	}

	@Test
	public void complex() {
		Complex complex = TestHelper.getComplex("1", "1");
		Set<String> path = Set.of("name", "simple.partition");

		Complex result = (Complex) EntryCleaner.cleanEntry(path, complex);
		assertNotNull(result.getName());
		assertNotNull(result.getSimple());
		assertNotNull(result.getSimple().getPartition());

		assertNull(result.getId());
		assertNull(result.getGlobalId());
		assertNull(result.getPartition());

		assertNull(result.getSimple().getId());
		assertNull(result.getSimple().getGlobalId());
	}

	@Test
	public void complex2() {
		ComplexWithCollectionOfSimple complex = TestHelper.getComplexWithCollection(CollectionType.LIST, false, false, false);
		Set<String> path = Set.of("listSimple.partition");

		ComplexWithCollectionOfSimple result = (ComplexWithCollectionOfSimple) EntryCleaner.cleanEntry(path, complex);
		assertNotNull(result.getListSimple());
		assertNotNull(result.getListSimple().get(0).getPartition());
		assertNotNull(result.getListSimple().get(1).getPartition());

		assertNull(result.getId());
		assertNull(result.getGlobalId());
		assertNull(result.getPartition());
		assertTrue(result.getSetSimple().isEmpty());
		assertTrue(result.getMapSimple().isEmpty());
	}
}
