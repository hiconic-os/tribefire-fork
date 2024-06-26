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
package com.braintribe.devrock.zed.context;

import java.util.Stack;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.console.ConsoleOutputs;
import com.braintribe.console.output.ConfigurableConsoleOutputContainer;
import com.braintribe.devrock.zed.api.context.ConsoleOutputContext;
import com.braintribe.devrock.zed.api.context.ConsoleOutputVerbosity;
import com.braintribe.devrock.zed.forensics.fingerprint.register.FingerPrintRegistry;
import com.braintribe.devrock.zed.forensics.fingerprint.register.RatingRegistry;
import com.braintribe.zarathud.model.data.Artifact;

public class BasicConsoleOutputContext implements ConsoleOutputContext {
	private ConfigurableConsoleOutputContainer consoleOutputContainer;	
	private Stack<Integer> stack = new Stack<>();
	ConsoleOutputVerbosity verbosity = ConsoleOutputVerbosity.terse; 
	Artifact runtimeArtifact;
	FingerPrintRegistry fingerprintRegistry;
	RatingRegistry ratingRegistry;
	
	public BasicConsoleOutputContext() {
		consoleOutputContainer = ConsoleOutputs.configurableSequence();
	}

	@Override
	public ConfigurableConsoleOutputContainer consoleOutputContainer() {		
		return consoleOutputContainer;
	}
	@Configurable @Required
	public void setConsoleOutputContainer(ConfigurableConsoleOutputContainer consoleOutputContainer) {
		this.consoleOutputContainer = consoleOutputContainer;
	}
	@Override
	public int peekIndent() {
		if (stack.isEmpty())
			return 0;
		return stack.peek();
	}
	@Override
	public void pushIndent() {
		if (!stack.isEmpty()) {
			stack.push( stack.peek() + 1);
		}
		else {
			stack.push( 1);
		}
		
	}
	@Override
	public void popIndent() {
		if (!stack.isEmpty()) {
			stack.pop();
		}		
	}

	@Configurable 
	public void setVerbosity(ConsoleOutputVerbosity verbosity) {
		this.verbosity = verbosity;
	}

	@Override
	public ConsoleOutputVerbosity verbosity() {		
		return verbosity;
	}
	@Configurable
	public void setRuntimeArtifact(Artifact runtimeArtifact) {
		this.runtimeArtifact = runtimeArtifact;
	}

	@Override
	public Artifact runtimeArtifact() {		
		return runtimeArtifact;
	}
	
	@Configurable
	public void setFingerprintRegistry(FingerPrintRegistry fingerprintRegistry) {
		this.fingerprintRegistry = fingerprintRegistry;
	}

	@Override
	public FingerPrintRegistry fingerprints() {
		return fingerprintRegistry;
	}

	@Configurable
	public void setRatingRegistry(RatingRegistry ratingRegistry) {
		this.ratingRegistry = ratingRegistry;
	}

	@Override
	public RatingRegistry ratings() {
		return ratingRegistry;
	}
	

	
}
