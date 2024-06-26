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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkingSet;

import com.braintribe.devrock.artifactcontainer.ArtifactContainerPlugin;
import com.braintribe.devrock.artifactcontainer.ArtifactContainerStatus;
import com.braintribe.devrock.artifactcontainer.control.project.ProjectImporter;
import com.braintribe.devrock.artifactcontainer.control.project.ProjectImporterTuple;
import com.braintribe.plugin.commons.selection.DetailedProjectExtractionResult;
import com.braintribe.plugin.commons.selection.PackageExplorerSelectedJarsTuple;
import com.braintribe.plugin.commons.selection.SelectionExtractor;
import com.braintribe.plugin.commons.selection.SelectionTuple;
import com.braintribe.plugin.commons.selection.TargetProvider;

public class JarImportCommand extends AbstractHandler implements TargetProvider {

	private IWorkingSet activeWorkingSet;
	private boolean preprocess = false;

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		final TargetProvider targetProvider = this;
		final PackageExplorerSelectedJarsTuple jarTuple = SelectionExtractor.extractSelectedJars();
		activeWorkingSet = jarTuple.currentWorkingSet;
		List<String> jars = jarTuple.selectedJars;
		
		if (jars == null || jars.size() == 0) {
			return null;
		}
		
		List<DetailedProjectExtractionResult> extractionResult = SelectionExtractor.extractProjectsWithDetails(jars.toArray( new String[0]));	
		List<ProjectImporterTuple> tupleList = new ArrayList<>();
		
		List<String> noProject = new ArrayList<>();
		for (DetailedProjectExtractionResult detail : extractionResult) {
			if (detail.extractedProject == null) {
				noProject.add(detail.jar);
			}
			else {
				ProjectImporterTuple importerTuple = new ProjectImporterTuple( detail.extractedProject, detail.extractedArtifact);
				tupleList.add(importerTuple);
			}
		}
		
		if (!noProject.isEmpty()) {
			String msg = "the following jars have no corresponding projects:\n" + noProject.stream().collect( Collectors.joining("\n"));
			ArtifactContainerStatus status = new ArtifactContainerStatus( msg, IStatus.WARNING);
			ArtifactContainerPlugin.getInstance().log(status);		
		}
					
		Job job = new Job("Running JarImporter") {
			@Override
			protected IStatus run(IProgressMonitor arg0) {
				ProjectImporter.importProjects( preprocess, targetProvider, null, tupleList.toArray( new ProjectImporterTuple[0]));
				return Status.OK_STATUS;
			}			
		};
		
		job.schedule();
		return null;		
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
