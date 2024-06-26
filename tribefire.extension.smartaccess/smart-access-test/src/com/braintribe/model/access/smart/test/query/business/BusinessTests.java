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
package com.braintribe.model.access.smart.test.query.business;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.business.CustomerA;
import com.braintribe.model.processing.query.smart.test.model.accessB.business.JdeInventoryB;
import com.braintribe.model.processing.query.smart.test.model.accessB.business.SapInventoryB;
import com.braintribe.model.processing.query.smart.test.model.smart.business.JdeInventory;
import com.braintribe.model.processing.query.smart.test.model.smart.business.Product;
import com.braintribe.model.processing.query.smart.test.model.smart.business.SapInventory;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.PaginatedSet;
import com.braintribe.model.smartqueryplan.set.OrderedConcatenation;

/**
 * 
 */
public class BusinessTests extends AbstractBuisnessTests {

	/**
	 * This is the situation: we have {@link OrderedConcatenation} consisting of two {@link PaginatedSet}s. There was a
	 * bug that these paginates sets were creating tuples with wrong dimension (the dimension of the result is 1, but
	 * arguments of the {@link OrderedConcatenation} must have 2 dimensions, since the other value is used for
	 * comparison). To force this, we also need to have DQJ inside, because otherwise the pagination could be delegated
	 * in the query.
	 * 
	 * <pre>
	 * PaginatedSet { Projection {
	 * 		OrderedConcatenation{
	 * 			first: PaginatedSet {
	 * 	 			DQJ {
	 * 					materialized: DQS(CustomerA)
	 * 					query: DQS(JDEInventory)
	 * 				} 		
	 * 			}
	 * 			second: PaginatedSet {
	 * 	 			DQJ {
	 * 					materialized: DQS(CustomerA)
	 * 					query: DQS(SAPInventory)
	 * 				} 		
	 * 			}
	 * 		}
	 * }}
	 * 
	 * <pre>
	 */
	@Test
	public void splitQueryWithDqjAndPaging() {
		CustomerA c1 = customerA("ucn1");
		CustomerA c2 = customerA("ucn2");

		JdeInventoryB jb1 = jde(c1.getUcn(), "i1");
		SapInventoryB sb1 = sap(c2.getUcn(), "i1");

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Product.class, "p")
				.select("p")
				.orderBy().property("p", "customer.id")
				.limit(10)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertNextResult(newInstance(JdeInventory.T, jb1));
		assertNextResult(newInstance(SapInventory.T, sb1));
		assertNoMoreResults();
	}

}
