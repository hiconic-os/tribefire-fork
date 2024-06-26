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
package com.braintribe.devrock.dmb.builder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.braintribe.devrock.dmb.plugin.DebugModuleBuilderPlugin;
import com.braintribe.devrock.dmb.plugin.DebugModuleBuilderStatus;

public class WorkspaceDebugModuleClasspathUpdater extends WorkspaceModifyOperation {
	
	
	private List<IProject> projects;

	public WorkspaceDebugModuleClasspathUpdater(List<IProject> projects) {
		this.projects = projects;
	}
	

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
	
		int i = 0;
		monitor.beginTask("Building debug module classpaths ", projects.size());
		for (IProject project : projects) {
			if (monitor.isCanceled()) {
				DebugModuleBuilderStatus status = new DebugModuleBuilderStatus("debug module classpath builds aborted by user", IStatus.INFO);
				DebugModuleBuilderPlugin.instance().log(status);
				return;
			}
			monitor.subTask( "resolving " + project.getName());
			DebugModuleBuilder.updateModuleCarrierClasspath(project);
			monitor.worked(++i);			
		}
	}
	
	public void runAsJob() {
		if (projects == null) {
			DebugModuleBuilderStatus status = new DebugModuleBuilderStatus("No projects to build configured", IStatus.WARNING);
			DebugModuleBuilderPlugin.instance().log(status);
			return;
		}
		Job job = new WorkspaceJob("Triggering debug module classpath build on (" + projects.size() +") debug module projects") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
								
				try {
					execute( monitor);
				} catch (Exception e) {
					DebugModuleBuilderStatus status = new DebugModuleBuilderStatus("cannot trigger debug module classpath builds", e);
					DebugModuleBuilderPlugin.instance().log(status);
				} 
				return Status.OK_STATUS;
			}
			
		};
		job.schedule();
	
	}

}
