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
package com.braintribe.devrock.artifactcontainer.views.dependency.tabs.capability.quickimport;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.braintribe.devrock.artifactcontainer.views.dependency.tabs.AbstractDependencyViewTab;
import com.braintribe.devrock.artifactcontainer.views.dependency.tabs.capability.AbstractDependencyViewActionContainer;
import com.braintribe.plugin.commons.views.actions.ViewActionContainer;
import com.braintribe.plugin.commons.views.actions.ViewActionController;

public class QuickImportActionContainer extends AbstractDependencyViewActionContainer implements ViewActionContainer<AbstractDependencyViewTab>, ViewActionController<AbstractDependencyViewTab> {
	private Action quickImportAction;

	@Override
	public ViewActionController<AbstractDependencyViewTab> create() {
		ImageDescriptor quickimportImageDescriptor = ImageDescriptor.createFromFile( QuickImportCapable.class, "quickImport.png");
		quickImportAction = new Action("Import dependency via QuickImport", quickimportImageDescriptor) {

			@Override
			public void run() {				
				AbstractDependencyViewTab viewTab = activeTabProvider.provideActiveTab();
				if (viewTab instanceof QuickImportCapable) {
					QuickImportCapable loadingTab = (QuickImportCapable) viewTab;
					loadingTab.quickImportArtifact();
				}
			}			
		};
		toolbarManager.add(quickImportAction);
		menuManager.add(quickImportAction);
				
		return this;
	}

	@Override
	public void controlAvailablity(AbstractDependencyViewTab tab) {
		if (tab instanceof QuickImportCapable) {
			quickImportAction.setEnabled(true);
		}
		else {
			quickImportAction.setEnabled(false);
		}		
	}

	
}
