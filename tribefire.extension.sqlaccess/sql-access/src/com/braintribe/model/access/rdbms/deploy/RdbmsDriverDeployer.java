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

import com.braintribe.model.access.sql.SqlAccessDriver;
import com.braintribe.model.access.sql.dialect.SqlDialect;
import com.braintribe.model.dbs.DbSchema;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.meta.cmd.CmdResolverImpl;
import com.braintribe.model.processing.meta.oracle.BasicModelOracle;
import com.braintribe.model.processing.meta.oracle.ModelOracle;

import tribefire.extension.sqlaccess.model.RdbmsDriver;

/**
 * @author peter.gazdik
 */
public class RdbmsDriverDeployer {

	public static SqlAccessDriver deploy(RdbmsDriver denotation, DataSource dataSource) {
		return new RdbmsDriverDeployer(denotation, dataSource).deploy();
	}

	// --------------------------------------------------------------------------------------------

	private final RdbmsDriver denotation;
	private final DataSource dataSource;
	private final ModelOracle modelOracle;
	private final CmdResolver cmdResolver;

	private SqlDialect sqlDialect;

	private DbSchema dbSchema;

	private RdbmsDriverDeployer(RdbmsDriver denotation, DataSource dataSource) {
		this.denotation = denotation;
		this.dataSource = dataSource;
		this.modelOracle = new BasicModelOracle(denotation.getMetaModel());
		this.cmdResolver = new CmdResolverImpl(modelOracle);
	}

	private SqlAccessDriver deploy() {
		prepareSqlDialect();
		prepareDbSchema();

		initializeDbs();

		return newRdbmsDriver();
	}

	private void prepareSqlDialect() {
		sqlDialect = RdbmsSqlDialectProvider.provide(denotation);
	}

	private void prepareDbSchema() {
		dbSchema = RdbmsDbSchemaProvider.provide(cmdResolver, sqlDialect);
	}

	private void initializeDbs() {
		RdbmsSchemaManager schemaManager = new RdbmsSchemaManager(denotation, dataSource, sqlDialect);

		schemaManager.ensureDatabase(denotation.getDatabaseName());
		schemaManager.ensureTables(dbSchema);
	}

	private SqlAccessDriver newRdbmsDriver() {
		com.braintribe.model.access.rdbms.RdbmsDriver result = new com.braintribe.model.access.rdbms.RdbmsDriver();
		result.setDataSource(dataSource);
		result.setCmdResolver(cmdResolver);
		result.setSqlDialect(sqlDialect);

		return result;
	}

}
