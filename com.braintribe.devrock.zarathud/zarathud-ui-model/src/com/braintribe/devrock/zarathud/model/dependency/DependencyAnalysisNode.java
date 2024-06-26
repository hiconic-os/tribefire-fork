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
package com.braintribe.devrock.zarathud.model.dependency;

import java.util.List;

import com.braintribe.devrock.zarathud.model.common.ArtifactNode;
import com.braintribe.devrock.zarathud.model.common.FingerPrintRating;
import com.braintribe.devrock.zarathud.model.common.ReferenceNode;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * represents a node in the dependency analysis view
 * @author pit
 *
 */
public interface DependencyAnalysisNode extends ArtifactNode {
	
	EntityType<DependencyAnalysisNode> T = EntityTypes.T(DependencyAnalysisNode.class);

	String kind = "kind";
	String references = "references";
	String incompleteForwardReference = "incompleteForwardReference";
	String redacted = "redacted";
	String overridden = "overridden";
	String rating = "rating";
	
	DependencyKind getKind();
	void setKind(DependencyKind value);

	List<ReferenceNode> getReferences();
	void setReferences(List<ReferenceNode> value);
	
	boolean getIncompleteForwardReference();
	void setIncompleteForwardReference(boolean value);

	boolean getRedacted();
	void setRedacted(boolean value);
	
	boolean getOverridden();
	void setOverridden(boolean value);


	FingerPrintRating getRating();
	void setRating(FingerPrintRating value);

	

}
