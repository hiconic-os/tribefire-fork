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
package com.braintribe.gwt.gme.assemblypanel.client.action;

import java.util.List;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gme.assemblypanel.client.AssemblyPanel;
import com.braintribe.gwt.gme.assemblypanel.client.LocalizedText;
import com.braintribe.gwt.gme.assemblypanel.client.resources.AssemblyPanelResources;
import com.braintribe.gwt.gmview.client.GmAmbiguousSelectionSupport;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.path.MapValuePathElement;
import com.braintribe.model.generic.path.ModelPath;

/**
 * Action used for adding the selected entity to the clipboard.
 *
 */
public class CopyEntityToClipboardAction extends ModelAction {
	
	private AssemblyPanel assemblyPanel;
	
	/**
	 * Configures the required {@link AssemblyPanel}.
	 */
	@Required
	public void setAssemblyPanel(AssemblyPanel assemblyPanel) {
		this.assemblyPanel = assemblyPanel;
		assemblyPanel.addSelectionListener(gmSelectionSupport -> {
			List<List<ModelPath>> modelPaths = gmSelectionSupport instanceof GmAmbiguousSelectionSupport
					? ((GmAmbiguousSelectionSupport) gmSelectionSupport).getAmbiguousSelection()
					: gmSelectionSupport.transformSelection(gmSelectionSupport.getCurrentSelection());
			updateState(modelPaths);
		});
	}
	
	public CopyEntityToClipboardAction() {
		setName(LocalizedText.INSTANCE.toClipboard());
		setTooltip(LocalizedText.INSTANCE.toClipboardDescription());
		setIcon(AssemblyPanelResources.INSTANCE.clipboard());
		setHidden(true);
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		assemblyPanel.addSelectedModelsToClipBoard();
	}

	@Override
	protected void updateVisibility() {
		setHidden(true);
		if (modelPaths == null)
			return;
		
		for (List<ModelPath> singleSelection : modelPaths) {
			boolean entityFound = false;
			for (ModelPath modelPath : singleSelection) {
				if (modelPath.last().getValue() instanceof GenericEntity) {
					if (modelPath.last() instanceof MapValuePathElement
							&& !(((MapValuePathElement) modelPath.last()).getKey() instanceof GenericEntity)) {
						continue;
					}
					entityFound = true;
					break;
				}
			}
			
			if (!entityFound)
				return;
		}
		
		setHidden(false);
	}

}
