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
package com.braintribe.gwt.gme.constellation.client;

import java.util.function.Supplier;

import com.braintribe.gwt.gmview.client.ModelEnvironmentSetListener;
import com.braintribe.gwt.htmlpanel.client.HtmlPanel;
import com.braintribe.gwt.ioc.client.InitializableBean;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;
import com.braintribe.model.workbench.WorkbenchConfiguration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.form.TextField;

public class TopBannerConstellation extends HtmlPanel implements InitializableBean, ModelEnvironmentSetListener {
	
	private Supplier<? extends TextField> quickAccessFieldSupplier;
	private TextField quickAccessField;
	private Widget managerMenu;
	private Label globalStateLabel;
	//private Widget notificationIcon;
	private Supplier<? extends Component> globalSearchPanelSupplier;
	private Component globalSearchPanel;
	private ModelEnvironmentDrivenGmSession gmSession;
	
	public TopBannerConstellation() {
		setBorders(false);
		setBodyBorder(false);
		setStyleName("topBannerHtmlPanel");
		setHtmlSourceUrl(GWT.getModuleBaseURL() + "bt-resources/commons/banner/topBanner.html");
	}
	
	/**
	 * Configures the required {@link TextField} used as the quick access field. Based on the WorkbenchConfiguration,
	 * either this or the component set via {@link #setGlobalSearchPanelSupplier(Supplier)} will be used.
	 */
	@Required
	public void setQuickAccessFieldSupplier(Supplier<? extends TextField> quickAccessFieldSupplier) {
		this.quickAccessFieldSupplier = quickAccessFieldSupplier;
	}
	
	/**
	 * Configures the required GlobalSearchPanel. Based on the WorkbenchConfiguration, either this or the component set via
	 * {@link #setQuickAccessFieldSupplier(Supplier)} will be used.
	 */
	@Required
	public void setGlobalSearchPanelSupplier(Supplier<? extends Component> globalSearchPanelSupplier) {
		this.globalSearchPanelSupplier = globalSearchPanelSupplier;
	}
	
	/**
	 * Configures the required manager menu.
	 */
	@Required
	public void setManagerMenu(Widget managerMenu) {
		this.managerMenu = managerMenu;
	}
	
	/**
	 * Configures the required global state label.
	 */
	@Required
	public void setGlobalStateLabel(Label globalStateLabel) {
		this.globalStateLabel = globalStateLabel;
	}
	
	@Required
	public void setGmSession(ModelEnvironmentDrivenGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	/*
	 * Configures the required notification icon widget.
	 * @param notificationIcon
	 *
	@Required
	public void setNotificationIcon(Widget notificationIcon) {
		this.notificationIcon = notificationIcon;
	}*/
	
	@Override
	public void intializeBean() throws Exception {
		addWidget("settingsMenu-slot", managerMenu);
		addWidget("globalState-slot", globalStateLabel);
		//addWidget("notification-slot", notificationIcon); 

		setWidth(null);
		init();
	}
	
	@Override
	public void onModelEnvironmentSet() {
		WorkbenchConfiguration workbenchConfiguration = gmSession.getModelEnvironment().getWorkbenchConfiguration();
		boolean useGlobalSearchPanel = workbenchConfiguration == null ? false : workbenchConfiguration.getUseGlobalSearch();
		
		if (useGlobalSearchPanel) {
			globalSearchPanel = globalSearchPanelSupplier.get();
			addWidget("quickAccess-slot", globalSearchPanel);
		} else {
			quickAccessField = quickAccessFieldSupplier.get();
			addWidget("quickAccess-slot", quickAccessField);
		}
	}
	
	@Override
	protected void buildLayout(String html) {
		super.buildLayout(html);
		setWidth(null);
	}
	
	public Component getQuickAccessField() {
		return quickAccessField != null ? quickAccessField : globalSearchPanel;
	}
	
	public void enableUI() {
		if (globalSearchPanel != null)
			globalSearchPanel.enable();
		else if (quickAccessField != null)
			quickAccessField.enable();
	}
	
	public void disableUI() {
		if (globalSearchPanel != null)
			globalSearchPanel.disable();
		else if (quickAccessField != null)
			quickAccessField.disable();
	}

}
