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
package com.braintribe.model.processing.library.service.expert;

import java.util.List;
import java.util.Set;

import com.braintribe.model.artifact.compiled.CompiledTerminal;
import com.braintribe.model.artifact.consumable.Artifact;
import com.braintribe.model.artifact.essential.ArtifactIdentification;
import com.braintribe.utils.collection.api.MultiMap;

public class DependencyResolutionResult {

	private ArtifactIdentification resolvedArtifact;
	private Set<Artifact> dependencies;
	private MultiMap<Artifact, Artifact> transitiveDependersMap;
	private List<CompiledTerminal> terminalClasspathArtifacts;

	public ArtifactIdentification getResolvedArtifact() {
		return resolvedArtifact;
	}
	public void setResolvedArtifact(ArtifactIdentification resolvedArtifact) {
		this.resolvedArtifact = resolvedArtifact;
	}
	public Set<Artifact> getDependencies() {
		return dependencies;
	}
	public void setDependencies(Set<Artifact> resultingDependencies) {
		this.dependencies = resultingDependencies;
	}
	public MultiMap<Artifact, Artifact> getTransitiveDependersMap() {
		return transitiveDependersMap;
	}
	public void setTransitiveDependersMap(MultiMap<Artifact, Artifact> transitiveDependersMap) {
		this.transitiveDependersMap = transitiveDependersMap;
	}
	public List<CompiledTerminal> getTerminalClasspathArtifacts() {
		return terminalClasspathArtifacts;
	}
	public void setTerminalClasspathArtifacts(List<CompiledTerminal> terminalClasspathArtifacts) {
		this.terminalClasspathArtifacts = terminalClasspathArtifacts;
	}

}
