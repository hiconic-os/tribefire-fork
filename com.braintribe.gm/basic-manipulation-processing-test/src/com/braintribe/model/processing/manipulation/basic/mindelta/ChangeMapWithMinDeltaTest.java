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
package com.braintribe.model.processing.manipulation.basic.mindelta;

import static com.braintribe.utils.lcd.CollectionTools2.asMap;

import java.util.Map;

import org.junit.Test;

import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.processing.manipulation.testdata.manipulation.TestEntity;
import com.braintribe.utils.junit.assertions.BtAssertions;

/**
 * @see ChangeMapWithMinDelta
 */
public class ChangeMapWithMinDeltaTest extends AbstractMinDeltaTest {

	private static Property property = TestEntity.T.getProperty("intMap");

	private Map<Integer, Integer> oldMap;
	private Map<Integer, Integer> newMap;

	@Test
	public void noChange_Empty() throws Exception {
		oldMap();
		newMap();

		record(session -> ChangeMapWithMinDelta.apply(entity, property, oldMap, newMap));
		BtAssertions.assertThat(recordedManipulations).hasSize(0);
	}

	@Test
	public void noChange_NonEmpty() throws Exception {
		oldMap(1, 10);
		newMap(1, 10);

		record(session -> ChangeMapWithMinDelta.apply(entity, property, oldMap, newMap));
		BtAssertions.assertThat(recordedManipulations).hasSize(0);
	}

	@Test
	public void changeAllValues() throws Exception {
		oldMap(1, 10, 2, 20, 3, 30);
		newMap(4, 40, 5, 50, 6, 60);

		record(session -> ChangeMapWithMinDelta.apply(entity, property, oldMap, newMap));
		assertChangeValue(newMap);
	}

	@Test
	public void onlyRemove() throws Exception {
		oldMap(1, 10, 2, 20, 3, 30);
		newMap(1, 10, 2, 20);

		record(session -> ChangeMapWithMinDelta.apply(entity, property, oldMap, newMap));
		assertRemove(asMap(3, 30));
	}

	@Test
	public void onlyAdd() throws Exception {
		oldMap(1, 10, 2, 20, 3, 30);
		newMap(1, 10, 2, 20, 3, 30, 4, 40);

		record(session -> ChangeMapWithMinDelta.apply(entity, property, oldMap, newMap));
		assertAdd(asMap(4, 40));
	}

	@Test
	public void onlyReplace() throws Exception {
		oldMap(1, 10, 2, 20, 3, 30);
		newMap(1, 10, 2, 20, 3, 300);
		
		record(session -> ChangeMapWithMinDelta.apply(entity, property, oldMap, newMap));
		assertAdd(asMap(3, 300));
	}
	
	
	@Test
	public void mixOfAll() throws Exception {
		oldMap(1, 10, 2, 20, 3, 30, 4, 40);
		newMap(1, 10, 2, 20, 4, 400, 5, 50);

		record(session -> ChangeMapWithMinDelta.apply(entity, property, oldMap, newMap));
		assertRemove(asMap(3, 30));
		assertAdd(asMap(4, 400, 5, 50));
	}
	
	@Test
	public void mixOfAll_CvmIsBetter() throws Exception {
		oldMap(1, 10, 2, 20, 3, 30);
		newMap(1, 10, 3, 300, 4, 40);

		record(session -> ChangeMapWithMinDelta.apply(entity, property, oldMap, newMap));
		assertChangeValue(newMap);
	}

	private void oldMap(Integer... values) {
		entity.setIntMap(asMap((Object[]) values));
		oldMap = entity.getIntMap();
	}

	private void newMap(Integer... values) {
		newMap = asMap((Object[]) values);
	}

}
