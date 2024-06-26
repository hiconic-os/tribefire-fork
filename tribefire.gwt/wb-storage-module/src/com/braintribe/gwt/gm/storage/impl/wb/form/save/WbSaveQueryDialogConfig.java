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
package com.braintribe.gwt.gm.storage.impl.wb.form.save;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gme.workbench.client.Workbench;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class WbSaveQueryDialogConfig {
	private Workbench workbench = null;
	private PersistenceGmSession workbenchSession = null;
	private Future<WbSaveQueryDialogResult> dialogResult = null;

	public PersistenceGmSession getWorkbenchSession() {
		return this.workbenchSession;
	}

	public void setWorkbenchSession(final PersistenceGmSession workbenchSession) {
		this.workbenchSession = workbenchSession;
	}

	public Workbench getWorkbench() {
		return this.workbench;
	}

	public void setWorkbench(final Workbench workbench) {
		this.workbench = workbench;
	}

	public Future<WbSaveQueryDialogResult> getDialogResult() {
		return this.dialogResult;
	}

	public void setDialogResult(final Future<WbSaveQueryDialogResult> dialogResult) {
		this.dialogResult = dialogResult;
	}
}
