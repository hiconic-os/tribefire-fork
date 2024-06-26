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
package com.braintribe.model.access.sql.dialect;

/**
 * @author peter.gazdik
 */
public class DerbySqlDialect extends DefaultSqlDialect {

	@SuppressWarnings("hiding")
	public static final DerbySqlDialect INSTANCE = new DerbySqlDialect();

	private DerbySqlDialect() {
	}

	@Override
	public String showDatabasesLike(String databaseName) {
		throw new UnsupportedOperationException("'Show databases' is not supported with Derby dialect.");
	}

	@Override
	public String createDatabase(String databaseName) {
		throw new UnsupportedOperationException("'Create database' is not supported with Derby dialect.");
	}

	@Override
	public String showTablesLike(String tableName) {
		return String.format("select * from sys.systables where tabletype = 'T' AND tablename like '%s'", tableName);
	}

}
