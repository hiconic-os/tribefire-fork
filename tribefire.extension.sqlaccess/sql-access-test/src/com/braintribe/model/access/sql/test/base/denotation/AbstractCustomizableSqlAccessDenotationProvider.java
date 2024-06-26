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
package com.braintribe.model.access.sql.test.base.denotation;

import com.braintribe.model.access.sql.test.model.SqlAccessTestModel;
import com.braintribe.model.deployment.database.connector.GenericDatabaseConnectionDescriptor;
import com.braintribe.model.deployment.database.pool.DatabaseConnectionPool;
import com.braintribe.model.deployment.database.pool.HikariCpConnectionPool;
import com.braintribe.model.meta.GmMetaModel;

import tribefire.extension.sqlaccess.model.CustomizableSqlAccess;
import tribefire.extension.sqlaccess.model.RdbmsDriver;

/**
 * @author peter.gazdik
 */
public abstract class AbstractCustomizableSqlAccessDenotationProvider {

	public CustomizableSqlAccess customizableSqlAccess() {
		CustomizableSqlAccess result = CustomizableSqlAccess.T.create();
		result.setMetaModel(model());
		result.setDriver(driver());

		return result;
	}

	public RdbmsDriver driver() {
		RdbmsDriver result = RdbmsDriver.T.create();
		result.setDatabaseName(databaseName());
		result.setMetaModel(model());
		result.setConnectionPool(connectionPool());
		result.setEnsureDatabase(false);

		return result;
	}

	protected abstract String databaseName();

	protected abstract DatabaseConnectionPool connectionPool();

	protected GmMetaModel model() {
		return SqlAccessTestModel.raw();
	}

	protected DatabaseConnectionPool singleThreadHikariCpConnectionPool(
			GenericDatabaseConnectionDescriptor connectionDescriptor) {
		HikariCpConnectionPool result = HikariCpConnectionPool.T.create();
		result.setConnectionDescriptor(connectionDescriptor);
		result.setCheckoutTimeout(10 * 1000);
		result.setMinPoolSize(1);
		// result.setInitialPoolSize(1); why not?
		result.setMaxPoolSize(1);

		return result;
	}

}
