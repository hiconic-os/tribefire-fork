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
 * For information on which type to use with which DB see
 * <a href="https://docs.oracle.com/cd/E19501-01/819-3659/gcmaz">oracle docs</a>.
 * 
 * @author peter.gazdik
 */
public interface StandardSqls {

	String metaTable = "m_e_t_a_";
	String metaTable_Name = "name_";
	String metaTable_Hash = "hash_";

	// boolean
	String SQL_BOOLEAN = "boolean";

	// string
	String SQL_VARCHAR_255 = "varchar(255)";
	String SQL_CLOB_64K = "clob(64k)";

	// numbers
	String SQL_INT = "int";
	String SQL_BIGINT = "bigint";
	String SQL_FLOAT = "float";
	String SQL_DOUBLE = "double";
	String SQL_DECIMAL_31_10 = "decimal(31,10)";

	// time
	String SQL_TIMESTAMT = "timestamp";
	String SQL_DATETIME = "datetime";

	static String showDatabasesLike(String databaseName) {
		return String.format("SHOW DATABASES LIKE '%s'", databaseName);
	}

	static String createDatabase(String databaseName) {
		return "CREATE DATABASE " + databaseName;
	}

	static String showTablesLike(String tableName) {
		return String.format("SHOW TABLES LIKE '%s'", tableName);
	}

	static String createMetaTable(SqlDialect sqlDialect) {
		return createTable(metaTable,
				String.format("%s %s, %s %s", metaTable_Name, sqlDialect.stringType(), metaTable_Hash, sqlDialect.longStringType()));
	}

	static String createTable(String tableName, String columns) {
		return String.format("CREATE TABLE %s (%s)", tableName, columns);
	}

	static String selectFromMetaTable() {
		return String.format("SELECT %s, %s FROM %s", metaTable_Name, metaTable_Hash, metaTable);
	}

	static String dropTable(String tableName) {
		return "DROP TABLE IF EXISTS " + tableName;
	}

	static String insertIntoPsTemplate(String tableName, String joinedColumnNames, String valuesWildcards) {
		return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, joinedColumnNames, valuesWildcards);
	}

}
