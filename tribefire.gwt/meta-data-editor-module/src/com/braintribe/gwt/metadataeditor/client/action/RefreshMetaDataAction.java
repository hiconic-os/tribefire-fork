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
package com.braintribe.gwt.metadataeditor.client.action;

import java.util.Arrays;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gmview.action.client.resources.GmViewActionResources;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;
import com.braintribe.gwt.gmview.metadata.client.MetaDataEditorPanelHandler;
import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.braintribe.gwt.metadataeditor.client.MetaDataEditorPanel;
import com.braintribe.gwt.metadataeditor.client.experts.DeclaredOverviewExpert;
import com.braintribe.gwt.metadataeditor.client.experts.DeclaredPropertyOverviewExpert;
import com.braintribe.gwt.metadataeditor.client.experts.InformationOverviewExpert;
import com.braintribe.gwt.metadataeditor.client.experts.MetaDataEditorBaseExpert;
import com.braintribe.gwt.metadataeditor.client.view.MetaDataEditorProvider;

/**
* This action provides a MetaData refresh
*
*/
public class RefreshMetaDataAction extends ModelAction {
	
	//private String useCase;
		
	/*
	@Configurable
	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}
	*/
	
	public RefreshMetaDataAction() {
		setName(LocalizedText.INSTANCE.refreshMetaData());
		setIcon(GmViewActionResources.INSTANCE.refresh());
		setHoverIcon(GmViewActionResources.INSTANCE.refreshBig());
		setHidden(true);
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
	}

	@Override
	protected void updateVisibility() {
		if (this.gmContentView instanceof MetaDataEditorPanelHandler) {
			MetaDataEditorProvider metaDataEditorProvider = ((MetaDataEditorPanel) this.gmContentView).getEditorProvider();
			if (metaDataEditorProvider != null) {
				MetaDataEditorBaseExpert metaDataExpert = metaDataEditorProvider.getModelExpert();
				if (metaDataExpert instanceof DeclaredOverviewExpert || metaDataExpert instanceof DeclaredPropertyOverviewExpert || metaDataExpert instanceof InformationOverviewExpert) {
					setHidden(false);
					return;
				}
			}			
		}				
		setHidden(true);
	}
	

	@Override
	public void perform(TriggerInfo triggerInfo) {
		GlobalState.mask(LocalizedText.INSTANCE.refreshing());
		handleSuccess();		
	}	
	
	private void handleSuccess() {
		GlobalState.unmask();
		
		if (this.gmContentView instanceof MetaDataEditorPanelHandler) {
			MetaDataEditorProvider metaDataEditorProvider = ((MetaDataEditorPanel) this.gmContentView).getEditorProvider();
			if (metaDataEditorProvider != null) {
				if (metaDataEditorProvider.getMetaDataEditorPanel() != null)
					metaDataEditorProvider.getMetaDataEditorPanel().doRefresh();
				else
					metaDataEditorProvider.doRefresh();
			}
		}
	}	
}
