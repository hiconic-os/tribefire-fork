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
package com.braintribe.devrock.dmb.commands;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.braintribe.devrock.api.nature.NatureHelper;
import com.braintribe.devrock.api.selection.SelectionExtracter;
import com.braintribe.devrock.api.ui.commons.UiSupport;
import com.braintribe.devrock.artifactcontainer.natures.TribefireServicesNature;
import com.braintribe.devrock.dmb.plugin.DebugModuleBuilderPlugin;
import com.braintribe.devrock.dmb.plugin.DebugModuleBuilderStatus;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.logging.Logger;


/**
 * dynamic command to remove the debug-module nature
 * 
 * @author pit
 *
 */
public class DynamicProjectRemoveDebugModuleNatureCommandItem extends ContributionItem {
	private static Logger log = Logger.getLogger(DynamicProjectRemoveDebugModuleNatureCommandItem.class);
	private Image image;
	private UiSupport uisupport = DebugModuleBuilderPlugin.instance().uiSupport();
	
	public DynamicProjectRemoveDebugModuleNatureCommandItem() {
		//ImageDescriptor dsc = org.eclipse.jface.resource.ImageDescriptor.createFromFile( DynamicProjectRemoveDebugModuleNatureCommandItem.class, "module.carrier.png");
		//image = dsc.createImage();
		image = uisupport.images().addImage("dmb-cmd-rem-nature", DynamicProjectRemoveDebugModuleNatureCommandItem.class, "module.carrier.png");
	}
	
	public DynamicProjectRemoveDebugModuleNatureCommandItem(String id) {
		super( id);
	}
	
	@Override
	public void fill(Menu menu, int index) {
		long before = System.currentTimeMillis();
		IProject project = SelectionExtracter.currentProject();
		if (project == null) {
			return;
		}

		MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
	    menuItem.setText("Removes the debug-module nature from : " + DevrockPlugin.instance().getWorkspaceProjectView().getProjectDisplayName(project));
	    menuItem.setToolTipText( "Removes the debug-module nature from the currently selected project : " + project.getName());
	    menuItem.setImage(  image);
	    
	    menuItem.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {	            
	            	if (!NatureHelper.removeNature(project, TribefireServicesNature.NATURE_ID)) {
	    				DebugModuleBuilderStatus status = new DebugModuleBuilderStatus("cannot remove nature [" + TribefireServicesNature.NATURE_ID + "] from project [" + project.getName() + "]", IStatus.ERROR);
	    				DebugModuleBuilderPlugin.instance().log(status);
	    			}
	            }
	        });
	    if (log.isDebugEnabled()) {
	    	long after = System.currentTimeMillis();
	    	log.debug( getClass().getName() + " : " + (after - before));
	    }
	}

	@Override
	public void dispose() {
		image.dispose();
		super.dispose();
	}

}
