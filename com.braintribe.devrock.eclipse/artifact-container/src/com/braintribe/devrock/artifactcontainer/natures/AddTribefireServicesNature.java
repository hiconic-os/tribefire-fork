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
package com.braintribe.devrock.artifactcontainer.natures;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.statushandlers.StatusManager;

import com.braintribe.devrock.artifactcontainer.ArtifactContainerStatus;
import com.braintribe.plugin.commons.selection.SelectionExtractor;

public class AddTribefireServicesNature extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IProject project = SelectionExtractor.extractSelectedProject();
		if (project != null) {
			addNature(project);
		}
		return null;
	}

	private void addNature(IProject project) throws ExecutionException {
		try {
			IProjectDescription description = project.getDescription();
			Set<String> natures = new TreeSet<>(Arrays.asList(description.getNatureIds()));
			
			if (natures.add( TribefireServicesNature.NATURE_ID)) {
				String manipulatedNatures[] = natures.toArray(new String[natures.size()]);
				description.setNatureIds(manipulatedNatures);
				project.setDescription(description, null);
			}
		} catch (CoreException e) {
			String msg = "error while adding nature ["+ TribefireServicesNature.NATURE_ID + "] to project description for " + project.getName();
			ArtifactContainerStatus status = new ArtifactContainerStatus(msg, e);
			StatusManager.getManager().handle(status);
			throw new ExecutionException(msg, e);
		}
	}
}
