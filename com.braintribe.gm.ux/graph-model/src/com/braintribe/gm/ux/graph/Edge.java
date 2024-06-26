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
package com.braintribe.gm.ux.graph;

import java.util.Set;

import com.braintribe.gm.ux.decorator.Decorator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface Edge extends GraphElement {

	EntityType<Edge> T = EntityTypes.T(Edge.class);
	
	Node getOrigin();
	void setOrigin(Node origin);
	
	Node getDestination();
	void setDestination(Node destination);

	Set<Decorator> getOriginDecorators();
	void setOriginDecorators(Set<Decorator> originDecorators);

	Set<Decorator> getDestinationDecorators();
	void setDestinationDecorators(Set<Decorator> destinationDecorators);
	
}
