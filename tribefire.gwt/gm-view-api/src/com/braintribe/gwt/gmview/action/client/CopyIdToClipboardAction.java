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
package com.braintribe.gwt.gmview.action.client;
// ============================================================================
// BRAINTRIBE TECHNOLOGY GMBH - www.braintribe.com
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2018 - All Rights Reserved
// It is strictly forbidden to copy, modify, distribute or use this code without written permission
// To this file the Braintribe License Agreement applies.
// ============================================================================

import java.util.Arrays;
import java.util.List;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gmview.action.client.resources.GmViewActionResources;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.path.ModelPath;

/**
 * Action that will copy entity Id to the clipboard as a string. On multiselection the Ids are comma separated
 *
 */
public class CopyIdToClipboardAction extends ModelAction  {

	public CopyIdToClipboardAction() {
		setHidden(true);
		setName(LocalizedText.INSTANCE.copyId());
		setIcon(GmViewActionResources.INSTANCE.clipboardBig());
		setHoverIcon(GmViewActionResources.INSTANCE.clipboardBig());
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
	}
	
	@Override
	protected void updateVisibility() {
		if (modelPaths == null || modelPaths.isEmpty()) {
			setHidden(true, true);
			return;
		}
		
		boolean hidden = true;
		for (List<ModelPath> selection : modelPaths) {
			for (ModelPath modelPath : selection) {
				if (modelPath == null)
					continue;
				
				Object value = modelPath.last().getValue();
				if (value != null && value instanceof GenericEntity) {
					hidden = false;
					break;
				}				
			}
			if (!hidden)
				break;
		}		
		
		setHidden(hidden);		
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		if (modelPaths == null || modelPaths.isEmpty())
			return;
		
		String idString = "";
		
		for (List<ModelPath> selection : modelPaths) {
			for (ModelPath modelPath : selection) {
				if (modelPath == null)
					continue;
				
				Object value = modelPath.last().getValue();
				if (value != null && value instanceof GenericEntity) {
					if (!idString.isEmpty())
						idString = idString + ",";
					idString = idString + ((GenericEntity) value).getId();
				}				
			}
		}
		
		ClipboardUtil.copyTextToClipboard(idString);
		GlobalState.showSuccess(LocalizedText.INSTANCE.entityIdCopiedClipboard(idString));
	}
}
