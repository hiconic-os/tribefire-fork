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
package com.braintribe.model.access.smood.distributed.test;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import com.braintribe.model.access.smood.distributed.test.config.Configurator;
import com.braintribe.model.access.smood.distributed.test.utils.TestUtilities;
import com.braintribe.model.access.smood.distributed.test.wire.contract.DistributedSmoodAccessTestContract;
import com.braintribe.processing.test.db.derby.DerbyServerControl;
import com.braintribe.testing.category.SpecialEnvironment;
import com.braintribe.utils.FileTools;

@Category(SpecialEnvironment.class)
public abstract class TestBase {

	protected static DistributedSmoodAccessTestContract configuration = null;
	protected static Configurator configurator = null;
	protected static TestUtilities testUtilities = null;
	protected static DerbyServerControl derbyServerControl = null;

	@BeforeClass
	public static void initializeDatabase() throws Exception {
		deleteDatabaseFiles();
		
		derbyServerControl = new DerbyServerControl();
		derbyServerControl.start();
		
		configurator = new Configurator();
		configuration =  configurator.getConfiguration();
		testUtilities = configuration.utils();
	}
	@AfterClass
	public static void shutdown() throws Exception {
		
		if (configurator != null) {
			configurator.close();
		}
		
		if (derbyServerControl != null) {
			derbyServerControl.stop();
		}
		deleteDatabaseFiles();
		File logFile = new File("derby.log");
		if (logFile.exists()) {
			logFile.delete();
		}
		
		//Add a sleep interval to allow all threads to terminate in the meantime
		Thread.sleep(2000L);
	}
	
	@Ignore
	protected static void deleteDatabaseFiles() throws IOException {
		File dbFolder = new File("res/db");
		if (dbFolder.exists() && dbFolder.isDirectory()) {
			FileTools.deleteDirectoryRecursively(dbFolder);
		}
	}

}
