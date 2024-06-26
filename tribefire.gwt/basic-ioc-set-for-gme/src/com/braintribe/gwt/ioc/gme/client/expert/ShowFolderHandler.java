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
package com.braintribe.gwt.ioc.gme.client.expert;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelEnvironmentSetListener;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.gwt.notification.client.Notification;
import com.braintribe.gwt.notification.client.NotificationListener;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.workbench.WorkbenchAction;
import com.braintribe.processing.async.api.AsyncCallback;

public class ShowFolderHandler implements NotificationListener<ShowFolderConfig>, ModelEnvironmentSetListener{
	
	private Supplier<? extends Function<WorkbenchActionContext<?>, ModelAction>> workbenchActionHandlerRegistrySupplier;
	private ExplorerConstellation explorerConstellation;
	private PersistenceGmSession gmSession;
	private EntityQuery folderQuery;
	private String folderName;
	boolean modelEnvironmentSet = false;
	
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}
	
	public void setWorkbenchActionHandlerRegistry(Supplier<? extends Function<WorkbenchActionContext<?>, ModelAction>> workbenchActionHandlerRegistrySupplier) {
		this.workbenchActionHandlerRegistrySupplier = workbenchActionHandlerRegistrySupplier;
	}
	
	public void setGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	@Override
	public void onModelEnvironmentSet() {
		modelEnvironmentSet = true;
		tryQuery();
	}
	
	@Override
	public void onNotificationReceived(final Notification<ShowFolderConfig> notification) {
		folderName = notification.getData().getFolderName();
		folderQuery = EntityQueryBuilder.from(Folder.class).where().property("name").eq(folderName).done();	
		tryQuery();
	}
	
	private void tryQuery() {
		if (folderQuery == null || !modelEnvironmentSet)
			return;
		
		GlobalState.mask("Showing folder");
		gmSession.query().entities(folderQuery).result(AsyncCallback.of( //
				future -> {
					GlobalState.unmask();
					if (future == null)
						return;

					Folder folder;
					try {
						folder = future.first();
						if (folder != null && folder.getContent() instanceof WorkbenchAction) {
							ModelAction action = workbenchActionHandlerRegistrySupplier.get().apply(prepareWorkbenchActionContext(folder));
							if (action != null)
								action.perform(null);
						}
					} catch (GmSessionException e) {
						ErrorDialog.show("Error while showing folder " + folderName, e);
					}
				}, e -> {
					GlobalState.unmask();
					ErrorDialog.show("Error while showing folder " + folderName, e);
				}));
	}
	
	private WorkbenchActionContext<WorkbenchAction> prepareWorkbenchActionContext(final Folder folder) {
		return new WorkbenchActionContext<WorkbenchAction>() {
			@Override
			public GmSession getGmSession() {
				return gmSession;
			}

			@Override
			public List<ModelPath> getModelPaths() {
				return Collections.emptyList();
			}

			@Override
			@SuppressWarnings("unusable-by-js")
			public WorkbenchAction getWorkbenchAction() {
				return (WorkbenchAction) folder.getContent();
			}

			@Override
			public Object getPanel() {
				return explorerConstellation;
			}
			
			@Override
			@SuppressWarnings("unusable-by-js")
			public Folder getFolder() {
				return folder;
			}
		};
	}

}
