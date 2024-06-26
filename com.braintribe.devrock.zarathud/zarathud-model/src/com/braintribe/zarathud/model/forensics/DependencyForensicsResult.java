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
package com.braintribe.zarathud.model.forensics;

import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.forensics.data.ArtifactReference;

/**
 * represents what Zed knows about dependencies of the terminal
 * 
 * @author pit
 *
 */
public interface DependencyForensicsResult extends ForensicsResult {

	EntityType<DependencyForensicsResult> T = EntityTypes.T(DependencyForensicsResult.class);
	
	String declarations = "declarations";
	String requiredDeclarations = "requiredDeclarations";
	String missingDeclarations = "missingDeclarations";
	String excessDeclarations = "excessDeclarations";
	String missingArtifactDetails = "missingArtifactDetails";
	String forwardedReferences = "forwardedReferences";
	String missingForwardDeclarations = "missingForwardDeclarations";
	
	/**
	 * @return - the {@link Artifact}s that were actually declared as dependencies
	 */
	List<Artifact> getDeclarations();
	void setDeclarations(List<Artifact> declaredArtifacts);

	/**
	 * @return - the {@link Artifact}s that are actually referenced in the code
	 */
	List<Artifact> getRequiredDeclarations();
	void setRequiredDeclarations(List<Artifact> actualDependencies);

	/**
	 * @return - the {@link Artifact}s required, but not declared
	 */
	List<Artifact> getMissingDeclarations();
	void setMissingDeclarations(List<Artifact> missingDependencyDeclarations);

	/**
	 * @return - the {@link Artifact}s that are in excess, i.e. declared but not required
	 */
	List<Artifact> getExcessDeclarations();
	void setExcessDeclarations(List<Artifact> excessDependencyDeclarations);

	/**
	 * @return - details in form of {@link ArtifactForensicsResult}s of the missing {@link Artifact}
	 */
	List<ArtifactForensicsResult> getMissingArtifactDetails();
	void setMissingArtifactDetails(List<ArtifactForensicsResult> missingArtifactDetails);
	
	/**
	 * @return - a {@link Map} of {@link ArtifactReference} and {@link Artifact} of foward declarations
	 */
	Map<ArtifactReference,Artifact> getForwardedReferences();
	void setForwardedReferences(Map<ArtifactReference,Artifact> value);
	
	/**
	 * @return - the {@link Artifact}s references in forward-annotations, but not declared as dependencies
	 */
	List<Artifact> getMissingForwardDeclarations();
	void setMissingForwardDeclarations(List<Artifact> missingForwardDependencyDeclarations);
	
}
