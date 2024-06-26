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
package com.braintribe.model.access.rdbms.deploy;

import javax.sql.DataSource;

import com.braintribe.model.access.sql.dialect.SqlDialect;
import com.braintribe.model.dbs.DbSchema;

import tribefire.extension.sqlaccess.model.RdbmsDriver;

/**
 * @author peter.gazdik
 */
public class RdbmsSchemaManager {

	private final DataSource dataSource;
	private final RdbmsDriver driverDenotation;
	private final SqlDialect sqlDialect;
	private RdbmsDdlExpert ddlExpert;

	public RdbmsSchemaManager(RdbmsDriver driverDenotation, DataSource dataSource, SqlDialect sqlDialect) {
		this.driverDenotation = driverDenotation;
		this.dataSource = dataSource;
		this.sqlDialect = sqlDialect;
	}

	public void ensureDatabase(String databaseName) {
		ddlExpert = new RdbmsDdlExpert(dataSource, databaseName, sqlDialect);

		if (!driverDenotation.getEnsureDatabase())
			return;

		try {
			ddlExpert.ensureDatabase();

		} catch (Exception e) {
			throw new RuntimeException("Error while ensuring database: " + databaseName, e);
		}
	}

	public void ensureTables(DbSchema dbSchema) {
		try {
			ddlExpert.ensureMetaTable();

			ddlExpert.ensureSchema(dbSchema);

		} catch (Exception e) {
			throw new RuntimeException("Error while ensuring tables.", e);
		}
	}
}
