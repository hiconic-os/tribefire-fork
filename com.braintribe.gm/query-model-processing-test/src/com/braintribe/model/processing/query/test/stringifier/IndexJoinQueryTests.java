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
package com.braintribe.model.processing.query.test.stringifier;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.model.processing.query.test.model.Address;
import com.braintribe.model.processing.query.test.model.Company;
import com.braintribe.model.processing.query.test.model.Person;
import com.braintribe.model.query.SelectQuery;

/**
 *
 */
public class IndexJoinQueryTests extends AbstractSelectQueryTests {

	// ####################################
	// ## . . . . . Value Join . . . . . ##
	// ####################################

	@Test
	public void simpleValueJoin() {
		// @formatter:off
		 SelectQuery selectQuery = query()
				.from(Person.class, "_Person")
				.from(Company.class, "_Company")
				.where()
				.property("_Person", "companyName").eq().property("_Company", "indexedName")
				.done();
		// @formatter:on

		String queryString = stringify(selectQuery);
		Assert.assertTrue(queryString.equalsIgnoreCase(
				"select * from com.braintribe.model.processing.query.test.model.Person _Person, com.braintribe.model.processing.query.test.model.Company _Company where _Person.companyName = _Company.indexedName"));
	}

	// ####################################
	// ## . . . . . Range Join . . . . . ##
	// ####################################

	@Test
	public void simpleRangeJoin() {
		// @formatter:off
		 SelectQuery selectQuery = query()
				.from(Person.class, "_Person")
				.from(Company.class, "_Company")
				.where()
				.property("_Person", "birthDate").ge().property("_Company", "indexedDate")
				.done();
		// @formatter:on

		String queryString = stringify(selectQuery);
		Assert.assertTrue(queryString.equalsIgnoreCase(
				"select * from com.braintribe.model.processing.query.test.model.Person _Person, com.braintribe.model.processing.query.test.model.Company _Company where _Person.birthDate >= _Company.indexedDate"));
	}

	// ####################################
	// ## . . Generated Value Join . . . ##
	// ####################################

	@Test
	public void mergeLookupJoin() {
		// @formatter:off
		 SelectQuery selectQuery = query()
				.from(Person.class, "_Person")
				.from(Company.class, "_Company")
				.where()
				.property("_Person", "companyName").eq().property("_Company", "name")
				.done();
		// @formatter:on

		String queryString = stringify(selectQuery);
		Assert.assertTrue(queryString.equalsIgnoreCase(
				"select * from com.braintribe.model.processing.query.test.model.Person _Person, com.braintribe.model.processing.query.test.model.Company _Company where _Person.companyName = _Company.name"));
	}

	@Test
	public void mergeLookupJoinWithJoinOperand() {
		// @formatter:off
		 SelectQuery selectQuery = query()
				.select("_Company", "name")
				.from(Address.class, "_Address")
				.from(Company.class, "_Company")
				.join("_Company", "address", "_Address2")
				.where()
				.entity("_Address").eq().entity("_Address2")
				.done();
		// @formatter:on

		String queryString = stringify(selectQuery);
		Assert.assertTrue(queryString.equalsIgnoreCase(
				"select _Company.name from com.braintribe.model.processing.query.test.model.Address _Address, com.braintribe.model.processing.query.test.model.Company _Company join _Company.address _Address2 where _Address = _Address2"));
	}

	@Test
	public void mergeRangeJoin() {
		// @formatter:off
		 SelectQuery selectQuery = query()
				.from(Person.class, "_Person")
				.from(Company.class, "_Company")
				.where()
				.property("_Person", "companyName").ge().property("_Company", "name")
				.done();
		// @formatter:on

		String queryString = stringify(selectQuery);
		Assert.assertTrue(queryString.equalsIgnoreCase(
				"select * from com.braintribe.model.processing.query.test.model.Person _Person, com.braintribe.model.processing.query.test.model.Company _Company where _Person.companyName >= _Company.name"));
	}

	@Test
	public void mergeRangeJoinWithJoinOperand() {
		// @formatter:off
		 SelectQuery selectQuery = query()
				.select("_Address", "name")
				.select("_Company", "name")
				.from(Address.class, "_Address")
				.from(Company.class, "_Company")
				.join("_Company", "address", "_Address2")
				.where()
				.property("_Address", "name").ge().property("_Address2", "name")
				.done();
		// @formatter:on

		String queryString = stringify(selectQuery);
		Assert.assertTrue(queryString.equalsIgnoreCase(
				"select _Address.name, _Company.name from com.braintribe.model.processing.query.test.model.Address _Address, com.braintribe.model.processing.query.test.model.Company _Company join _Company.address _Address2 where _Address.name >= _Address2.name"));
	}
}
