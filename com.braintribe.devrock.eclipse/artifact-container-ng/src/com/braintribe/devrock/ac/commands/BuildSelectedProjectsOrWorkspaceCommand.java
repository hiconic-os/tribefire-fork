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

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.braintribe.devrock.ac.container.plugin.ArtifactContainerPlugin;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerStatus;
import com.braintribe.devrock.ac.container.updater.ProjectUpdater;
import com.braintribe.devrock.ac.container.updater.WorkspaceUpdater;
import com.braintribe.devrock.api.selection.SelectionExtracter;
import com.braintribe.devrock.api.storagelocker.StorageLockerSlots;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.logging.Logger;

/**
 * a command that can be configured to either refresh all containers in the workspace or the containers 
 * of the selected projects. 
 * See storage-locker slot {@link StorageLockerSlots#SLOT_SELECTIVE_WS_SYNCH}
 * @author pit
 *
 */
public class BuildSelectedProjectsOrWorkspaceCommand extends AbstractHandler {
	private static Logger log = Logger.getLogger(BuildSelectedProjectsOrWorkspaceCommand.class);
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);		
		
		ISelection selection = SelectionExtracter.currentSelection( activeWorkbenchWindow);
		Set<IProject> selectedProjects = SelectionExtracter.selectedProjects(selection);
		
		boolean selectivity = DevrockPlugin.envBridge().storageLocker().getValue( StorageLockerSlots.SLOT_SELECTIVE_WS_SYNCH, false);
		if (selectivity && selectedProjects != null && selectedProjects.size() != 0) {
			// selectivity activated and projects are selected	-> refresh on project
			try {
				ProjectUpdater updater = new ProjectUpdater();
				updater.setSelectedProjects( selectedProjects);
				updater.runAsJob();
			} catch (Exception e) {
				String msg = "cannot run project updater";
				log.error(msg, e);
				ArtifactContainerStatus status = new ArtifactContainerStatus( msg, e);
				ArtifactContainerPlugin.instance().log(status);
			}			
		}
		else {
			// no selectivity, no projects selected -> full refresh 
			try {
				WorkspaceUpdater updater = new WorkspaceUpdater();			
				updater.runAsJob();
			} catch (Exception e) {
				String msg = "cannot run workspace updater";
				log.error(msg, e);
				ArtifactContainerStatus status = new ArtifactContainerStatus( msg, e);
				ArtifactContainerPlugin.instance().log(status);
			}	
		}
		
		
		return null;		
	}
			
}
