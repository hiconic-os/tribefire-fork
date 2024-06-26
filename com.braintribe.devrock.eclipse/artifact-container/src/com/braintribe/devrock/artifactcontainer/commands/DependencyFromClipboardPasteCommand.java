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
package com.braintribe.devrock.artifactcontainer.commands;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.braintribe.devrock.artifactcontainer.ArtifactContainerPlugin;
import com.braintribe.model.artifact.Artifact;
import com.braintribe.model.malaclypse.cfg.preferences.ac.qi.VersionModificationAction;
import com.braintribe.plugin.commons.commands.AbstractDropdownCommandHandler;
import com.braintribe.plugin.commons.commands.ArtifactToClipboardExpert;
import com.braintribe.plugin.commons.selection.SelectionExtractor;

public class DependencyFromClipboardPasteCommand extends AbstractDropdownCommandHandler {	
	private String PARM_MSG = "com.braintribe.devrock.artifactcontainer.common.commands.command.param.paste";
	private String PARM_DIRECT = VersionModificationAction.untouched.name();//"direct";
	private String PARM_RANGIFY = VersionModificationAction.rangified.name();//"rangify";
	private String PARM_VARIABLE = VersionModificationAction.referenced.name();//"variable";
	
	private Object performInjection(VersionModificationAction mode) {

		// get contents from Clipoard
		boolean canPaste = false;
		
		// get pom file 
		IWorkbench iworkbench = PlatformUI.getWorkbench();
		IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
		IWorkbenchPage page =  iworkbenchwindow.getActivePage();
		ISelection selection = page.getSelection();		
		IProject project = SelectionExtractor.extractSelectedProject(selection);

		Clipboard clipboard = new Clipboard(Display.getCurrent());
		
		// see whether there are structured data of the copied artifacts 
		Artifact[] storedArtifactsOfCurrentClipboard = ArtifactToClipboardExpert.getStoredArtifactsOfCurrentClipboard(clipboard);
		
		if (storedArtifactsOfCurrentClipboard == null) {
			
			TextTransfer plainTextTransfer = TextTransfer.getInstance();
			TransferData[] td = clipboard.getAvailableTypes();
	  		for (int i = 0; i < td.length; ++i) {
	  			if (TextTransfer.getInstance().isSupportedType(td[i])) {
	  				canPaste = true;
	  				break;
	  			}
	  		}
	  		if (!canPaste)
	  			return null;
	  		
			String cliptxt = (String)clipboard.getContents(plainTextTransfer, DND.CLIPBOARD);
			if (cliptxt == null || cliptxt.length() == 0) {
				return null;
			}
			ArtifactToClipboardExpert.injectDependenciesIntoProject( project, mode, cliptxt);	
		}
		else {
			ArtifactToClipboardExpert.injectDependenciesIntoProject( project, mode, storedArtifactsOfCurrentClipboard);
		}
		
		
		// 
		return null;
	}

	@Override
	public void process(String param) {		
		VersionModificationAction pasteMode = ArtifactContainerPlugin.getInstance().getArtifactContainerPreferences(false).getQuickImportPreferences().getLastDependencyPasteMode();
		if (param == null) {
			;
		}
		else if (param.equalsIgnoreCase( PARM_DIRECT)) {
			pasteMode = VersionModificationAction.untouched;
		}
		else if (param.equalsIgnoreCase( PARM_RANGIFY)) {
			pasteMode = VersionModificationAction.rangified;
		}
		else if (param.equalsIgnoreCase( PARM_VARIABLE)) {
			pasteMode = VersionModificationAction.referenced;	
		}
		performInjection(pasteMode);
		ArtifactContainerPlugin.getInstance().getArtifactContainerPreferences(false).getQuickImportPreferences().setLastDependencyPasteMode(pasteMode);
	}

	@Override
	protected String getParamKey() {
		return PARM_MSG;
	}
	
	
			
}
