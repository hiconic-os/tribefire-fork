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
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;
import com.braintribe.gwt.gmview.metadata.client.MetaDataEditorPanelHandler;
import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.braintribe.gwt.metadataeditor.client.resources.MetaDataEditorResources;

@SuppressWarnings("unusable-by-js")
public class NextMetaDataAction extends ModelAction {

	private MetaDataEditorHistory history = null;

	public NextMetaDataAction() {
		setHidden(true);
		setName(LocalizedText.INSTANCE.next());
		setIcon(MetaDataEditorResources.INSTANCE.next2());
		setHoverIcon(MetaDataEditorResources.INSTANCE.next2());
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
	}

	public void setMetaDataHistory(MetaDataEditorHistory history) {
		 this.history = history;
	}
	
	@Override
	public void configureGmContentView(GmContentView gmContentView) {
		this.gmContentView = gmContentView;
	}
	
	@Override
	protected void updateVisibility() {
		boolean useHidden = true;
		if (gmContentView instanceof MetaDataEditorPanelHandler && history.hasNext())
			useHidden = false;
		
		setHidden(useHidden);
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		history.next();		
	}

}
