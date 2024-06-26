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
package com.braintribe.model.access.smart.test.query.special;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.access.smart.test.query.AbstractSmartQueryTests;
import com.braintribe.model.processing.query.smart.test.model.accessA.AllPurposeDelegateEntity;
import com.braintribe.model.processing.query.smart.test.model.smart.AllPurposeSmartEntity;
import com.braintribe.model.processing.query.smart.test.setup.base.AbstractSmartSetupProvider;
import com.braintribe.model.processing.query.smart.test.setup.base.SmartSetupProvider;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.testing.category.KnownIssue;

/**
 * This tests a special case for a left join, see {@link #EXPECTED_TO_FAIL_selectKpaProperty_LeftJoin()}
 * 
 * @author peter.gazdik
 */
public class IndirectPropertyLeftJoin_Tests extends AbstractSmartQueryTests {

	@Override
	protected SmartSetupProvider getSmartSetupProvider() {
		return new AbstractSmartSetupProvider() {

			@Override
			protected void configureMappings() {
				// @formatter:off
				editor.onEntityType(AllPurposeSmartEntity.T)
					.addMetaData(qualifiedEntityAssignment(AllPurposeDelegateEntity.T))
					.addPropertyMetaData(asIsProperty())
					.addPropertyMetaData("referencedEntity", kpa(cqp(AllPurposeDelegateEntity.T, "longProperty"), qp(AllPurposeDelegateEntity.T, "id")))
				;
				// @formatter:on
			}
		};
	}

	/**
	 * This is a bug,tough to solve this one. Here is the deal:
	 * 
	 * We have our entity, which references another entity using KPA, and the key property is the ID property. Our data
	 * is only a single entity, which looks like this:
	 * 
	 * <pre>
	 * AllPurposeDelegateEntity {
	 * 		id: null,
	 * 		stringProperty: "str1",
	 * 		longProperty: 99L
	 * }
	 * </pre>
	 * 
	 * When we query the "referencedEntity", we have a following tuple set: [null, "str1", 99L, {empty - joined
	 * stringProperty}, {empty - joined longProperty}]. Now we do a left join where we look for entity whose id is 99L.
	 * There is no such entity, thus this tuple stays the way it is, and it is the only tuple which is returned as the
	 * result of this DQJ.
	 * 
	 * Here is the problem, we now do not know, whether the right side of the join should be null, or it should be an
	 * entity whose stringProperty and longProperty are both null. Because the slot for original entity's longProperty
	 * and correlated entity's id is the same, we do not know if we actually found an entity with such id.
	 * 
	 * Currently, I am not sure what is the best way to fix this.
	 */
	@Test
	@Category(KnownIssue.class)
	public void EXPECTED_TO_FAIL_selectKpaProperty_LeftJoin() {
		bA.allPurpose().stringP("str1").longP(99L).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("a", "referencedEntity")
				.from(AllPurposeSmartEntity.class, "a")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertNextResult(null);
		assertNoMoreResults();
	}

}
