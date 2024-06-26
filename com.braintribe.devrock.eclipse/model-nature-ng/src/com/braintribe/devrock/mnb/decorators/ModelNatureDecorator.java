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
package com.braintribe.devrock.mnb.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.braintribe.devrock.api.storagelocker.StorageLockerSlots;
import com.braintribe.devrock.mnb.natures.ModelNature;
import com.braintribe.devrock.plugin.DevrockPlugin;

public class ModelNatureDecorator implements ILightweightLabelDecorator {
	private final ImageDescriptor image;

	public ModelNatureDecorator() {
		image = ImageDescriptor.createFromFile(ModelNatureDecorator.class,"model.png");
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

		// no need to apply the small label if the whole icon is model-specific
		if (readFlag(StorageLockerSlots.SLOT_TF_NATURE_PROJECT_ICONS))
			return;

		IProject project = (IProject) element;
		if (hasModelNature(project)) {
			decoration.addOverlay(image, IDecoration.TOP_LEFT);
		}
	}

	private boolean readFlag(String slot) {
		return DevrockPlugin.instance().storageLocker().getValue(slot, false);
	}

	private boolean hasModelNature(IProject project) {
		try {
			IProjectDescription descriptions = project.getDescription();

			for (String id : descriptions.getNatureIds()) {
				if (id.equalsIgnoreCase(ModelNature.NATURE_ID))
					return true;
			}

		} catch (CoreException e) {
		}
		return false;
	}

}
