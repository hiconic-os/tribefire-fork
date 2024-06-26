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
package com.braintribe.devrock.bridge.eclipse.workspace;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import com.braintribe.devrock.api.identification.PomIdentificationHelper;
import com.braintribe.devrock.mc.core.declared.commons.HashComparators;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;
import com.braintribe.model.artifact.essential.VersionedArtifactIdentification;

/**
 * just a wrapper for the {@link WorkspaceProjectView} to enable caching
 * @author pit
 *
 */
public class WorkspaceProjectViewSupplier implements Supplier<WorkspaceProjectView> {
	private WorkspaceProjectView currentWorkspaceProjectView;
	private Instant exposureInstant;
	private boolean dirtied;
	private Duration staleDelay = Duration.ofMillis( 1000);  // a second for now 
	
	/**
	 * @return - the current state of the workspace as a {@link WorkspaceProjectView} 
	 */
	private WorkspaceProjectView retrieveWorkspaceProjectView() {
		System.out.println("Accessing project view");
		WorkspaceProjectView view = new WorkspaceProjectView();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (IProject project : root.getProjects()) {
			if (!project.isAccessible() || !project.isOpen()) {
				System.out.println("project [" + project.getName() + "] isn't accessible");
				continue;
			}
			Maybe<CompiledArtifactIdentification> caipot = PomIdentificationHelper.identifyProject(project);
			if (caipot.isUnsatisfied()) {
				System.out.println("Cannot identify pom of project [" + project.getName() + "]");
				continue;
			}
					
			CompiledArtifactIdentification cai = caipot.get();
			
			BasicWorkspaceProjectInfo workspaceProjectInfo = new BasicWorkspaceProjectInfo();
			workspaceProjectInfo.setProject(project);
			VersionedArtifactIdentification vai = VersionedArtifactIdentification.create( cai.getGroupId(), cai.getArtifactId(), cai.getVersion().asString());
			workspaceProjectInfo.setVersionedArtifactIdentification( vai);
			
			view.getArtifactsInWorkspace().put( HashComparators.versionedArtifactIdentification.eqProxy( vai), workspaceProjectInfo);
			view.getProjectsInWorkspace().put( project, workspaceProjectInfo);						
		}		
		return view;
	}
	
	/**
	 * @return - 
	 */
	@Override
	public WorkspaceProjectView get() {
		if (currentWorkspaceProjectView == null) {
			currentWorkspaceProjectView = retrieveWorkspaceProjectView();
		}
		else if (isStale()){
			WorkspaceProjectView previousView = currentWorkspaceProjectView;
			currentWorkspaceProjectView = retrieveWorkspaceProjectView();	
			if (dirtied == false) {
				dirtied = !previousView.equals(currentWorkspaceProjectView);
			}
		}
		exposureInstant = Instant.now();
		return currentWorkspaceProjectView;
	}

	/**
	 * @return - true if the last exposure is older than the delay
	 */
	private boolean isStale() {
		return Instant.now().minus( staleDelay).isAfter(exposureInstant);
	}
	
	public boolean dirtied() {
		// no old data -> no comparison, i.e. dirty
		if (currentWorkspaceProjectView == null || dirtied) {
			dirtied = false;
			return true;
		}
 
		WorkspaceProjectView storedProjectView = currentWorkspaceProjectView;
		
		currentWorkspaceProjectView = null; // set to null so it gets recalculated in any case
		WorkspaceProjectView determinedProjectView = get();
		
		// compare the two
		return !storedProjectView.equals(determinedProjectView);		
	}

	
	
}
