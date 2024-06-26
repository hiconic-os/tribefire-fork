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
package com.braintribe.model.artifact.weeder;

import java.util.Set;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * helper entity for the weeder - analyze solution networks for unneeded solutions
 * @author Pit
 *
 */

public interface Node extends StandardIdentifiable{
		
	final EntityType<Node> T = EntityTypes.T(Node.class);
	
	public void setOverridden( boolean flag);
	public boolean getOverridden();
	
	public void setParentNode( Node node);
	public Node getParentNode();
	
	public void setChildNodes( Set<Node> childNodes);
	public Set<Node> getChildNodes();
}
