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
package com.braintribe.devrock.mc.analytics.dependers;

import java.util.List;

import com.braintribe.model.artifact.compiled.CompiledArtifact;
import com.braintribe.model.artifact.compiled.CompiledDependency;
import com.braintribe.model.artifact.essential.ArtifactIdentification;
import com.braintribe.model.artifact.essential.VersionedArtifactIdentification;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * internal {@link GenericEntity} to contain the 'reverse dependency look-up data)
 * @author pit
 *
 */
public interface DependerAnalysisNode extends GenericEntity {
	
	EntityType<DependerAnalysisNode> T = EntityTypes.T(DependerAnalysisNode.class);
	
	String previousNode = "previousNode";
	String nextNodes = "nextNodes";
	String versionedArtifactIdentification = "versionedArtifactIdentification";
	String initialArtifactIdentification = "initialArtifactIdentification";
	String referencingDependency = "referencingDependency";
	String origin = "origin";

	/**
	 * @return - the {@link DependerAnalysisNode} of the previous link
	 */
	DependerAnalysisNode getPreviousNode();
	void setPreviousNode(DependerAnalysisNode value);
	
	
	/**
	 * @return - the {@link DependerAnalysisNode}s that come next
	 */
	List<DependerAnalysisNode> getNextNodes();
	void setNextNodes(List<DependerAnalysisNode> value);

	
	/**
	 * @return - the {@link VersionedArtifactIdentification} of the dependency owner
	 */
	VersionedArtifactIdentification getVersionedArtifactIdentification();
	void setVersionedArtifactIdentification(VersionedArtifactIdentification value);
	
	
	/**
	 * @return - the {@link ArtifactIdentification} of the root of the chain of nodes
	 */
	ArtifactIdentification getInitialArtifactIdentification();
	void setInitialArtifactIdentification(ArtifactIdentification value);

	/**
	 * @return - the {@link CompiledDependency} that pointed to the searched 
	 */
	CompiledDependency getReferencingDependency();
	void setReferencingDependency(CompiledDependency value);

	/**
	 * @return - the {@link CompiledArtifact}
	 */
	CompiledArtifact getOrigin();
	void setOrigin(CompiledArtifact value);
	
	/**
	 * creates a new {@link DependerAnalysisNode}
	 * @param ca
	 * @param cd
	 * @param previous
	 * @return
	 */
	static DependerAnalysisNode from( CompiledArtifact ca, CompiledDependency cd, DependerAnalysisNode previous) {
		DependerAnalysisNode node = DependerAnalysisNode.T.create();
		node.setPreviousNode(previous);
		node.setOrigin(ca);
		node.setVersionedArtifactIdentification( VersionedArtifactIdentification.create(ca.getGroupId(), ca.getArtifactId(), ca.getVersion().asString()));
		node.setReferencingDependency(cd);			
		return node;
	}
	
	static DependerAnalysisNode from( CompiledArtifact ca) {
		DependerAnalysisNode node = DependerAnalysisNode.T.create();
		node.setOrigin(ca);
		node.setInitialArtifactIdentification( ArtifactIdentification.create(ca.getGroupId(), ca.getArtifactId()));
		return node;
	}
	
	/**
	 *
	 */
	default String asString() {
		String ai; 
		if (getVersionedArtifactIdentification() != null) {
			ai = getVersionedArtifactIdentification().asString();
		}
		else {
			ai = getInitialArtifactIdentification().asString();
		}
	
		if (getReferencingDependency() != null) {
			ai += "->" + getReferencingDependency().asString();
		}	
		return ai;
	}

}
