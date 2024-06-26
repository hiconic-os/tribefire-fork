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
package com.braintribe.devrock.ac.container.properties;




import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

import com.braintribe.devrock.ac.container.ArtifactContainer;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerPlugin;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerStatus;
import com.braintribe.devrock.ac.container.properties.component.ContainerPropertiesComponent;

/**
 * the property page for the containers 
 * @author pit
 *
 */
public class ArtifactContainerPropertiesPage extends WizardPage implements IClasspathContainerPage, IClasspathContainerPageExtension {
	private com.braintribe.devrock.ac.container.ArtifactContainer container;
	private IJavaProject javaProject;
	private IClasspathEntry classpathEntry;

	private ContainerPropertiesComponent cpc;
	
	
	public ArtifactContainerPropertiesPage() {
		super("Artifact Container Properties");
		setTitle("Artifact Container Properties");
		setDescription("Set the default properties for the Artifact Container");	
	}

	
	@Override
	public void dispose() {
		if (cpc != null) {
			cpc.dispose();
		}
		
		super.dispose();
	}


	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
	
		cpc = new ContainerPropertiesComponent(getShell(), container, javaProject);
		Composite composite = cpc.createControl(parent);		
		setControl(composite);                        	
	}
	
	@Override
	public void initialize(IJavaProject project, IClasspathEntry[] entries) {
		this.javaProject = project;
		
		IProject iProject = project.getProject();		
		if (iProject != null) {
			container = ArtifactContainerPlugin.instance().containerRegistry().getContainerOfProject(iProject);
			if (container == null) {
			
				ArtifactContainerStatus status = new ArtifactContainerStatus( "No container stored for [" + iProject.getName() + "]", IStatus.WARNING);
				ArtifactContainerPlugin.instance().log(status);				
			}		
			
		}
	}

	@Override
	public boolean finish() {					
		// ok, no container -> must create one
		if (container == null) {
			classpathEntry = JavaCore.newContainerEntry(ArtifactContainer.ID, false);
		}
		return true;
	}

	@Override
	public IClasspathEntry getSelection() {		
		return classpathEntry;
		
	}

	@Override
	public void setSelection(IClasspathEntry classpathEntry) {
		this.classpathEntry = classpathEntry;
	}
	
	
		
}
