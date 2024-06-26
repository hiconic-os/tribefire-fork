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
package com.braintribe.gwt.gmview.actionbar.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.async.client.Loader;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.accessapi.ModelEnvironment;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.workbench.KnownWorkenchPerspective;
import com.braintribe.model.workbench.WorkbenchPerspective;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Loader used for loading folders from the {@link ModelEnvironment} in the configured session.
 * @author michel.docouto
 *
 */
public class ModelEnvironmentFolderLoader implements Loader<Folder> {
	
	private Function<KnownWorkenchPerspective, Future<WorkbenchPerspective>> workbenchPerspectiveFutureProvider;
	private KnownWorkenchPerspective knownWorkbenchPerspective;
	private static Set<Folder> emptyFoldersParentSet;
	
	/**
	 * Configures the required provider used for providing the {@link WorkbenchPerspective} for the home folders.
	 */
	@Required
	public void setWorkbenchPerspectiveFutureProvider(Function<KnownWorkenchPerspective, Future<WorkbenchPerspective>> workbenchPerspectiveFutureProvider) {
		this.workbenchPerspectiveFutureProvider = workbenchPerspectiveFutureProvider;
	}
	
	/**
	 * Configures the {@link KnownWorkenchPerspective} used for loading the folder data.
	 */
	@Required
	public void setKnownWorkbenchPerspective(KnownWorkenchPerspective knownWorkbenchPerspective) {
		this.knownWorkbenchPerspective = knownWorkbenchPerspective;
	}
	
	@Override
	public void load(AsyncCallback<Folder> asyncCallback) {
		workbenchPerspectiveFutureProvider.apply(knownWorkbenchPerspective) //
				.andThen(result -> {
					Folder folder;
					if (result != null) {
						List<Folder> folders = result.getFolders();
						folder = folders.isEmpty() ? null : folders.get(0);
					} else
						folder = null;

					if (knownWorkbenchPerspective == KnownWorkenchPerspective.actionBar && folder != null)
						handleActionBarPerspective(folder);
					asyncCallback.onSuccess(folder);
				}).onError(asyncCallback::onFailure);
	}
	
	private void handleActionBarPerspective(Folder folder) {
		if (emptyFoldersParentSet == null) {
			emptyFoldersParentSet = new HashSet<>();
			emptyFoldersParentSet.add(folder);
		} else if (!emptyFoldersParentSet.add(folder))
			return;
		
		Folder emptyFolder = Folder.T.create();
		emptyFolder.setName("$Not Known");
		folder.detach();
		folder.getSubFolders().add(emptyFolder);
	}
	
}
