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
package com.braintribe.devrock.ac.commands.dynamic;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.braintribe.devrock.ac.container.plugin.ArtifactContainerPlugin;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerStatus;
import com.braintribe.devrock.ac.container.updater.ProjectUpdater;
import com.braintribe.devrock.api.selection.SelectionExtracter;
import com.braintribe.devrock.api.storagelocker.StorageLockerSlots;
import com.braintribe.devrock.api.ui.commons.UiSupport;
import com.braintribe.devrock.bridge.eclipse.workspace.WorkspaceProjectView;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.logging.Logger;


/**
 * dynamic command to show the properties of a selected project's container
 * @author pit
 *
 */
public class DynamicBuildProjectsCommandItem extends ContributionItem {
	private static Logger log = Logger.getLogger(DynamicBuildProjectsCommandItem.class);
	
	private UiSupport uisupport = ArtifactContainerPlugin.instance().uiSupport();	
	private Image image;
	
	public DynamicBuildProjectsCommandItem() {
		//ImageDescriptor dsc = org.eclipse.jface.resource.ImageDescriptor.createFromFile( DynamicBuildProjectsCommandItem.class, "refresh-classpath.png");
		image = uisupport.images().addImage( "ac-cmd-build-prjs", DynamicBuildProjectsCommandItem.class, "refresh-classpath.png");		
	}
	
	public DynamicBuildProjectsCommandItem(String id) {
		super( id);
	}
	
	@Override
	public void fill(Menu menu, int index) {	
		long before = System.currentTimeMillis();
		Set<IProject> selectedProjects = SelectionExtracter.selectedProjects( SelectionExtracter.currentSelection());
		if (selectedProjects == null || selectedProjects.size() == 0)
			return;
		
		WorkspaceProjectView workspaceProjectView = DevrockPlugin.instance().getWorkspaceProjectView();
		MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
		if (selectedProjects.size() > 1) {
			menuItem.setText("Refresh the classpath containers of (" + selectedProjects.size() + " selected projects)");			
			String txt = selectedProjects.stream().map(ip -> workspaceProjectView.getProjectDisplayName(ip)).collect( Collectors.joining(", "));
			menuItem.setToolTipText( "Refreshes the classpath containers of the following projects: " + txt);
		}
		else {			
			IProject ip = selectedProjects.stream().findFirst().orElse(null);			
			String token = workspaceProjectView.getProjectDisplayName(ip);			
			menuItem.setText("Refresh the classpath containers of the selected project: " + token);
			menuItem.setToolTipText( "Refreshes the classpath containers of the following project: " + token);
		}
		
	    menuItem.setImage(  image);
	    
	    menuItem.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {
	            	try {	        			
	            		ProjectUpdater updater = new ProjectUpdater();
	        			updater.setSelectedProjects( selectedProjects);
	        			updater.runAsJob();
					} catch (Exception e1) {
						ArtifactContainerStatus status = new ArtifactContainerStatus("cannot run refresh on projects", e1);
						ArtifactContainerPlugin.instance().log(status);
					}
	            }
	        });		
	    long after = System.currentTimeMillis();
	    long delay = after - before;

	    if (log.isDebugEnabled()) {
			log.debug( getClass().getName() + " : " + delay  + "ms");
	    }

	    long maxDelay = DevrockPlugin.envBridge().storageLocker().getValue( StorageLockerSlots.SLOT_DYNAMIC_COMMAND_MAX_DELAY, StorageLockerSlots.DEFAULT_DYNAMIC_COMMAND_MAX_DELAY);
	    if (delay > maxDelay) {
	    	ArtifactContainerStatus status = new ArtifactContainerStatus( "dynamic command took too long to setup [" + delay + " ms] :" + getClass().getName(), IStatus.WARNING);
	    	ArtifactContainerPlugin.instance().log(status);	
	    }
	}

	@Override
	public void dispose() {
		//image.dispose();
		super.dispose();
	}
	
	
	
	

}
