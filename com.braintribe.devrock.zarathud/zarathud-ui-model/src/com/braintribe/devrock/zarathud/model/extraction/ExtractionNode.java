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
package com.braintribe.devrock.zarathud.model.extraction;

import java.util.List;
import java.util.Map;

import com.braintribe.devrock.zarathud.model.common.Node;
import com.braintribe.devrock.zarathud.model.extraction.subs.ContainerNode;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * basic extraction node - all involved nodes derive from this
 */
public interface ExtractionNode extends Node {

	
	EntityType<ExtractionNode> T = EntityTypes.T(ExtractionNode.class);

	String name = "name";
	String treepathElements = "treepathElements";
	String parent = "parent";
	String isOwner = "isOwner";
	String isOther = "isOther";
	String containerNodes = "containerNodes";
	
	String getName();
	void setName(String value);
	
	
	List<ExtractionNode> getTreepathElements();
	void setTreepathElements(List<ExtractionNode> value);

	ExtractionNode getParent();
	void setParent(ExtractionNode value);

	boolean getIsOwner();
	void setIsOwner( boolean isOwner);
	
	boolean getIsOther();
	void setIsOther(boolean isOther);

	Map<String,ContainerNode> getContainerNodes();
	void setContainerNodes(Map<String,ContainerNode> value);


}
