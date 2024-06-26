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
package com.braintribe.devrock.zarathud.runner.impl.builders;

import java.util.function.Supplier;

import com.braintribe.devrock.zarathud.model.JarProcessingRunnerContext;
import com.braintribe.devrock.zarathud.model.context.ConsoleOutputVerbosity;
import com.braintribe.devrock.zarathud.wirings.core.context.CoreContext;
import com.braintribe.zarathud.model.data.Artifact;

public class JarCoreContextBuilder implements Supplier<CoreContext> {
	private JarProcessingRunnerContext context;
	
	public JarCoreContextBuilder( JarProcessingRunnerContext context) {
		this.context = context;
	}

	@Override
	public CoreContext get() {
		CoreContext coreContext = new CoreContext();
		
		coreContext.setClasspath( context.getClasspath());
		coreContext.setCompiledSolutionsOfClasspath( context.getCompiledSolutionsOfClasspath());
		coreContext.setDependencies( context.getDependencies());
		coreContext.setCustomRatingsResource( context.getCustomRatingsResource());
		coreContext.setPullRequestRatingsResource(context.getPullRequestRatingsResource());
		
		String terminal = context.getTerminal();			
		Artifact artifact = Artifact.parse(terminal);			
		coreContext.setTerminalArtifact( artifact);
				
		ConsoleOutputVerbosity consoleOutputVerbosity = context.getConsoleOutputVerbosity();
		if (consoleOutputVerbosity != null) {
			coreContext.setConsoleOutputVerbosity(com.braintribe.devrock.zed.api.context.ConsoleOutputVerbosity.valueOf( consoleOutputVerbosity.toString()));
		}		
		
		return coreContext;
	}

}
