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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.braintribe.devrock.artifactcontainer.ArtifactContainerPlugin;
import com.braintribe.devrock.artifactcontainer.ArtifactContainerStatus;
import com.braintribe.devrock.artifactcontainer.quickImport.ui.QuickImportDialog;
import com.braintribe.model.malaclypse.cfg.preferences.ac.qi.QuickImportAction;
import com.braintribe.plugin.commons.commands.AbstractDropdownCommandHandler;
import com.braintribe.plugin.commons.selection.TargetProviderImpl;

public class QuickImportDropdownHandler extends AbstractDropdownCommandHandler {
	private String PARM_MSG = "com.braintribe.devrock.artifactcontainer.common.commands.command.param.quickimport";
	private static final String paramForQuickImportProject = "project";
	private static final String paramForQuickImportDepdendency = "dependency";
	
	

	@Override
	protected String getParamKey() {	
		return PARM_MSG;
	}


	@Override
	public void process(String parameter) {
			
		QuickImportAction action = QuickImportAction.importProject;

		if (parameter != null) { 
			if (parameter.equalsIgnoreCase( paramForQuickImportProject)) {			
				action = QuickImportAction.importProject;									
			} 
			else if (parameter.equalsIgnoreCase(paramForQuickImportDepdendency)) {
				action = QuickImportAction.importDependency;
			}
		}
		else {								
			action = QuickImportAction.importProject;			
		}
		
		// 
		Display display = PlatformUI.getWorkbench().getDisplay();
		final Shell shell = new Shell (display, QuickImportDialog.SHELL_STYLE);		
		final QuickImportAction selectedAction = action;
		Job job = new Job("Running QuickImporter") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {						
					//																					
					shell.getDisplay().asyncExec( new Runnable() {
						
						@Override
						public void run() {
							QuickImportDialog quickImportDialog = new QuickImportDialog( shell);
							quickImportDialog.setImportAction( selectedAction);												
							quickImportDialog.setTargetProvider( new TargetProviderImpl());
							quickImportDialog.open();									
						}
					});
					
			
					return Status.OK_STATUS;
				} catch (Exception e) {		
					ArtifactContainerStatus status = new ArtifactContainerStatus("Launching Quick Importer failed", e);
					ArtifactContainerPlugin.getInstance().log(status);	
					
				}
				return Status.CANCEL_STATUS;
			}
			
		};
		
		try {
			job.schedule();
		} catch (Exception e) {
			ArtifactContainerStatus status = new ArtifactContainerStatus("Running Quick Importer failed", e);
			ArtifactContainerPlugin.getInstance().log(status);	
		}
	}

	
}
