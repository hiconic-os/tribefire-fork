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
package com.braintribe.devrock.zarathud.runner;

import java.util.function.Predicate;

import com.braintribe.devrock.zed.api.context.ConsoleOutputVerbosity;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.zarathud.model.data.ZedEntity;

/**
 * simple context for the runner
 * @author pit
 *
 */
public class RunnerContext {
	/**
	 *  required : the terminal
	 */
	public AnalysisArtifact solution;

	/**
	 * defaults : true if model forensics should be done after extraction, default : false; 
	 */
	//public boolean runModelForensics = false;

	
	public boolean allowBraintribeSpecifica = false;
	/**
	 * defaults : output kind, default : OutputKind.console
	 */
	public OutputKind outputKind = OutputKind.console;	
	
	
	/**
	 * defaults : verbosity of output, default : {@link ConsoleOutputVerbosity.verbose}   
	 */
	public ConsoleOutputVerbosity consoleOutputVerbosity = ConsoleOutputVerbosity.verbose;
	
	
	/**
	 * defaults : true if all class references should be listed 
	 */
	public boolean dumpTopArtifactClassReferences = false; 
	
	/**
	 * optional : filter to phase out specific entities
	 */

	public Predicate<ZedEntity> filter;
	
}
