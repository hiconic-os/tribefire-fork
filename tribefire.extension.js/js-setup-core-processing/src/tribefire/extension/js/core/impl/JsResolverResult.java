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
package tribefire.extension.js.core.impl;

import java.io.File;
import java.util.Set;

import com.braintribe.model.artifact.Solution;

public class JsResolverResult {
	
	private Solution terminalSolution;
	private Set<File> resolvedLibs;
	private Set<Solution> resolvedSolutions;
	
	public JsResolverResult(Solution terminalSolution, Set<File> resolvedLibs, Set<Solution> resolvedSolutions) {
		super();
		this.terminalSolution = terminalSolution;
		this.resolvedLibs = resolvedLibs;
		this.resolvedSolutions = resolvedSolutions;
	}

	public Solution getTerminalSolution() {
		return terminalSolution;
	}
	
	public Set<File> getResolvedLibs() {
		return resolvedLibs;
	}
	
	public Set<Solution> getResolvedSolutions() {
		return resolvedSolutions;
	}
	
}
