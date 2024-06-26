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
package com.braintribe.devrock.eclipse.model.resolution.nodes;

import java.util.List;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * represents a {@link Reason} (and an Origination)
 * @author pit
 *
 */
public interface ReasonNode extends Node {
	EntityType<ReasonNode> T = EntityTypes.T(ReasonNode.class);

	String type = "type";
	String classification = "classification";
	String text = "text";
	String children = "children";
	String customProperties = "customProperties";
	
	
	/**
	 * @return - the type of the {@link Reason} (its EntityType)
	 */
	String getType();
	void setType(String value);
	
	/**
	 * @return -  the {@link ReasonClassification}, mainly used to assign an image to node in the viewers
	 */
	ReasonClassification getClassification();
	void setClassification(ReasonClassification value);

	
	/**
	 * @return - the text of the {@link Reason} as {@link String}
	 */
	String getText();
	void setText(String value);	

	/**
	 * @return - a {@link List} of {@link ReasonNode} standing for the 'custom properties' - not supported yet
	 */
	List<ReasonNode> getCustomProperties();
	void setCustomProperties(List<ReasonNode> value);
	
	/**
	 * @return - the actual {@link Reason}
	 */
	Reason getBackingReason();
	void setBackingReason(Reason value);

	
}
