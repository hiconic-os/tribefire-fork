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
package com.braintribe.devrock.zarathud.model.model;

import java.util.List;

import com.braintribe.devrock.zarathud.model.common.HasRelatedFingerPrint;
import com.braintribe.devrock.zarathud.model.common.MethodNode;
import com.braintribe.devrock.zarathud.model.common.Node;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface GenericEntityNode extends Node, HasRelatedFingerPrint {
	
	EntityType<GenericEntityNode> T = EntityTypes.T(GenericEntityNode.class);
	
	String properties = "properties";
	String name = "name";
	String conformMethods = "conformMethods";
	String unconformMethods = "unconformMethods";

	/**
	 * @return - name of the GenericEntity
	 */
	String getName();
	void setName(String value);

	/**
	 * @return - the {@link List} of properties
	 */
	List<PropertyNode> getProperties();
	void setProperties(List<PropertyNode> value);

	/**
	 * @return - {@link List} of methods that are compatible with a GenericEntity
	 */
	List<MethodNode> getConformMethods();
	void setConformMethods(List<MethodNode> value);
	
	/**
	 * @return - {@link List} of methods that are incompatible with a GenericEntity
	 */
	List<MethodNode> getNonConformMethods();
	void setNonConformMethods(List<MethodNode> value);


}
