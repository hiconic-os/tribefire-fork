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
package com.braintribe.devrock.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;

import com.braintribe.devrock.api.clipboard.ArtifactToClipboardExpert;
import com.braintribe.devrock.api.clipboard.ClipboardEntry;
import com.braintribe.devrock.api.commands.AbstractDropdownCommandHandler;
import com.braintribe.devrock.api.selection.SelectionExtracter;
import com.braintribe.devrock.eclipse.model.actions.VersionModificationAction;
import com.braintribe.logging.Logger;
import com.braintribe.model.artifact.compiled.CompiledDependencyIdentification;


/**
 * command the inject dependencies from the clipboard into the selected projects
 * @author pit
 *
 */
public class DependencyFromClipboardPasteCommand extends AbstractDropdownCommandHandler {	
	private static Logger log = Logger.getLogger(DependencyFromClipboardPasteCommand.class);
	private String PARM_DIRECT = VersionModificationAction.untouched.name();//"direct";
	private String PARM_RANGIFY = VersionModificationAction.rangified.name();//"rangify";
	private String PARM_VARIABLE = VersionModificationAction.referenced.name();//"variable";	
	private VersionModificationAction lastUsedPasteMode = VersionModificationAction.referenced;
	private IProject target;
	
	// declare the param here
	{
		PARM_MSG = "com.braintribe.devrock.artifactcontainer.common.commands.command.param.paste";		
	}
	
	
	public DependencyFromClipboardPasteCommand() {	
	}
	
	public DependencyFromClipboardPasteCommand(IProject target) {
		this.target = target;
		
	}
	
	private void performInjection(VersionModificationAction mode) {

		// get contents from Clipoard
		boolean canPaste = false;
		
		// get pom file
		
		if (target == null) {
				
			target = SelectionExtracter.currentProject();
			if (target == null) {
				log.debug("no project currently selected");
				return;
			}
		}

		Clipboard clipboard = new Clipboard(Display.getCurrent());
		
		// see whether there are structured data of the copied artifacts 
		ClipboardEntry[] storedArtifactsOfCurrentClipboard = ArtifactToClipboardExpert.getStoredArtifactsOfCurrentClipboard(clipboard);
		
		if (storedArtifactsOfCurrentClipboard == null) {
			// no real artifacts attached to the clipoard, use the text-insert feature.. 
			TextTransfer plainTextTransfer = TextTransfer.getInstance();
			TransferData[] td = clipboard.getAvailableTypes();
	  		for (int i = 0; i < td.length; ++i) {
	  			if (TextTransfer.getInstance().isSupportedType(td[i])) {
	  				canPaste = true;
	  				break;
	  			}
	  		}
	  		if (!canPaste)
	  			return;
	  		
			String cliptxt = (String)clipboard.getContents(plainTextTransfer, DND.CLIPBOARD);
			if (cliptxt == null || cliptxt.length() == 0) {
				return;
			}
			ArtifactToClipboardExpert.injectDependenciesIntoProject( target, mode, cliptxt);	
		}
		else {
			// actual artifacts in the clipboard..
			List<CompiledDependencyIdentification> ecais = new ArrayList<>( storedArtifactsOfCurrentClipboard.length);
			for (ClipboardEntry entry : storedArtifactsOfCurrentClipboard) {
				ecais.add( entry.getIdentification());
			}
			ArtifactToClipboardExpert.injectDependenciesIntoProject( target, mode, ecais);
		}			
	}
	
	public void process( VersionModificationAction mode) {
		performInjection(mode);
	}

	@Override
	public void process(String param) {		
		VersionModificationAction pasteMode = lastUsedPasteMode;
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
	}


}
