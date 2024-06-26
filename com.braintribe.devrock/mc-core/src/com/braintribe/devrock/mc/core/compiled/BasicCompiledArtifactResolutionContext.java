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
package com.braintribe.devrock.mc.core.compiled;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.devrock.mc.api.compiled.CompiledArtifactDependencyKind;
import com.braintribe.devrock.mc.api.compiled.CompiledArtifactResolutionContext;
import com.braintribe.devrock.mc.core.declared.commons.HashComparators;
import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.artifact.essential.VersionedArtifactIdentification;

/**
 * @author pit / dirk
 *
 */
public class BasicCompiledArtifactResolutionContext implements CompiledArtifactResolutionContext {
	
	private CompiledArtifactResolutionContext depender;
	private CompiledArtifactDependencyKind kind;
	private VersionedArtifactIdentification artifactIdentification;
	private List<Reason> invalidationReasons = new ArrayList<>();
	
	/**
	 * creates a new {@link BasicCompiledArtifactResolutionContext}
	 * @param artifactIdentification - the {@link VersionedArtifactIdentification} of the pom
	 * @param depender - the ancestor {@link BasicCompiledArtifactResolutionContext}
	 * @param kind - the type of the pom, one of {@link CompiledArtifactDependencyKind}'s values
	 */
	public BasicCompiledArtifactResolutionContext(VersionedArtifactIdentification artifactIdentification, CompiledArtifactResolutionContext depender, CompiledArtifactDependencyKind kind) {
		this.artifactIdentification = artifactIdentification;
		this.depender = depender;
		this.kind = kind;
		
		checkCyclic();
	}
	
	/**
	 * creates a new 'default' {@link BasicCompiledArtifactResolutionContext}
	 * @param artifactIdentification - the {@link VersionedArtifactIdentification} of the pom
	 */
	public BasicCompiledArtifactResolutionContext(VersionedArtifactIdentification artifactIdentification) {
		this.artifactIdentification = artifactIdentification;
		this.kind = CompiledArtifactDependencyKind.STANDARD;
	}
	
	@Override
	public CompiledArtifactDependencyKind kind() {
		return kind;
	}

	@Override
	public CompiledArtifactResolutionContext depender() {
		return depender;
	}
	
	@Override
	public VersionedArtifactIdentification artifactIdentification() {
		return artifactIdentification;
	}
	
	/**
	 * @return - true if the chain of {@link BasicCompiledArtifactResolutionContext} is cyclic, i.e. one entry appears twice
	 */
	private boolean isCyclic() {
		CompiledArtifactResolutionContext context = depender();
		
		while (context != null) {
			if (HashComparators.versionedArtifactIdentification.compare(artifactIdentification, context.artifactIdentification()))
				return true;
			
			context = context.depender();
		}
		
		return false;
	}
	
	/**
	 * check for a cycle and throw an {@link Exception} if it finds one
	 */
	private void checkCyclic() {
		if (isCyclic()) {
			throw new IllegalStateException(getCycleErrorMessage());
		}
	}
	
	/**
	 * construct an error message showing the cycle
	 * @return - a formatted string with error message and path to cycle
	 */
	private String getCycleErrorMessage() {
		CompiledArtifactResolutionContext context = depender();
		StringBuilder builder = new StringBuilder();
		
		builder.append("Invalid parent structure due to cycle in dependency path\n\n");
		
		builder.append(asIdentificationString());
		builder.append("\n");
		
		while (context != null) {
			builder.append("  ");
			builder.append(context.asIdentificationString());
			builder.append("\n");
			
			if (HashComparators.versionedArtifactIdentification.compare(artifactIdentification, context.artifactIdentification())) {
				return builder.toString();
			}
			
			context = context.depender();
		}
		
		throw new IllegalStateException("Unexpected code flow when building cycle error message");
	}
	
	/**
	 *@return - a concatenation of {@link CompiledArtifactDependencyKind}'s value and the {@link VersionedArtifactIdentification}
	 */
	public String asIdentificationString() {
		return kind.name().toLowerCase() + " " + artifactIdentification.asString();
	}

	@Override
	public List<Reason> invalidationReasons() {
		return invalidationReasons;
	}
	
	
	
}
