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
package com.braintribe.devrock.ac.container.decorator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.braintribe.devrock.ac.container.ArtifactContainer;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerPlugin;
import com.braintribe.devrock.api.ui.commons.ResolutionValidator;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;


/**
 * simple decorator that marks the project if there's no backing resolution or a failed resolution
 * @author pit
 *
 */
public class ArtifactContainerDecorator implements ILightweightLabelDecorator {
	private ImageDescriptor imageError;
	//private ImageDescriptor imageSkull;

	public ArtifactContainerDecorator() {
		imageError = ImageDescriptor.createFromFile(ArtifactContainerDecorator.class,"error.container.gif");
		//imageSkull = ImageDescriptor.createFromFile(ArtifactContainerDecorator.class,"dead.container.png");
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String arg1) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof IProject == false)
			return;		
		IProject project = (IProject) element;
		
		if (!project.isAccessible()) {			
			return;
		}
			
		ArtifactContainer container = ArtifactContainerPlugin.instance().containerRegistry().getContainerOfProject(project);
		if (container == null)
			return;
		
		AnalysisArtifactResolution resolution = container.getCompileResolution();
		// just check if it's failed
		if (resolution == null || ResolutionValidator.isResolutionInvalid(resolution)) {			
			decoration.addOverlay(imageError, IDecoration.TOP_LEFT);
		}
		
		
	}


}
