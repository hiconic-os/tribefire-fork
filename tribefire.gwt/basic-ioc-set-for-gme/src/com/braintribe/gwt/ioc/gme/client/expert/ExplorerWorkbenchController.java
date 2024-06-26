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

import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gme.propertypanel.client.field.QuickAccessTriggerField;
import com.braintribe.gwt.gme.workbench.client.Workbench;
import com.braintribe.gwt.gme.workbench.client.WorkbenchListenerAdapter;
import com.braintribe.gwt.gmview.action.client.ObjectAndType;
import com.braintribe.gwt.gmview.action.client.SpotlightPanel;
import com.braintribe.gwt.ioc.client.InitializableBean;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.ioc.gme.client.Runtime;
import com.braintribe.model.accessapi.ModelEnvironment;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.workbench.WorkbenchConfiguration;
import com.google.gwt.dom.client.Style.Visibility;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.widget.core.client.SplitBar;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;

import tribefire.extension.js.model.deployment.ViewWithJsUxComponent;

/**
 * This controller is responsible for controlling some of the behavior of the {@link Workbench} used within the {@link ExplorerConstellation}
 * @author michel.docouto
 *
 */
public class ExplorerWorkbenchController implements InitializableBean {
	private ExplorerConstellation explorerConstellation;
	private SpotlightPanel quickAccessPanel;
	private boolean workbenchHidden = false;
	private double lastWorkbenchSize;
	private SplitBar workbenchSplitBar;
	
	/**
	 * Configures the required {@link ExplorerConstellation}, with the Workbench that will be linked to the
	 * {@link SpotlightPanel} configured via {@link #setQuickAccessPanel(SpotlightPanel)}.
	 */
	@Required
	public void setExplorerConstellation(final ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
		
		if (explorerConstellation.isUseWorkbenchWithinTab() || explorerConstellation.getWorkbench() == null)
			return;
		
		Workbench workbench = explorerConstellation.getWorkbench();
		workbench.addWorkbenchListener(new WorkbenchListenerAdapter() {
			@Override
			public void onWorkenchRefreshed(boolean containsData) {
				BorderLayoutData westBorderLayoutData = explorerConstellation.getWestBorderLayoutData();
				if (containsData && workbenchHidden) {
					workbenchHidden = false;
					westBorderLayoutData.setSize(lastWorkbenchSize);
					westBorderLayoutData.setSplit(true);
					workbench.setData("splitBar", workbenchSplitBar);
					workbenchSplitBar.getElement().getStyle().clearVisibility();
					explorerConstellation.doLayout();
				} else if (!containsData && !workbenchHidden) {
					lastWorkbenchSize = westBorderLayoutData.getSize();
					workbenchHidden = true;
					westBorderLayoutData.setSize(0);
					westBorderLayoutData.setSplit(false);
					workbenchSplitBar = explorerConstellation.getSplitBar(LayoutRegion.WEST);
					workbenchSplitBar.getElement().getStyle().setVisibility(Visibility.HIDDEN);
					workbench.setData("splitBar", null);
					explorerConstellation.doLayout();
				} else {
					workbenchSplitBar = explorerConstellation.getSplitBar(LayoutRegion.WEST);
					workbenchSplitBar.getElement().getStyle().clearVisibility();
				}
			}
			
			@Override
			public void onModelEnvironmentChanged(ModelEnvironment modelEnvironment) {
				quickAccessPanel.onModelEnvironmentChanged();
				WorkbenchConfiguration workbenchConfiguration = modelEnvironment.getWorkbenchConfiguration();
				Runtime.useGlobalSearchPanel = workbenchConfiguration == null ? false : workbenchConfiguration.getUseGlobalSearch();
			}
		});
	}
	
	/**
	 * Configures the required {@link SpotlightPanel}, which will have its events listened by the {@link ExplorerConstellation}'s {@link Workbench}.
	 */
	@Required
	public void setQuickAccessPanel(SpotlightPanel quickAccessPanel) {
		this.quickAccessPanel = quickAccessPanel;
	}
	
	/**
	 * Configures the required {@link QuickAccessTriggerField}, which will have its events listened.
	 */
	@Required
	public void setQuickAccessTriggerField(QuickAccessTriggerField quickAccessTriggerField) {
		quickAccessTriggerField.addQuickAccessTriggerFieldListener(result -> {
			if (result != null && isUseServiceRequestPanel(result.getObjectAndType()))
				explorerConstellation.handleServiceRequestPanel(result);
			else
				explorerConstellation.getWorkbench().handleQuickAccessResult(result);
		});
	}
	
	@Override
	public void intializeBean() {
		quickAccessPanel.setLoadExistingValues(false);
		quickAccessPanel.setDisplayValuesSection(false);
		quickAccessPanel.setUseCase(explorerConstellation.getWorkbench() != null ? explorerConstellation.getWorkbench().getUseCase() : "");
	}
	
	private boolean isUseServiceRequestPanel(ObjectAndType objectAndType) {
		if (objectAndType == null)
			return false;
		
		if (!objectAndType.isServiceRequest())
			return false;
		
		EntityType<?> entityType = GMF.getTypeReflection().getEntityType(objectAndType.getType().getTypeSignature());
		if (entityType.isAbstract())
			return false;
		
		ViewWithJsUxComponent viewWithJsUxComponent = explorerConstellation.getTransientGmSession().getModelAccessory().getMetaData().lenient(true)
				.entityType(entityType).meta(ViewWithJsUxComponent.T).exclusive();
		
		return viewWithJsUxComponent == null;
	}

}
