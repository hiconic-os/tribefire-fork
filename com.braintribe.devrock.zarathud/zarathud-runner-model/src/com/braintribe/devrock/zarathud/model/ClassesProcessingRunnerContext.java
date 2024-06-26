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
package com.braintribe.devrock.zarathud.model;

import java.util.List;
import java.util.Map;

import com.braintribe.devrock.zarathud.model.context.ConfigurationAspect;
import com.braintribe.devrock.zarathud.model.context.CoreAnalysisAspect;
import com.braintribe.devrock.zarathud.model.context.CoreProcessingAspect;
import com.braintribe.devrock.zarathud.model.context.RatingAspect;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.consumable.Part;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ClassesProcessingRunnerContext extends RatingAspect, CoreAnalysisAspect, CoreProcessingAspect, ConfigurationAspect {
	
	EntityType<ClassesProcessingRunnerContext> T = EntityTypes.T(ClassesProcessingRunnerContext.class);
	
	String terminalClassesDirectoryNames = "terminalClassesDirectoryNames";
	String compiledSolutionsOfClasspath = "compiledSolutionsOfClasspath";
	String nonpackedSolutionsOfClasspath = "nonpackedSolutionsOfClasspath";
	
	/**
	 * @return - the folder to look for the classes of the terminal
	 */
	List<String> getTerminalClassesDirectoryNames();
	void setTerminalClassesDirectoryNames(List<String> value);

	/**
	 * @return - the {@link AnalysisArtifact}s that have a jar as {@link Part}
	 */
	List<AnalysisArtifact> getCompiledSolutionsOfClasspath();
	void setCompiledSolutionsOfClasspath( List<AnalysisArtifact> solution);
	
	
	/**
	 * @return - the {@link AnalysisArtifact}s that have classes-folders
	 */
	Map<String,AnalysisArtifact> getNonpackedSolutionsOfClasspath();
	void setNonpackedSolutionsOfClasspath(Map<String,AnalysisArtifact> value);
 
}
