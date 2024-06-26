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
package com.braintribe.devrock.greyface.views.dependency.tabs.capability.selection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.braintribe.devrock.greyface.view.tab.GenericViewTab;
import com.braintribe.devrock.greyface.views.dependency.tabs.capability.AbstractDependencyViewActionContainer;
import com.braintribe.plugin.commons.views.actions.ViewActionContainer;
import com.braintribe.plugin.commons.views.actions.ViewActionController;

public class GlobalSelectionActionContainer extends AbstractDependencyViewActionContainer implements ViewActionContainer<GenericViewTab>, ViewActionController<GenericViewTab> {

	private Action selectAllAction;
	private Action deselectAllAction;
	
	@Override
	public ViewActionController<GenericViewTab> create() {
		ImageDescriptor selectAllImageDescriptor = ImageDescriptor.createFromFile( GlobalSelectionActionContainer.class, "check_selected.png");
		ImageDescriptor deselectAllImageDescriptor = ImageDescriptor.createFromFile( GlobalSelectionActionContainer.class, "check_unselected.png");

		selectAllAction = new Action("select all entries", selectAllImageDescriptor) {		
			@Override
			public void run() {				
				GenericViewTab viewTab = activeTabProvider.provideActiveTab();
				if (viewTab instanceof GlobalSelectionCapable) {
					GlobalSelectionCapable selectionCapable = (GlobalSelectionCapable) viewTab; 
					selectionCapable.selectAll();					
				}
			}
			
		};
		toolbarManager.add(selectAllAction);
		menuManager.add( selectAllAction);
		
		deselectAllAction = new Action("deselect all entries", deselectAllImageDescriptor) {		
			@Override
			public void run() {				
				GenericViewTab viewTab = activeTabProvider.provideActiveTab();
				if (viewTab instanceof GlobalSelectionCapable) {
					GlobalSelectionCapable selectionCapable = (GlobalSelectionCapable) viewTab; 
					selectionCapable.deselectAll();					
				}
			}
			
		};
		toolbarManager.add(deselectAllAction);
		menuManager.add( deselectAllAction);
		
		return this;
	}

	@Override
	public void controlAvailablity(GenericViewTab tab) {
		if (tab instanceof GlobalSelectionCapable) {
			selectAllAction.setEnabled(true);
			deselectAllAction.setEnabled(true);
		}
		else {
			selectAllAction.setEnabled( false);
			deselectAllAction.setEnabled( false);
		}
			
		
	}
	
	

}
