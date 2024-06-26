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
package com.braintribe.devrock.zarathud.model.extraction.subs;

import com.braintribe.devrock.zarathud.model.common.FingerPrintRating;
import com.braintribe.devrock.zarathud.model.common.FingerPrintRepresentation;
import com.braintribe.devrock.zarathud.model.extraction.ExtractionNode;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.data.ZedEntity;

/**
 * a common base for the different container nodes
 */
public interface ContainerNode extends ExtractionNode {
	
	EntityType<ContainerNode> T = EntityTypes.T(ContainerNode.class);
	
	String contentOrigin = "contentOrigin";
	String contentStructure = "contentStructure";
	String representation = "representation";
	String owner = "owner";
	String rating = "rating";
	
	ContainerComparisonOrigin getContentOrigin();
	void setContentOrigin(ContainerComparisonOrigin value);
	
	ContainerComparisonContent getContentStructure();
	void setContentStructure(ContainerComparisonContent value);

	FingerPrintRepresentation getRepresentation();
	void setRepresentation(FingerPrintRepresentation value);

	ZedEntity getOwner();
	void setOwner(ZedEntity value);
	
	
	/**
	 * @return - rating of the fingerprints below, if any 
	 */
	FingerPrintRating getRating();
	void setRating(FingerPrintRating value);



}
