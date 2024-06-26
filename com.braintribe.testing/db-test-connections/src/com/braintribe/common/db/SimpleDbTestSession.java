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

import java.io.File;

import javax.sql.DataSource;

import com.braintribe.common.db.wire.DbTestConnectionsWireModule;
import com.braintribe.common.db.wire.contract.DbTestDataSourcesContract;
import com.braintribe.utils.FileTools;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;

/**
 * Represents an access point to the {@link DbTestDataSourcesContract}, i.e. the contract which provides the {@link DataSource} instances for all the
 * supported {@link DbVendor vendors}.
 * <p>
 * Typically this test-session is acquired via {@link #startDbTest()}, and stored in a static field. It is then cleaned-up via
 * {@link #shutdownDbTest()}.
 * <p>
 * For an example see <tt>AbstractGmDbTestBase</tt> in <tt>jdbc-gm-support-test</tt> artifact.
 *
 * @author peter.gazdik
 */
public class SimpleDbTestSession {

	public WireContext<DbTestDataSourcesContract> context;
	public DbTestDataSourcesContract contract;

	private SimpleDbTestSession() {
		DbTestSupport.startDerby();
		context = Wire.context(DbTestConnectionsWireModule.INSTANCE);
		contract = context.contract();
	}

	public static SimpleDbTestSession startDbTest() {
		deleteResFolderWithDerbyData();

		return new SimpleDbTestSession();
	}

	/** @see DbTestConstants#derbyUrl */
	private static void deleteResFolderWithDerbyData() {
		File res = new File("res/db/dbtest");
		if (res.exists()) {
			FileTools.deleteDirectoryRecursivelyUnchecked(res);
		}
	}

	public void shutdownDbTest() throws Exception {
		if (context != null) {
			context.shutdown();
		}

		DbTestSupport.shutdownDerby();
	}

}
