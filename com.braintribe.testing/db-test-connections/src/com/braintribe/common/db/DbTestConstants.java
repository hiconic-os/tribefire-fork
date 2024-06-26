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
package com.braintribe.common.db;

import com.braintribe.common.db.wire.contract.DbTestDataSourcesContract;

/**
 * @see DbTestDataSourcesContract
 *
 * @author peter.gazdik
 */
public interface DbTestConstants {

	String derbyUrl = "jdbc:derby://localhost:1527/res/db/dbtest;create=true";
	String derbyDriver = "org.apache.derby.jdbc.ClientDriver";

	String mssqlUrl = "jdbc:sqlserver://localhost:51433;databaseName=dbtest";
	String mssqlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	String mysqlUrl = "jdbc:mysql://localhost:53306/dbtest";
	String mysqlDriver = "com.mysql.jdbc.Driver";

	String oracleUrl = "jdbc:oracle:thin:@localhost:51521/xe";
	String oracleDriver = "oracle.jdbc.pool.OracleDataSource";

	String postgresUrl = "jdbc:postgresql://localhost:55432/dbtest";
	String postgresDriver = "org.postgresql.Driver";

	String dbTestUsername = "cortex";
	String dbTestPassword = "cortex";

}
