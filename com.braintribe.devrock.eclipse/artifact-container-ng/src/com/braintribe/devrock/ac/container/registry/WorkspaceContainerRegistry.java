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
package com.braintribe.devrock.ac.container.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;

import com.braintribe.devrock.ac.container.ArtifactContainer;

/**
 * simple registry for artifact containers in the workspace.. 
 * @author pit
 *
 */
public class WorkspaceContainerRegistry {
	private Map<IProject,ArtifactContainer> projectToContainerMap = new ConcurrentHashMap<>();
	private Map<ArtifactContainer, IProject> containerMapToProjectMap = new ConcurrentHashMap<>();
	
	/**
	 * called when a container is initialized
	 * @param project - the {@link IProject}
	 * @param container - the {@link ArtifactContainer}
	 */
	public void acknowledgeContainerAttachment(IProject project, ArtifactContainer container) {
		projectToContainerMap.put(project, container);
		containerMapToProjectMap.put(container, project);
	}
	/**
	 * @param project - the {@link IProject}
	 * @param container - the {@link ArtifactContainer}
	 * @return
	 */
	public boolean acknowledgeContainerDetachment(IProject project, ArtifactContainer container) {		
		boolean mapped = projectToContainerMap.remove(project, container);
		if (mapped) {
			containerMapToProjectMap.remove(container);			
		}
		return mapped;
			
	}
	/**
	 * call via copy constructor of {@link ArtifactContainer}
	 * @param project - the {@link IProject}
	 * @param oldContainer - old {@link ArtifactContainer} instance
	 * @param newContainer - new {@link ArtifactContainer} instance
	 */
	public boolean acknowledgeContainerReassignment(ArtifactContainer oldContainer, ArtifactContainer newContainer) {
		IProject project = containerMapToProjectMap.get(oldContainer);
		if (project == null)
			return false;
		projectToContainerMap.remove(project);		
		projectToContainerMap.put(project, newContainer);
		containerMapToProjectMap.remove(oldContainer);
		containerMapToProjectMap.put(newContainer, project);
		return true;
	}
	
	public boolean hasContainer(IProject project) {
		return projectToContainerMap.containsKey(project);
	}
	
	public ArtifactContainer getContainerOfProject(IProject project) {		
		return projectToContainerMap.get(project);
	}
	
	public IProject getProjectOfContainer( ArtifactContainer container) {
		return containerMapToProjectMap.get(container);
	}
}
