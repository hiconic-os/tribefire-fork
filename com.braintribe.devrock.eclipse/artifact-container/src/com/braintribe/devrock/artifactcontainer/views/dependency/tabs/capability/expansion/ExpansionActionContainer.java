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
package com.braintribe.devrock.artifactcontainer.views.dependency.tabs.capability.expansion;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.braintribe.devrock.artifactcontainer.views.dependency.tabs.AbstractDependencyViewTab;
import com.braintribe.devrock.artifactcontainer.views.dependency.tabs.capability.AbstractDependencyViewActionContainer;
import com.braintribe.plugin.commons.views.actions.ViewActionContainer;
import com.braintribe.plugin.commons.views.actions.ViewActionController;

/**
 * actions around expansion / condensation 
 * @author pit
 *
 */
public class ExpansionActionContainer extends AbstractDependencyViewActionContainer implements ViewActionContainer<AbstractDependencyViewTab>, ViewActionController<AbstractDependencyViewTab> {

	private Action condenseDisplayAction;
	private Action expandDisplayAction;
	
	@Override
	public ViewActionController<AbstractDependencyViewTab> create() {
		ImageDescriptor expandAllImageDescriptor = ImageDescriptor.createFromFile( ViewExpansionCapable.class, "expand_all.gif");
		ImageDescriptor collapseAllImageDescriptor = ImageDescriptor.createFromFile( ViewExpansionCapable.class, "collapse_all.gif");

		expandDisplayAction = new Action("Expand display", expandAllImageDescriptor) {		
			@Override
			public void run() {				
				AbstractDependencyViewTab viewTab = activeTabProvider.provideActiveTab();
				if (viewTab instanceof ViewExpansionCapable) {
					ViewExpansionCapable vec = (ViewExpansionCapable)viewTab; 
					vec.expand();
					expandDisplayAction.setEnabled( vec.isCondensed());
					condenseDisplayAction.setEnabled( !vec.isCondensed());
				}
			}
			
		};
		menuManager.add( expandDisplayAction);
		// condense dispay 
		condenseDisplayAction = new Action("Condense display", collapseAllImageDescriptor) {		
			@Override
			public void run() {				
				AbstractDependencyViewTab viewTab = activeTabProvider.provideActiveTab(); 
				if (viewTab instanceof ViewExpansionCapable) {
					ViewExpansionCapable vec = (ViewExpansionCapable)viewTab; 
					vec.condense();
					expandDisplayAction.setEnabled( vec.isCondensed());
					condenseDisplayAction.setEnabled( !vec.isCondensed());
				}
			}					
		};
		menuManager.add( condenseDisplayAction);
		return this;
	}

	@Override
	public void controlAvailablity(AbstractDependencyViewTab tab) {
		if (tab instanceof ViewExpansionCapable && tab.getItemCount() < 100) {
			boolean isCondensed = ((ViewExpansionCapable) tab).isCondensed();
			expandDisplayAction.setEnabled( isCondensed);
			condenseDisplayAction.setEnabled( !isCondensed);			
		} else {
			expandDisplayAction.setEnabled( false);
			condenseDisplayAction.setEnabled( false);			
		}
	}	
}
