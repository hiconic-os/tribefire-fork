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
package com.braintribe.model.query.parser.impl;

import org.junit.Test;

import com.braintribe.model.bvd.context.UserName;
import com.braintribe.model.processing.query.parser.QueryParser;
import com.braintribe.model.processing.query.parser.api.ParsedQuery;
import com.braintribe.model.processing.query.test.model.Person;
import com.braintribe.model.query.Query;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.conditions.ValueComparison;
import com.braintribe.utils.genericmodel.GMCoreTools;

public class ContextFunctionTest extends AbstractQueryParserTest {

	@Test
	public void testUserName() throws Exception {

		// @formatter:off
		SelectQuery expectedQuery = sq()
				.from(Person.class, "p")
				.where()
					.property("p", "name").eq().value(UserName.T.create())
				.done();
		// @formatter:on
		// this is needed as the builder does not provide the required functionality
		((ValueComparison) expectedQuery.getRestriction().getCondition()).setRightOperand(UserName.T.create());

		String queryString = "select * from " + Person.class.getName() + " p where p.name=userName()";

		ParsedQuery parsedQuery = QueryParser.parse(queryString);
		validatedParsedQuery(parsedQuery);
		Query actualQuery = parsedQuery.getQuery();
		GMCoreTools.checkDescription(actualQuery, expectedQuery);
	}

}
