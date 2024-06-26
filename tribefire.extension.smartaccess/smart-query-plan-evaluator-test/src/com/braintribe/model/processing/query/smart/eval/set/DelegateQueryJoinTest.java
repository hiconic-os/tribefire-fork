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
package com.braintribe.model.processing.query.smart.eval.set;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.eval.api.EvalTupleSet;
import com.braintribe.model.processing.query.smart.eval.set.base.AbstractDelegateQueryJoinTest;
import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.query.smart.processing.eval.set.EvalDelegateQueryJoin;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.smartqueryplan.set.DelegateQueryJoin;

public class DelegateQueryJoinTest extends AbstractDelegateQueryJoinTest {

	private int bulkSize = 100;

	@Before
	public void buildData() {
		for (int i = 0; i < 10; i++) {
			aPersons.add(bA.personA("a" + i).parentB("b" + i).create());
			bPersons.add(bA.personA("b" + i).companyNameA("c" + i).create());
		}
	}

	@Test
	public void simpleDqj() {
		DelegateQueryJoin dqj = buildDqj();
		evaluate(dqj);
		assertContainsAllPersonData();
	}

	@Test
	public void bulkDqj_BatchSizeDividesTotlaResultCount() {
		bulkSize = 2;
		simpleDqj();
	}

	@Test
	public void bulkDqj_General() {
		bulkSize = 3;
		simpleDqj();
	}

	@Override
	protected EvalTupleSet resolveTupleset(TupleSet tupleSet) {
		EvalTupleSet result = super.resolveTupleset(tupleSet);
		if (result instanceof EvalDelegateQueryJoin) {
			((EvalDelegateQueryJoin) result).setBulkSize(bulkSize);
		}

		return result;
	}

	// ###################################
	// ## . . . . . Asserts . . . . . . ##
	// ###################################

	private void assertContainsAllPersonData() {
		for (int i = 0; i < 10; i++) {
			PersonA a = aPersons.get(i);
			PersonA b = bPersons.get(i);

			assertContainsTuple(a.getNameA(), b.getNameA(), b.getCompanyNameA());
		}

		assertNoMoreTuples();
	}
}
