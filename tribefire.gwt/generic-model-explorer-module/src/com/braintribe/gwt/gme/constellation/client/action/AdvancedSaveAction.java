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
package com.braintribe.gwt.gme.constellation.client.action;

import java.util.function.Supplier;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gme.constellation.client.LocalizedText;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.braintribe.gwt.gmview.client.ModelEnvironmentSetListener;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.meta.data.ui.ShowAdvancedCommit;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;

/**
 * Action that will present the Advanced Save Dialog.
 * @author michel.docouto
 *
 */
public class AdvancedSaveAction extends Action implements ModelEnvironmentSetListener {
	
	private Supplier<AdvancedSaveActionDialog> advancedSaveDialogSupplier;
	private AdvancedSaveActionDialog advancedSaveDialog;
	private ModelEnvironmentDrivenGmSession gmSession;
	private Action settingsAdvancedSaveAction;
	private boolean platformSetupSupported;
	
	/**
	 * Configures the required dialog for the advanced save.
	 */
	@Required
	public void setAdvancedSaveDialogSupplier(Supplier<AdvancedSaveActionDialog> advancedSaveDialogSupplier) {
		this.advancedSaveDialogSupplier = advancedSaveDialogSupplier;
	}
	
	/**
	 * Configures the session used for getting the model environment.
	 */
	@Required
	public void setGmSession(ModelEnvironmentDrivenGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	/**
	 * Configures the action which is displayed in the settings, and will have its visibility updated here.
	 */
	@Required
	public void setSettingsAdvancedSaveAction(Action settingsAdvancedSaveAction) {
		this.settingsAdvancedSaveAction = settingsAdvancedSaveAction;
	}
	
	/**
	 * Configures whether the platform setup is supported within GME. If true, then both the settings action and PFA Management section within the advanced dialog
	 * are shown. If false, the settings action is hidden and also is the PFA Management section within the advanced dialog.
	 */
	@Required
	public void setPlatformSetupSupported(boolean platformSetupSupported) {
		this.platformSetupSupported = platformSetupSupported;
	}
	
	public AdvancedSaveAction() {
		setName(LocalizedText.INSTANCE.advanced());
		setIcon(ConstellationResources.INSTANCE.saveSmall());
		setHoverIcon(ConstellationResources.INSTANCE.saveSmall());
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		if (advancedSaveDialog == null)
			advancedSaveDialog = advancedSaveDialogSupplier.get();
		
		advancedSaveDialog.show();
	}
	
	@Override
	public void onModelEnvironmentSet() {
		boolean showAdvancedCommit = gmSession.getModelAccessory().getCmdResolver().getMetaData().is(ShowAdvancedCommit.T);
		setEnabled(showAdvancedCommit);
		settingsAdvancedSaveAction.setHidden(!platformSetupSupported);
		
		if (showAdvancedCommit) {
			if (advancedSaveDialog == null)
				advancedSaveDialog = advancedSaveDialogSupplier.get();
			if (platformSetupSupported)
				advancedSaveDialog.configureAccessId(gmSession.getAccessId());
			advancedSaveDialog.configureAdvancedSetupSupport(platformSetupSupported);
		}
	}

}
