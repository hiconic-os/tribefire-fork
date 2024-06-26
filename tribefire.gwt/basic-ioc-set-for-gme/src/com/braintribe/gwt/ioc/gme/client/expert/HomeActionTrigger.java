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

import com.braintribe.gwt.gme.constellation.client.HomeConstellation;
import com.braintribe.gwt.gmview.client.GmInteractionListener;
import com.braintribe.gwt.gmview.client.GmMouseInteractionEvent;
import com.braintribe.gwt.gmview.client.GmSelectionListener;
import com.braintribe.gwt.gmview.client.GmSelectionSupport;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.workbench.WorkbenchAction;

/**
 * Expert for handling the triggering of the {@link HomeConstellation}.
 */
public class HomeActionTrigger implements GmSelectionListener, GmInteractionListener {
	
	private Function<WorkbenchActionContext<?>, ModelAction> workbenchActionHandlerRegistry;
	private ModelEnvironmentDrivenGmSession gmSession;
	private Object parentPanel;
	private boolean triggerOnDblClick = false;
	private HomeConstellation homeConstellation;

	/**
	 * Configures the required action handler registry.
	 */
	@Required
	public void setWorkbenchActionHandlerRegistry(Function<WorkbenchActionContext<?>, ModelAction> workbenchActionHandlerRegistry) {
		this.workbenchActionHandlerRegistry = workbenchActionHandlerRegistry;
	}
	
	/**
	 * Configures the session which is set into the {@link WorkbenchActionContext}.
	 */
	@Required
	public void setGmSession(ModelEnvironmentDrivenGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	/**
	 * Configures the panel which is set into the {@link WorkbenchActionContext}.
	 */
	@Required
	public void setParentPanel(Object parentPanel) {
		this.parentPanel = parentPanel;
	}
	
	/**
	 * Configures the {@link HomeConstellation}.
	 */
	@Configurable
	public void setHomeConstellation(HomeConstellation homeConstellation) {
		this.homeConstellation = homeConstellation;
	}

	/**
	 * Configures if we should trigger the action on double click instead of a single click.
	 * Defaults to false.
	 */
	@Configurable
	public void setTriggerOnDblClick(boolean triggerOnDblClick) {
		this.triggerOnDblClick = triggerOnDblClick;
	}
	
	private WorkbenchActionContext<WorkbenchAction> prepareWorkbenchActionContext(Folder folder) {
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
				return parentPanel;
			}
			
			@Override
			@SuppressWarnings("unusable-by-js")
			public Folder getFolder() {
				return folder;
			}
		};
	}

	@Override
	public void onClick(GmMouseInteractionEvent event) {
		if (triggerOnDblClick)
			return;
		
		ModelPath modelPath = event.getSource().getFirstSelectedItem();
		if (modelPath != null) {
			Folder folder = modelPath.get(0).getValue();
			triggerAction(folder);
		}
	}

	@Override
	public void onDblClick(GmMouseInteractionEvent event) {
		if (!triggerOnDblClick)
			return;
		
		Folder folder = event.getSource().getFirstSelectedItem().get(0).getValue();	
		triggerAction(folder);
	}
	
	private void triggerAction(Folder folder) {
		if (folder == null)
			return;
		
		if (!(folder.getContent() instanceof WorkbenchAction)) {
			if (homeConstellation == null)
				return;
			
			homeConstellation.createTetherForFolder(folder);
			return;
		}
		
		ModelAction action = workbenchActionHandlerRegistry.apply(prepareWorkbenchActionContext(folder));
		if (action != null)
			action.perform(null);
	}

	@Override
	public boolean onBeforeExpand(GmMouseInteractionEvent event) {
		return false;
	}
	
	@Override
	public void onSelectionChanged(GmSelectionSupport gmSelectionSupport) {
		Folder folder = gmSelectionSupport.getFirstSelectedItem().get(0).getValue();
		triggerAction(folder);
	}

}
