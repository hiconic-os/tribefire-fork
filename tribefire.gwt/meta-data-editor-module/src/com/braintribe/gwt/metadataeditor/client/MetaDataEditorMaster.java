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
package com.braintribe.gwt.metadataeditor.client;

import java.util.HashSet;
import java.util.Set;

import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.metadataeditor.client.action.MetaDataEditorHistory;
import com.braintribe.gwt.metadataeditor.client.action.MetaDataEditorHistoryEntry;

public class MetaDataEditorMaster {
	
	private final Set<MetaDataEditorPanel> panelList = new HashSet<MetaDataEditorPanel>();
	public void addPanel(MetaDataEditorPanel panel)	{
		if (!panelList.contains(panel))
			panelList.add(panel);
	}
	
	public void removePanel(MetaDataEditorPanel panel) {
		if (panelList.contains(panel))
			panelList.remove(panel);
	}
	
	public void showHistory(MetaDataEditorHistoryEntry entry) {
		for (MetaDataEditorPanel panel : panelList) {
			if (panel.getContentPath().equals(entry.modelPath)) {
				if ((!panel.isVisible() && (entry.tetherBar != null) && (entry.tetherBarElement != null))) {
					entry.tetherBar.setSelectedThetherBarElement(entry.tetherBarElement);
				} else {				
					panel.showTab(entry.tabType);
				}
				return;
			}
		}
	}
	
	public void addHistory(MetaDataEditorHistory history, GmContentView gmView) {
		if (gmView instanceof MetaDataEditorPanel)
			history.add(((MetaDataEditorPanel) gmView).getContentPath(), ((MetaDataEditorPanel) gmView).getActiveEditor().asWidget(), ((MetaDataEditorPanel) gmView).getActiveEditor().getCaption());
			
	}
}
