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
package com.braintribe.devrock.commands.dynamic;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.braintribe.devrock.api.clipboard.ArtifactToClipboardExpert;
import com.braintribe.devrock.api.selection.EnhancedSelectionExtracter;
import com.braintribe.devrock.api.selection.SelectionExtracter;
import com.braintribe.devrock.api.storagelocker.StorageLockerSlots;
import com.braintribe.devrock.api.ui.commons.UiSupport;
import com.braintribe.devrock.commands.DependencyToClipboardCopyCommand;
import com.braintribe.devrock.eclipse.model.actions.VersionModificationAction;
import com.braintribe.devrock.eclipse.model.identification.EnhancedCompiledArtifactIdentification;
import com.braintribe.devrock.eclipse.model.identification.RemoteCompiledDependencyIdentification;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.devrock.plugin.DevrockPluginStatus;
import com.braintribe.logging.Logger;


public class DynamicDependencyCopyCommandItem extends ContributionItem {	
	private static Logger log = Logger.getLogger(DynamicDependencyCopyCommandItem.class);
	private Image image;
	private Clipboard clipboard;
	private UiSupport uisupport =  DevrockPlugin.instance().uiSupport();
	
	public DynamicDependencyCopyCommandItem() {
		//ImageDescriptor dsc = org.eclipse.jface.resource.ImageDescriptor.createFromFile( DynamicDependencyCopyCommandItem.class, "copyToClipboard.png");
		image = uisupport.images().addImage("cmd-dep-copy",  DynamicDependencyCopyCommandItem.class, "copyToClipboard.png"); 		 		
	}
	
	public DynamicDependencyCopyCommandItem(String id) {
		super( id);
	}
	

	@Override
	public void fill(Menu menu, int index) {
		long before = System.currentTimeMillis();
		// retrieve last used copy mode
		VersionModificationAction copyMode = DevrockPlugin.envBridge().storageLocker().getValue( DependencyClipboardRelatedHelper.STORAGE_SLOT_COPY_MODE, VersionModificationAction.referenced); 

    	ISelection selection = SelectionExtracter.currentSelection();
		// get selected container entry 
		List<EnhancedCompiledArtifactIdentification> identifiedArtifacts = EnhancedSelectionExtracter.extractEitherJarEntriesOrOwnerArtifacts(selection);

		String namesToBeCopied = identifiedArtifacts.stream().map( ecai -> ecai.asString()).collect(Collectors.joining(","));
		int num = identifiedArtifacts.size();
		
		String text;
		String tooltip;
		if (num > 1) {
			text = "Copy " + num + " dependency declarations " + DependencyClipboardRelatedHelper.getAppropriateActionLabelRepresentation(copyMode);
			tooltip = "Copy " + num + " dependency declarations " + DependencyClipboardRelatedHelper.getAppropriateActionLabelRepresentation(copyMode) + "\n" + namesToBeCopied;
		}
		else {
			text = "Copy dependency declaration of " + namesToBeCopied + ", " + DependencyClipboardRelatedHelper.getAppropriateActionLabelRepresentation(copyMode);
			tooltip = "Copy " + num + " dependency declaration, " + DependencyClipboardRelatedHelper.getAppropriateActionLabelRepresentation(copyMode) + ":" + namesToBeCopied;
		}
		
		MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
	    menuItem.setText(text);
	    menuItem.setToolTipText( tooltip);
	    menuItem.setImage(  image);
	    
	    menuItem.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {
	            	DependencyToClipboardCopyCommand cmd = new DependencyToClipboardCopyCommand();
	            	cmd.process( null);
	            		        		
	        		if (identifiedArtifacts.size() > 0) {
	        			if (clipboard != null) {
	        				clipboard.dispose();
	        			}
	        			List<RemoteCompiledDependencyIdentification> rcdis = identifiedArtifacts.stream().map( ecai -> RemoteCompiledDependencyIdentification.from( ecai)).collect(Collectors.toList());
	        			clipboard = ArtifactToClipboardExpert.copyToClipboard( copyMode, rcdis);
	        		}
	        		else {
	        			DevrockPluginStatus status = new DevrockPluginStatus( "cannot identify any artifacts from selection", IStatus.WARNING);
	        			DevrockPlugin.instance().log(status);	
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
			DevrockPluginStatus status = new DevrockPluginStatus( "dynamic command took too long to setup [" + delay + " ms] :" + getClass().getName(), IStatus.WARNING);
			DevrockPlugin.instance().log(status);	
	    }
	    
	}

	@Override
	public void dispose() {
		//image.dispose();
		super.dispose();
	}
	
	
	
	

}
