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
package com.braintribe.devrock.api.nature;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.essential.InternalError;

/**
 * a helper to access a {@link IProject}'s builder configuration
 * 
 * @author pit
 *
 */
public class JavaProjectBuilderHelper {

	/**	 
	 * @param project - the {@link IProject} to check
	 * @param builderId - the fully qualified name of the builder (aka name of java class)
	 * @return - a {@link Maybe} with true or false
	 */
	public static Maybe<Boolean> hasBuilder(IProject project, String builderId) {
		Maybe<List<String>> builderMaybe = getBuilders(project);
		if (builderMaybe.isUnsatisfied()) {
			return Maybe.empty( builderMaybe.whyUnsatisfied());
		}
		List<String> builders = builderMaybe.get();
		if (builders.contains(builderId)) {
			return Maybe.complete(true);
		}
		return Maybe.complete(false);				
	}
	
	/**
	 * @param project - the {@link IProject} to check 
	 * @param builderIds - the fully qualified names of the builders to check
	 * @return - a {@link Maybe} with true or false 
	 */
	public static Maybe<Boolean> hasBuilders(IProject project, String ... builderIds ) {
		Maybe<List<String>> builderMaybe = getBuilders(project);
		if (builderMaybe.isUnsatisfied()) {
			return Maybe.empty( builderMaybe.whyUnsatisfied());
		}
		List<String> builders = builderMaybe.get();
		for (String builderId : builderIds) {
			if (builders.contains(builderId)) {
				return Maybe.complete(true);
			}
			
		}
		return Maybe.complete( false);
	}
	

	/**
	 * @param project - the {@link IProject} to check
	 * @return - a {@link Maybe} with a {@link List} of the fully qualified names of the builders attached
	 */
	public static Maybe<List<String>> getBuilders(IProject project) {
		List<String> result = new ArrayList<>();
		IProjectDescription desc;
		try {
			desc = project.getDescription();
		} catch (CoreException e) {
			InternalError ie = InternalError.from(e, "cannot access description of project: " + project.getName());
			return Maybe.empty( ie);
		}
		ICommand[] commands = desc.getBuildSpec();
		   for (int i = 0; i < commands.length; ++i) {		      
		      result.add( commands[i].getBuilderName());
		   }
		return Maybe.complete( result);
	}
}
