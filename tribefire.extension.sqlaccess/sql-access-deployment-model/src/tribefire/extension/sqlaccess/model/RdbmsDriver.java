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
package tribefire.extension.sqlaccess.model;

import com.braintribe.model.deployment.database.pool.DatabaseConnectionPool;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * @author peter.gazdik
 */
public interface RdbmsDriver extends SqlAccessDriver {

	EntityType<RdbmsDriver> T = EntityTypes.T(RdbmsDriver.class);

	String getDatabaseName();
	void setDatabaseName(String value);

	DatabaseConnectionPool getConnectionPool();
	void setConnectionPool(DatabaseConnectionPool databaseConnectionPool);

	/**
	 * Information for the deployment expert whether it should also try to create the database. If set to false
	 * (default), the expert expects the DB to already exist.
	 * 
	 * Creating a DB on demand is convenient, but not every RDBMS supports it.
	 * 
	 * PGA: Let's see if this will make sense later.
	 */
	boolean getEnsureDatabase();
	void setEnsureDatabase(boolean ensureDatabase);

}
