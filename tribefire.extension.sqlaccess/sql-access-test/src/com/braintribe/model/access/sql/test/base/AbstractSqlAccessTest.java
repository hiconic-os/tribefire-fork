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
package com.braintribe.model.access.sql.test.base;

import java.io.File;
import java.sql.SQLException;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.junit.Before;

import com.braintribe.model.access.rdbms.deploy.RdbmsDriverDeployer;
import com.braintribe.model.access.sql.SqlAccess;
import com.braintribe.model.access.sql.SqlAccessDriver;
import com.braintribe.model.access.sql.test.base.denotation.DerbyBasedSqlAccessProvider;
import com.braintribe.model.access.sql.test.base.javax.DelegatingDataSource;
import com.braintribe.model.access.sql.test.base.javax.NonCommittingDataSource;
import com.braintribe.model.deployment.database.pool.HikariCpConnectionPool;
import com.braintribe.model.generic.reflection.GenericModelException;
import com.braintribe.utils.FileTools;

import tribefire.extension.sqlaccess.model.RdbmsDriver;

/**
 * @author peter.gazdik
 */
public abstract class AbstractSqlAccessTest {

	protected static String ACCESS_ID = "sql.test.access";
	protected static boolean PREVENT_COMMITS = false;
	protected static Supplier<? extends RdbmsDriver> DEFAULT_DRIVER_DENOTATION_PROVIDER = DerbyBasedSqlAccessProvider::newDriver;

	protected static SqlAccess staticAccess;
	protected static DataSource dataSource;

	protected SqlAccess access;

	@Before
	public void beforeSqlTest() throws Exception {
		clearJunitTestFolderIfNeeded();
		prepareSqlAccess();
	}

	private static void clearJunitTestFolderIfNeeded() throws Exception {
		if (staticAccess != null)
			// we have already deployed an access, thus also already cleared the folder before
			return;

		File file = new File("res/junit");
		if (!file.exists())
			return;

		String filePath = file.getAbsolutePath();
		if (!file.isDirectory())
			throw new RuntimeException("Cannot clear test folder as it is actually a file: " + filePath);

		FileTools.deleteDirectoryRecursively(file);
	}

	private void prepareSqlAccess() {
		if (staticAccess != null)
			rollbackPreviousTest();
		else
			deployStaticAccess();

		access = staticAccess;
	}

	private void rollbackPreviousTest() {
		try {
			if (PREVENT_COMMITS)
				dataSource.getConnection().rollback();
		} catch (SQLException e) {
			throw new GenericModelException("Rollback of transaction from previous test failed.", e);
		}
	}

	private void deployStaticAccess() {
		RdbmsDriver driverDenotation = driverDenotationSupplier().get();
		DataSource actualDataSource = SqlAccessTestTools.deploy((HikariCpConnectionPool) driverDenotation.getConnectionPool());
		DelegatingDataSource delegatingDataSource = new DelegatingDataSource(actualDataSource);

		// Ensuring DB schema - will be committed for all tests to have it
		SqlAccessDriver driver = RdbmsDriverDeployer.deploy(driverDenotation, delegatingDataSource);

		// now that the schema is ensured, we might switch to a non-committing DS if configured
		if (PREVENT_COMMITS) {
			NonCommittingDataSource nonCommittingDataSource = new NonCommittingDataSource(actualDataSource);
			delegatingDataSource.setDelegate(nonCommittingDataSource);
			dataSource = nonCommittingDataSource;

		} else {
			dataSource = actualDataSource;
		}

		staticAccess = new SqlAccess();
		staticAccess.setAccessId(ACCESS_ID);
		staticAccess.setSqlAccessDriver(driver);
	}

	protected Supplier<? extends RdbmsDriver> driverDenotationSupplier() {
		return DEFAULT_DRIVER_DENOTATION_PROVIDER;
	}

}
