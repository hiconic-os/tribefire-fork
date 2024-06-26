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
package com.braintribe.model.processing.manipulation.expert;

import java.util.Arrays;

import org.junit.Test;

import com.braintribe.model.accessapi.ManipulationRequest;
import com.braintribe.model.processing.manipulation.AbstractManipulationTest;
import com.braintribe.model.processing.manipulation.basic.normalization.Normalizer;
import com.braintribe.model.processing.manipulation.testdata.manipulation.TestEntity;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.utils.junit.assertions.BtAssertions;

/**
 * This test has four parts:
 * <ol>
 * <li>Initialization of the collection</li>
 * <li>Recording manipulations with the original collections, but not applying them yet</li>
 * <li>Changing the original collection</li>
 * <li>Applying manipulations recorded in step 2</li>
 * </ol>
 * 
 * If everything works OK, the step 3 should not have an effect on which elements were actually removed (there will of course not be any
 * problem with inserts)
 */
public class ConcurrentCollectionModificationTest extends AbstractManipulationTest {

	@Test
	public void listManipulations() throws Exception {
		apply((PersistenceGmSession session) -> {
			TestEntity entity = createDefaultEntity(session);

			entity.setIntList(Arrays.asList(1, 99, null, 2));

			TestEntity e1 = createEntity(session, "e1");
			entity.setSomeList(Arrays.asList(entity, e1, null, entity));
		});

		ManipulationRequest request = prepare((PersistenceGmSession session) -> {
			TestEntity entity = queryDefualtEntity(session);

			entity.getIntList().remove(2);
			entity.getIntList().remove(1);
			entity.getSomeList().remove(2);
			entity.getSomeList().remove(1);
		});

		apply((PersistenceGmSession session) -> {
			TestEntity entity = queryDefualtEntity(session);

			entity.getIntList().add(0, 0);
			entity.getSomeList().add(0, entity);
		});

		apply(request);

		TestEntity e = queryDefualtEntity();

		BtAssertions.assertThat(e.getIntList()).isNotNull().isEqualTo(Arrays.asList(0, 1, 2));
		BtAssertions.assertThat(e.getSomeList()).isNotNull().isEqualTo(Arrays.asList(e, e, e));
	}

	/**
	 * Same as previous test, but uses bulk manipulations (those recorded in step 2) instead of simple ones
	 */
	@Test
	public void bulkListManipulations() throws Exception {
		apply((PersistenceGmSession session) -> {
			TestEntity entity = createDefaultEntity(session);

			entity.setIntList(Arrays.asList(1, 99, 2, null));

			TestEntity e1 = createEntity(session, "e1");
			entity.setSomeList(Arrays.asList(entity, e1, entity, null));
		});

		ManipulationRequest request = prepare((PersistenceGmSession session) -> {
			TestEntity entity = queryDefualtEntity(session);

			entity.getIntList().remove(3);
			entity.getIntList().remove(1);
			entity.getSomeList().remove(3);
			entity.getSomeList().remove(1);
		});

		// This will turn the remove manipulations to bulk ones
		request.setManipulation(Normalizer.normalize(request.getManipulation()));

		apply((PersistenceGmSession session) -> {
			TestEntity entity = queryDefualtEntity(session);

			entity.getIntList().add(0, 0);
			entity.getSomeList().add(0, entity);
		});

		apply(request);

		TestEntity e = queryDefualtEntity();

		BtAssertions.assertThat(e.getIntList()).isNotNull().isEqualTo(Arrays.asList(0, 1, 2));
		BtAssertions.assertThat(e.getSomeList()).isNotNull().isEqualTo(Arrays.asList(e, e, e));
	}

	/** There was a bug that this would cause an IndexOutOfBoundsException (see first comment of BTT-5355). */
	@Test
	public void removingLastListItem() throws Exception {
		apply((PersistenceGmSession session) -> {
			TestEntity entity = createDefaultEntity(session);

			entity.setIntList(Arrays.asList(0, 1, 2, null));
		});

		ManipulationRequest request = prepare((PersistenceGmSession session) -> {
			TestEntity entity = queryDefualtEntity(session);
			entity.getIntList().remove(3);
		});

		// when removing item on position 3 (i.e. value==null), we do it once and second time we do nothing
		apply(request);
		apply(request);

		TestEntity e = queryDefualtEntity();
		BtAssertions.assertThat(e.getIntList()).isNotNull().isEqualTo(Arrays.asList(0, 1, 2));
	}

	/** There was a bug that this would cause an IndexOutOfBoundsException (see first comment of BTT-5355). */
	@Test
	public void removingLastListItem_WhenPresentTwice() throws Exception {
		apply((PersistenceGmSession session) -> {
			TestEntity entity = createDefaultEntity(session);

			entity.setIntList(Arrays.asList(0, 1, 2, null, null));
		});

		ManipulationRequest request = prepare((PersistenceGmSession session) -> {
			TestEntity entity = queryDefualtEntity(session);
			entity.getIntList().remove(4);
		});

		// when removing item on position 4 (i.e. value==null), we do it once and then we remove the value at position 3
		apply(request);
		apply(request);

		TestEntity e = queryDefualtEntity();
		BtAssertions.assertThat(e.getIntList()).isNotNull().isEqualTo(Arrays.asList(0, 1, 2));
	}

}
