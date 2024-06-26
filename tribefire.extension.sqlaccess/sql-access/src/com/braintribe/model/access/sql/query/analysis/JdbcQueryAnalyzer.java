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
package com.braintribe.model.access.sql.query.analysis;

import com.braintribe.model.access.sql.query.oracle.JdbcQuery;
import com.braintribe.model.query.SelectQuery;

/**
 * @author peter.gazdik
 */
public class JdbcQueryAnalyzer {

	@SuppressWarnings("unused")
	private final SelectQuery selectQuery;

	public JdbcQueryAnalyzer(SelectQuery selectQuery) {
		this.selectQuery = selectQuery;
	}

	public static JdbcQuery analyze(SelectQuery selectQuery) {
		return new JdbcQueryAnalyzer(selectQuery).analyze();
	}

	private JdbcQuery analyze() {
		throw new UnsupportedOperationException("Method 'JdbcQueryAnalyzer.analyze' is not implemented yet!");
	}

}
