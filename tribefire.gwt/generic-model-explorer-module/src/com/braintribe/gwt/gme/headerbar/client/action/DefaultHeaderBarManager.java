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
package com.braintribe.gwt.gme.headerbar.client.action;

import java.util.function.Supplier;

import com.braintribe.gwt.async.client.AsyncCallbacks;
import com.braintribe.gwt.async.client.Loader;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.logging.client.Profiling;
import com.braintribe.gwt.logging.client.ProfilingHandle;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 *
 */
public class DefaultHeaderBarManager implements Loader<Void> {
	
	private ModelEnvironmentDrivenGmSession gmSession;
	private ModelEnvironmentDrivenGmSession workbenchSession;
	private Supplier<? extends HeaderBar> headerBarSupplier;
	private Loader<Folder> folderLoader;
	
	public DefaultHeaderBarManager() {
	}
	
	/**
	 * Configures the required {@link ModelEnvironmentDrivenGmSession} used within the actions.
	 */
	@Required
	public void setPersistenceSession(ModelEnvironmentDrivenGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	/**
	 * Configures the workbench session used for preparing the {@link HeaderBar} given via {@link #setHeaderBar(Supplier)}.
	 */
	@Required
	public void setWorkbenchSession(ModelEnvironmentDrivenGmSession workbenchSession) {
		this.workbenchSession = workbenchSession;
	}
	
	/**
	 * Configures the required {@link HeaderBar}.
	 */
	@Required
	public void setHeaderBar(Supplier<? extends HeaderBar> headerBarSupplier) {
		this.headerBarSupplier = headerBarSupplier;
	}
	
	/**
	 * Configures the required loader for the header bar folder.
	 */
	@Required
	public void setFolderLoader(Loader<Folder> folderLoader) {
		this.folderLoader = folderLoader;
	}
	
	@Override
	public void load(final AsyncCallback<Void> asyncCallback) {
		final ProfilingHandle ph = Profiling.start(DefaultHeaderBarManager.class, "Loading headerBar folders", true);
		HeaderBar headerBar = headerBarSupplier.get();
		headerBar.configureGmSession(gmSession);
		headerBar.setWorkbenchSession(workbenchSession);
		folderLoader.load(AsyncCallbacks.of(result -> {
			headerBar.apply(result);
			ph.stop();
			asyncCallback.onSuccess(null);
		}, e -> {
			e.printStackTrace();
			headerBar.apply(null);
			ph.stop();
			asyncCallback.onSuccess(null);
		}));
	}
	
}
