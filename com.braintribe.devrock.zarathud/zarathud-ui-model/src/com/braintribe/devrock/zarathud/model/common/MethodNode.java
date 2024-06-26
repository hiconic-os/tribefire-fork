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
package com.braintribe.devrock.zarathud.model.common;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface MethodNode extends Node, HasRelatedFingerPrint {
	
	EntityType<MethodNode> T = EntityTypes.T(MethodNode.class);
	String name = "name";
	String returnType = "returnType";
	String parameterTypes = "parameterTypes";
	

	/**
	 * @return - the name of the method
	 */
	String getName();
	void setName(String value);

	/**
	 * @return - the {@link EntityNode} of the return type 
	 */
	EntityNode getReturnType();
	void setReturnType(EntityNode value);

	/**
	 * @return - the {@link List} of {@link EntityNode} making up the parameter
	 */
	List<EntityNode> getParameterTypes();
	void setParameterTypes(List<EntityNode> value);

	
}
