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
package com.braintribe.gwt.accessdeploymentmodeluisupport.client;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gmview.action.client.resources.GmViewActionResources;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.extensiondeployment.WebTerminal;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.tracking.ManipulationListener;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

public class OpenGmeForWebTerminalInNewTabAction extends ModelAction {
	private String clientUrl;
	private WebTerminal terminal;
	private PersistenceGmSession gmSession;
	private ManipulationListener manipulationListener;
	private Supplier<String> servicesUrlSupplier;
	
	public OpenGmeForWebTerminalInNewTabAction() {
		setName(LocalizedText.INSTANCE.switchTo());
		setIcon(GmViewActionResources.INSTANCE.globe());
		setHoverIcon(GmViewActionResources.INSTANCE.globeBig());
		setHidden(true);
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
		
		manipulationListener = manipulation -> Scheduler.get().scheduleDeferred(this::updateVisibility);
	}
		
	@Configurable
	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}
	
	/**
	 * Configures the session used for listening for manipulations.
	 */
	@Configurable
	public void setGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	/**
	 * Configures the required supplier for the services URL.
	 */
	@Required
	public void setServicesUrlSupplier(Supplier<String> servicesUrlSupplier) {
		this.servicesUrlSupplier = servicesUrlSupplier;
	}
	
	public String getClientUrl() {
		if (clientUrl == null) {
			String href = Location.getHref();
			int index = href.indexOf("?");
			clientUrl = index != -1 ? href.substring(0, index) : href;
		}
		return clientUrl;
	}
	
	@Override
	protected void updateVisibility() {
		setHidden(true);
		
		if (terminal != null && gmSession != null)
			gmSession.listeners().entity(terminal).remove(manipulationListener);
		
		terminal = null;
		
		if (this.modelPaths == null || this.modelPaths.size() != 1)
			return;
		
		List<ModelPath> selection = modelPaths.get(0);
		if (selection.isEmpty())
			return;
		
		ModelPath modelPath = selection.get(selection.size() - 1);
		if (modelPath != null && !modelPath.isEmpty() && modelPath.last().getValue() instanceof WebTerminal) {
			terminal = modelPath.last().getValue();
			if (gmSession != null)
				gmSession.listeners().entity(terminal).add(manipulationListener);
			
			//TODO: originally checked for getDeployed() -> if this check actually needs to be aware of the deployment state an according DDSA request needs to be performed
			if (terminal.getAutoDeploy() && terminal.getExternalId() != null && !terminal.getExternalId().isEmpty()) {
				setHidden(false);
				return;
			}
		}
		setHidden(true);
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {		
		if (terminal != null) {
			String url = servicesUrlSupplier.get();
			if (!url.endsWith("/"))
				url = url + "/";
			
			url = url + "component/";
			url = url + URL.encode(terminal.getPathIdentifier());
			Window.open(url,"_blank","");
		}		
	}	

}
