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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.braintribe.devrock.ac.container.plugin.ArtifactContainerPlugin;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerStatus;
import com.braintribe.devrock.mc.api.repository.configuration.RepositoryReflection;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.gm.model.reason.Maybe;

public class PurgeLockFilesCommand  extends AbstractHandler {
	private List<File> lockFilesFound;

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		Maybe<RepositoryReflection> repositoryReflectionMaybe = DevrockPlugin.mcBridge().reflectRepositoryConfiguration();
		if (!repositoryReflectionMaybe.isSatisfied()) {
			ArtifactContainerStatus acs = new ArtifactContainerStatus("cannot retrieve repository reflection as " + repositoryReflectionMaybe.whyUnsatisfied(), IStatus.ERROR);
			ArtifactContainerPlugin.instance().log(acs);
			return null;
		}
		RepositoryReflection reflection = repositoryReflectionMaybe.get();
		String localRepositoryPath = reflection.getRepositoryConfiguration().getCachePath();
		File repoRoot = new File( localRepositoryPath);
		if (!repoRoot.exists()) {
			ArtifactContainerStatus acs = new ArtifactContainerStatus("referenced local repository [" + localRepositoryPath  + "] doesn't exist", IStatus.ERROR);
			ArtifactContainerPlugin.instance().log(acs);
			return null;	
		}
		// scan for *.lck files
		lockFilesFound = new ArrayList<>(50);
		
		Job job = new WorkspaceJob("purging *.lck files") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				monitor.beginTask( "scanning for *.lck files", -1);
				scan( repoRoot);				
			
				int numLockFiles = lockFilesFound.size();
				//System.out.println("Found [" + numLockFiles + "] lock files in local repository [" + repoRoot.getAbsolutePath() + "]");
				if (numLockFiles == 0) {
					ArtifactContainerStatus acs = new ArtifactContainerStatus("no lock files found in local repository [" + repoRoot.getAbsolutePath() + "]" , IStatus.INFO);
					ArtifactContainerPlugin.instance().log(acs);
					return Status.OK_STATUS;
				}
					
				int i = 0;
				monitor.beginTask("deleting found lck files", numLockFiles);
				for (File file : lockFilesFound) {
					try {
						file.delete();
						monitor.worked(++i);						
					} catch (Exception e) {
						ArtifactContainerStatus acs = new ArtifactContainerStatus("cannot delete lock file [" + file.getAbsolutePath()  + "]", IStatus.ERROR);
						ArtifactContainerPlugin.instance().log(acs);		
					}
				}
				monitor.done();
				ArtifactContainerStatus acs = new ArtifactContainerStatus("deleted  [" + i  + "] lock files from local repository [" + repoRoot.getAbsolutePath() + "]" , IStatus.INFO);
				ArtifactContainerPlugin.instance().log(acs);
				return Status.OK_STATUS;								
			}
		};
		
		job.schedule();
		
		return null;
	}

	private void scan(File repoRoot) {
		//System.out.println("scanning " + repoRoot.getAbsolutePath());
		File[] files = repoRoot.listFiles();
		if (files != null && files.length > 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					scan( file);
				}
				else {
					if (file.getName().endsWith(".lck")) {
						lockFilesFound.add(file);
						//System.out.println("detected lck file : " + file.getAbsolutePath());
					}				
				}
			}
		}		
	}

	
}
