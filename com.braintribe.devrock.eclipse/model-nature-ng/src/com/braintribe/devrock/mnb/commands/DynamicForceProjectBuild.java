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
package com.braintribe.devrock.mnb.commands;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.braintribe.devrock.api.nature.NatureHelper;
import com.braintribe.devrock.api.selection.SelectionExtracter;
import com.braintribe.devrock.api.storagelocker.StorageLockerSlots;
import com.braintribe.devrock.api.ui.commons.UiSupport;
import com.braintribe.devrock.bridge.eclipse.workspace.WorkspaceProjectView;
import com.braintribe.devrock.mnb.commands.builder.WorkspaceModelDeclarationUpdater;
import com.braintribe.devrock.mnb.plugin.ModelBuilderPlugin;
import com.braintribe.devrock.mnb.plugin.ModelBuilderStatus;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.logging.Logger;


/**
 * dynamic command activate the builder on the model-projects within the currently selected projects in the workspace
 * 
 * @author pit
 *
 */
public class DynamicForceProjectBuild extends ContributionItem {
	private static Logger log = Logger.getLogger(DynamicForceProjectBuild.class);
	private Image image;
	private UiSupport uisupport = ModelBuilderPlugin.instance().uiSupport();
	
	public DynamicForceProjectBuild() {
		//ImageDescriptor dsc = org.eclipse.jface.resource.ImageDescriptor.createFromFile( DynamicForceProjectBuild.class, "model.png");
		//image = dsc.createImage();
		image = uisupport.images().addImage("mb-cmd-build", DynamicForceProjectBuild.class, "model.png"); 
	}
	
	public DynamicForceProjectBuild(String id) {
		super( id);
	}
	
	@Override
	public void fill(Menu menu, int index) {
		long before = System.currentTimeMillis();
		ISelection currentSelection = SelectionExtracter.currentSelection();
		if (currentSelection == null || currentSelection.isEmpty()) 
			return;
		
		Set<IProject> selectedProjects = SelectionExtracter.selectedProjects(currentSelection);
		
		List<IProject> modelProjects = selectedProjects.stream().filter( p -> NatureHelper.isModelArtifact(p)).collect(Collectors.toList());
		
		if (modelProjects == null || modelProjects.size() == 0)
			return;
								
		
		WorkspaceProjectView workspaceProjectView = DevrockPlugin.instance().getWorkspaceProjectView();
		
		MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
	    String modelNames = modelProjects.stream().map( p -> workspaceProjectView.getProjectDisplayName(p)).collect(Collectors.joining(","));
		menuItem.setText("Force model declaration build on (" + modelProjects.size() + ") selected models");
	    menuItem.setToolTipText( "Triggers a model-declaration build on the selected project(s) : " + modelNames);
	    menuItem.setImage(  image);
	    
	    menuItem.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {
	            	WorkspaceModelDeclarationUpdater updater = new WorkspaceModelDeclarationUpdater( modelProjects);
	            	updater.runAsJob();
	            }
	        });		
	    long after = System.currentTimeMillis();
	    long delay = after - before;

	    if (log.isDebugEnabled()) {
			log.debug( getClass().getName() + " : " + delay  + "ms");
	    }

	    long maxDelay = DevrockPlugin.envBridge().storageLocker().getValue( StorageLockerSlots.SLOT_DYNAMIC_COMMAND_MAX_DELAY, StorageLockerSlots.DEFAULT_DYNAMIC_COMMAND_MAX_DELAY);
	    if (delay > maxDelay) {
	    	ModelBuilderStatus status = new ModelBuilderStatus( "dynamic command took too long to setup [" + delay + " ms] :" + getClass().getName(), IStatus.WARNING);
	    	ModelBuilderPlugin.instance().log(status);	
	    }
	}

	@Override
	public void dispose() {
		//image.dispose();
		super.dispose();
	}

}
