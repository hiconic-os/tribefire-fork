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
package com.braintribe.devrock.mj.commands.natures;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;

import com.braintribe.devrock.api.nature.NatureHelper;
import com.braintribe.devrock.api.selection.SelectionExtracter;
import com.braintribe.devrock.mj.natures.GwtLibraryNature;
import com.braintribe.devrock.mj.natures.GwtTerminalNature;
import com.braintribe.devrock.mj.plugin.MungoJerryPlugin;
import com.braintribe.devrock.mj.plugin.MungoJerryStatus;

/**
 * remove the model nature from the currently selected project
 * @author pit
 *
 */
public class RemoveGwtNature extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IProject project = SelectionExtracter.currentProject( SelectionExtracter.currentSelection());
		if (project != null) {
			if (!NatureHelper.removeNature(project, GwtLibraryNature.NATURE_ID, GwtTerminalNature.NATURE_ID)) {
				MungoJerryStatus status = new MungoJerryStatus("cannot remove nature [" + GwtLibraryNature.NATURE_ID + "] from project [" + project.getName() + "]", IStatus.ERROR);
				MungoJerryPlugin.instance().log(status);
			}
		}
		return null;
	}

}
