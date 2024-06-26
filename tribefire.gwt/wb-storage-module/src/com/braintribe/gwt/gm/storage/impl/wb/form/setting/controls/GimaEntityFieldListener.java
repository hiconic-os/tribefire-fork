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
package com.braintribe.gwt.gm.storage.impl.wb.form.setting.controls;

import java.util.function.Supplier;

import com.braintribe.gwt.gm.storage.impl.wb.WbStorageRuntimeException;
import com.braintribe.gwt.gme.constellation.client.GIMADialog;
import com.braintribe.gwt.qc.api.client.EntityFieldListener;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.RootPathElement;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class GimaEntityFieldListener implements EntityFieldListener {
	private Supplier<GIMADialog> gimaDialogProvider = null;

	public GimaEntityFieldListener(final Supplier<GIMADialog> gimaDialogProvider) {
		this.gimaDialogProvider = gimaDialogProvider;
	}

	protected void displayGIMA(final PersistenceGmSession workbenchSession, final GenericEntity entity, final AsyncCallback<Boolean> callback) {
		try {
			// Provide GIMA and set our session to it
			final GIMADialog gimaDialog = this.gimaDialogProvider.get();
			gimaDialog.setGmSession(workbenchSession);

			// Create model path for the criterion
			final ModelPath modelPath = new ModelPath();
			modelPath.add(new RootPathElement(entity.entityType(), entity));

			// Display GIMA for the defined TraversingCriterion
			gimaDialog.showForModelPathElement(modelPath).get(callback);
		} catch (final Exception e) {
			// Throw GIMADialog providing exception
			throw new WbStorageRuntimeException("Error while providing GIMADialog.", e);
		}
	}
}
