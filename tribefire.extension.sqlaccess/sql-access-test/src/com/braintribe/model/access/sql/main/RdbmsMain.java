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
package com.braintribe.model.access.sql.main;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import com.braintribe.model.access.rdbms.deploy.RdbmsDriverDeployer;
import com.braintribe.model.access.sql.SqlAccess;
import com.braintribe.model.access.sql.SqlAccessDriver;
import com.braintribe.model.access.sql.main.assembly.RdbmsAssemblyProvider;
import com.braintribe.model.access.sql.test.base.SqlAccessTestTools;
import com.braintribe.model.deployment.database.pool.HikariCpConnectionPool;
import com.braintribe.utils.FileTools;

import tribefire.extension.sqlaccess.model.RdbmsDriver;

/**
 * @author peter.gazdik
 */
public class RdbmsMain {

	public static void main(String[] args) throws Exception {
		removeDbFolder();

		RdbmsDriver sqlDriverDenotation = RdbmsAssemblyProvider.provide();
		DataSource dataSource = SqlAccessTestTools.deploy((HikariCpConnectionPool) sqlDriverDenotation.getConnectionPool());
		SqlAccessDriver sqlAccessDriver = RdbmsDriverDeployer.deploy(sqlDriverDenotation, dataSource);

		SqlAccess sqlAccess = new SqlAccess();
		sqlAccess.setSqlAccessDriver(sqlAccessDriver);
		
		System.out.println("Rdbms sqlAccessDriver ready to be used:" + sqlAccessDriver);
	}

	private static void removeDbFolder() throws IOException {
		File file = new File("res/main/RdbmsMain");
		if (!file.isDirectory())
			return;

		System.out.println("Deleting folder: " + file.getAbsolutePath());
		FileTools.deleteDirectoryRecursively(file);
	}
	
}
