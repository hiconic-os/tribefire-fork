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
package tribefire.extension.vitals.jdbc.jdbc_dcsa_storage.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.api.ResourceBuilder;

import tribefire.extension.jdbc.gmdb.dcsa.TemporaryJdbc2GmDbSharedStorage;
import tribefire.extension.vitals.jdbc.model.migration.SharedStorageState;
import tribefire.extension.vitals.jdbc.model.migration.SharedStorageStatus;

/**
 * @author peter.gazdik
 */
public class SharedStorageStatusReporter {

	private final String implementation;
	private final ResourceBuilder resourceBuilder;

	private Jdbc2GmDbUpgrader upgrader;

	private Date date = new Date();
	private SharedStorageStatus status = SharedStorageStatus.NORMAL;
	private Resource statusDetail;

	public SharedStorageStatusReporter(TemporaryJdbc2GmDbSharedStorage sharedStorage, ResourceBuilder resourceBuilder) {
		this.resourceBuilder = resourceBuilder;
		this.implementation = sharedStorage.isGmDbImplementation() ? "GmDb (New)" : "JDBC (Old)";
	}

	public void onStartUpgrade(Jdbc2GmDbUpgrader upgrader) {
		this.upgrader = upgrader;
		this.date = new Date();
		this.status = SharedStorageStatus.UPGRADING;
	}

	public void onStartDowngrade() {
		this.upgrader = null;
		this.date = new Date();
		this.status = SharedStorageStatus.DOWNGRADED;
	}

	public void onActionFinished(SharedStorageStatus status, Exception e) {
		this.status = status;
		this.statusDetail = e == null ? null : printStackTrace(e);
	}

	private Resource printStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return errorResource(sw.toString());
	}

	public Resource errorResource(String errorText) {
		return resourceBuilder.newResource() //
				.withName("DCSA migration error.txt") //
				.withMimeType("plain/text") //
				.string(errorText);
	}

	public SharedStorageState getState() {
		SharedStorageState state = SharedStorageState.T.create();
		state.setDate(date);
		state.setImplementation(implementation);
		state.setStatus(status);
		state.setDetails(statusDetail);

		if (upgrader != null)
			state.setMigratedAccesses(upgrader.migratedAccesses());

		return state;
	}

}
