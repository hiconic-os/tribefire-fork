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
public interface SqlDialect {

	String booleanType();
	String integerType();
	String longType();
	String floatType();
	String doubleType();
	String decimalType();
	String stringType();
	String longStringType();
	String dateType();
	String enumType();
	String entityType();

	String showDatabasesLike(String databaseName);

	String createDatabase(String databaseName);

	String showTablesLike(String tableName);

	String createTable(String tableName, String columns);

	String dropTable(String tableName);

	String createMetaTable();

	String selectFromMetaTable();

	String insertIntoPsTemplate(String tableName, String joinedColumnNames, String valuesWildcards);
}
