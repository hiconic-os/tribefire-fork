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

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * represents a reference of a one type to another
 * 
 * @author pit
 *
 */
public interface ReferenceNode extends Node {
	
	EntityType<ReferenceNode> T = EntityTypes.T(ReferenceNode.class);
	
	String source = "source";
	String target = "target";
	String count = "count";

	/**
	 * @return - the {@link EntityNode} that represents the source of the reference
	 */
	EntityNode getSource();
	void setSource(EntityNode value);
	
	/**
	 * @return - the {@link EntityNode} that represents target of the reference
	 */
	EntityNode getTarget();
	void setTarget(EntityNode value);
	
	/**
	 * @return - the numbers of identical references in a dependency reference
	 */
	int getCount();
	void setCount(int value);


}
