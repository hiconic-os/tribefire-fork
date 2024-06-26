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
package com.braintribe.devrock.ac.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.braintribe.devrock.ac.container.ArtifactContainer;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerPlugin;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerStatus;
import com.braintribe.devrock.ac.container.resolution.viewer.ContainerResolutionViewer;
import com.braintribe.devrock.api.selection.SelectionExtracter;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.gm.model.reason.essential.NotFound;
import com.braintribe.logging.Logger;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;

/**
 * command to show the current data of a container. Alternative to using Eclipse's
 * 'container properties' or 'project properties -> configure build path -> container -> edit'
 * 
 * @author pit
 *
 */
public class ShowContainerResolutionCommand extends AbstractHandler {
	private static Logger log = Logger.getLogger(BuildProjectCommand.class);
	private static boolean autoGenerate = true;
	
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
			
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);		
		try {
			ISelection selection = SelectionExtracter.currentSelection(activeWorkbenchWindow);
			if (selection == null) {
				return null;
			}
			
			IProject selectedProject = SelectionExtracter.currentProject(selection);
			if (selectedProject == null) {
				return null;
			}
			ArtifactContainer containerOfProject = ArtifactContainerPlugin.instance().containerRegistry().getContainerOfProject(selectedProject);
			if (containerOfProject == null) {
				return null;
			}
			AnalysisArtifactResolution resolution = containerOfProject.getCompileResolution();
			if (resolution == null) {
				if (autoGenerate) {
					String msg = "no resolution attached to container of project : " +selectedProject.getName() + ", retrying";
					log.warn(msg);
					ArtifactContainerStatus status = new ArtifactContainerStatus( msg, IStatus.WARNING);
					ArtifactContainerPlugin.instance().log(status);
					
					containerOfProject.getClasspathEntries();
					resolution = containerOfProject.getCompileResolution();				
				}
				if (resolution == null) {											
					resolution = AnalysisArtifactResolution.T.create();
					resolution.setFailure( Reasons.build(NotFound.T).text( "no resolution attached to container of [" + selectedProject.getName() + "]").toReason());								
				}				
			}
			Display display = PlatformUI.getWorkbench().getDisplay();
			ContainerResolutionViewer resolutionViewer = new ContainerResolutionViewer( display.getActiveShell());
			resolutionViewer.setResolution( resolution);
			resolutionViewer.setProjectDependencies(containerOfProject.getProjectDependencies());
			resolutionViewer.preemptiveDataRetrieval();
			resolutionViewer.open();
			
			
		} catch (Exception e) {
			String msg = "cannot open container dialog";
			log.error(msg, e);
			ArtifactContainerStatus status = new ArtifactContainerStatus( msg, e);
			ArtifactContainerPlugin.instance().log(status);
		}
		return null;		
	}
}
