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
package com.braintribe.devrock.ac.container.updater;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.braintribe.devrock.ac.container.ArtifactContainer;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerPlugin;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerStatus;
import com.braintribe.devrock.api.selection.SelectionExtracter;

/**
 * updates the currently selected projects - if they have a container attached
 * 
 * @author pit
 *
 */
public class ProjectUpdater extends WorkspaceModifyOperation {

	private Set<IProject> selectedProjects;
	private IWorkbenchWindow activeWorkbenchWindow;
	public enum Mode { standard, pom};
	private Mode mode;
	
	
	public ProjectUpdater() {
		this.mode = Mode.standard;
	}
	public ProjectUpdater(Mode mode) {
		this.mode = mode;
	}
	
	
	public ProjectUpdater(IWorkbenchWindow activeWorkbenchWindow) {
		this.activeWorkbenchWindow = activeWorkbenchWindow;
	}
	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
		
		if (selectedProjects == null) {
			ISelection selection = SelectionExtracter.currentSelection(activeWorkbenchWindow);
			selectedProjects = SelectionExtracter.selectedProjects(selection);
		}
		
		if (selectedProjects == null || selectedProjects.size() == 0)
			return;
		
		try {
			int i = 0;
			monitor.beginTask("reinitializing containers", selectedProjects.size());
			
			for (IProject project : selectedProjects) {
				monitor.subTask( "resolving " + project.getName());
				
				ArtifactContainer container = ArtifactContainerPlugin.instance().containerRegistry().getContainerOfProject(project);
				if (container != null) {
					// reassign - and perhaps update the VAI in the container
					ArtifactContainer sibling = container.reinitialize( mode);
					// trigger resolve
					sibling.getClasspathEntries( false);
				}
				else {
					System.out.println("no container for [" + project.getName() + "]");
				}
				monitor.worked(i++);
			}
		} 
		finally {
			monitor.done();
		}			
	}
	public void runAsJob() {
		Job job = new WorkspaceJob("Triggering sync on selected projects") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				// rescan				
				try {
					execute( monitor);
				} catch (Exception e) {
					ArtifactContainerStatus status = new ArtifactContainerStatus("cannot trigger update the selected containers", e);
					ArtifactContainerPlugin.instance().log(status);
				} 
				return Status.OK_STATUS;
			}
			
		};
		job.schedule();
	
	}
	public void setSelectedProjects(Set<IProject> selectedProjects) {
		this.selectedProjects = selectedProjects;
		// 
		
	}
		
}
