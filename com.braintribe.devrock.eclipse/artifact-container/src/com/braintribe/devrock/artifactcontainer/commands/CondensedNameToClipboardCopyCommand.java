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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;

import com.braintribe.devrock.artifactcontainer.ArtifactContainerPlugin;
import com.braintribe.devrock.artifactcontainer.ArtifactContainerStatus;
import com.braintribe.model.artifact.Artifact;
import com.braintribe.model.artifact.processing.version.VersionProcessor;
import com.braintribe.plugin.commons.selection.PackageExplorerSelectedJarsTuple;
import com.braintribe.plugin.commons.selection.SelectionExtractor;
import com.braintribe.plugin.commons.selection.SelectionTuple;
import com.braintribe.plugin.commons.selection.TargetProvider;

/**
 * copies condensed names of all selected entries in the package explorer 
 * @author pit
 *
 */
public class CondensedNameToClipboardCopyCommand extends AbstractHandler implements TargetProvider {

	private IWorkingSet activeWorkingSet;	
	private Clipboard clipboard;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PackageExplorerSelectedJarsTuple tuple = SelectionExtractor.extractSelectedJars();
		activeWorkingSet = tuple.currentWorkingSet;
	
		Set<Artifact> identifiedArtifacts = new HashSet<Artifact>();
		ISelection selection = SelectionExtractor.getCurrentPackageExplorerSelection();
		identifiedArtifacts.addAll( SelectionExtractor.extractSelectedArtifacts( selection));
		if (identifiedArtifacts.size() > 0) {
			copyToClipboard( identifiedArtifacts.toArray( new Artifact[0]));
		}
		else {
			ArtifactContainerStatus status = new ArtifactContainerStatus( "cannot identify any artifacts from selection", IStatus.WARNING);
			ArtifactContainerPlugin.getInstance().log(status);	
		}
		return null;
	}
	
	
			
	private void copyToClipboard( Artifact ... artifacts ) {
		
		if (clipboard != null) {
			clipboard.dispose();
		}
		Display display = PlatformUI.getWorkbench().getDisplay();
	
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < artifacts.length; i++) {
			Artifact artifact = artifacts[i];
			String condensedName = artifact.getGroupId() + ":" + artifact.getArtifactId() + "#" + VersionProcessor.toString(artifact.getVersion());
			if (builder.length() > 0)
				builder.append( "\n");
			builder.append( condensedName);
		}
		clipboard = new Clipboard( display);
		clipboard.setContents( new Object [] {builder.toString()}, new Transfer[] { TextTransfer.getInstance() });
		
	}
		
	@Override
	public IWorkingSet getTargetWorkingSet() {	
		return activeWorkingSet;
	}

	@Override
	public SelectionTuple getSelectionTuple() {	
		return null;
	}
	@Override
	public IProject getTargetProject() { 
		return null;
	}	
	@Override
	public Set<IProject> getTargetProjects() {
		return null;
	}
	@Override
	public void refresh() {			
	}
	
	
	
}
