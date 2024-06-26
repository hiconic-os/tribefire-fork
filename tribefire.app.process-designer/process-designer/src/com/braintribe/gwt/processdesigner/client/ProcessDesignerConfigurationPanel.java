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
package com.braintribe.gwt.processdesigner.client;

import com.braintribe.gwt.gme.propertypanel.client.PropertyPanel;
import com.braintribe.gwt.processdesigner.client.resources.LocalizedText;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.RootPathElement;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;

public class ProcessDesignerConfigurationPanel extends ContentPanel {
	
	private PersistenceGmSession session;
	//private ProcessDesignerConfiguration processDesignerConfiguration;
	private ProcessDesignerRenderer processDesignerRenderer;
	private PropertyPanel propertyPanel;
	private TextButton renderButton;
	
	public ProcessDesignerConfigurationPanel() {
		setHeaderVisible(false);
		setBorders(false);
		setBodyBorder(false);
		setBodyStyle("background: white");
		addButton(getRenderButton());
	}
	
	public void setProcessDesignerRenderer(ProcessDesignerRenderer processDesignerRenderer) {
		this.processDesignerRenderer = processDesignerRenderer;
	}
	
	public void setSession(PersistenceGmSession session) {
		this.session = session;
		propertyPanel.configureGmSession(session);
	}
	
	public void setProcessDesignerConfiguration(ProcessDesignerConfiguration processDesignerConfiguration) {
		//this.processDesignerConfiguration = processDesignerConfiguration;
		
		ModelPath modelPath = new ModelPath();
		modelPath.add(new RootPathElement(ProcessDesignerConfiguration.T, processDesignerConfiguration));
		
		propertyPanel.setContent(modelPath);
		session.listeners().entity(processDesignerConfiguration).add(propertyPanel);
	}
	
	public void setPropertyPanel(PropertyPanel propertyPanel) {
		this.propertyPanel = propertyPanel;
		add(this.propertyPanel);
	}
	
	public TextButton getRenderButton() {
		if(renderButton ==  null){
			renderButton = new TextButton(LocalizedText.INSTANCE.render());
			renderButton.addSelectHandler(event -> processDesignerRenderer.render());
		}
		return renderButton;
	}

}
