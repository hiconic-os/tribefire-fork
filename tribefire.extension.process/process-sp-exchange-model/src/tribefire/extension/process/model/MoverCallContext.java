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
package tribefire.extension.process.model;


import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.value.PersistentEntityReference;

/**
 * @author pit, dirk
 *
 */

public interface MoverCallContext extends AbstractCallContext {

	EntityType<MoverCallContext> T = EntityTypes.T(MoverCallContext.class);
	
	void setProcessReference(PersistentEntityReference processReference);
	PersistentEntityReference getProcessReference();

	Object getMoveFrom();
	void setMoveFrom(Object moveFrom);
	
	Object getMoveTo();
	void setMoveTo(Object moveTo);
	
	String getInitiator();
	void setInitiator(String initiator);
}
