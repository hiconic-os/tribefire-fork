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
package com.braintribe.model.access.smart.test.query2.basic;

import static com.braintribe.model.processing.query.smart.test2.basic.model.accessA.SimplePropertiesEntityA.date;
import static com.braintribe.model.processing.query.smart.test2.basic.model.accessA.SimplePropertiesEntityA.string;

import java.util.Date;

import org.junit.Test;

import com.braintribe.model.access.smart.test.query2._base.AbstractSmartQueryTests;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetup;
import com.braintribe.model.processing.query.smart.test2.basic.BasicSmartSetup;
import com.braintribe.model.processing.query.smart.test2.basic.model.accessA.SimplePropertiesEntityA;
import com.braintribe.model.processing.query.smart.test2.basic.model.smart.SmartSimplePropertiesEntityA;
import com.braintribe.model.query.SelectQuery;

/**
 * @author peter.gazdik
 */
public class Basic_QueryTest extends AbstractSmartQueryTests {

	@Override
	protected SmartModelTestSetup getSmartModelTestSetup() {
		return BasicSmartSetup.BASIC_SETUP;
	}

	@Test
	public void noData() throws Exception {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("e", GenericEntity.id)
				.from(SmartSimplePropertiesEntityA.T, "e")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertNoMoreResults();
	}

	@Test
	public void singleSimpleEntityProperties() throws Exception {
		bA.make(SimplePropertiesEntityA.T).set(string, "hello").set(date, new Date(0)).done();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("e", SmartSimplePropertiesEntityA.smartString)
				.select("e", SmartSimplePropertiesEntityA.smartDate)
				.from(SmartSimplePropertiesEntityA.T, "e")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("hello", new Date(0));
		assertNoMoreResults();
	}

	@Test
	public void singleSimpleEntity() throws Exception {
		SimplePropertiesEntityA e = bA.make(SimplePropertiesEntityA.T).set(string, "hello").set(date, new Date(0)).done();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("e")
				.from(SmartSimplePropertiesEntityA.T, "e")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		SmartSimplePropertiesEntityA se = acquireSmart(e, SmartSimplePropertiesEntityA.T);
		
		assertResultContains(se);
		assertNoMoreResults();
	}
}
