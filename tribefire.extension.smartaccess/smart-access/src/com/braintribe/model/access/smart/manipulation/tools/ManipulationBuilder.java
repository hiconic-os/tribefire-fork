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
package com.braintribe.model.access.smart.manipulation.tools;

import java.util.Map;

import com.braintribe.model.generic.manipulation.AddManipulation;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.ClearCollectionManipulation;
import com.braintribe.model.generic.manipulation.DeleteManipulation;
import com.braintribe.model.generic.manipulation.DeleteMode;
import com.braintribe.model.generic.manipulation.EntityProperty;
import com.braintribe.model.generic.manipulation.InstantiationManipulation;
import com.braintribe.model.generic.manipulation.RemoveManipulation;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.generic.value.PreliminaryEntityReference;
import com.braintribe.model.meta.GmProperty;

/**
 * 
 */
public class ManipulationBuilder {

	public static InstantiationManipulation instantiationManipulation(EntityReference entityReference) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.instantiation(entityReference);
	}

	public static DeleteManipulation delete(PersistentEntityReference entityReference, DeleteMode deleteMode) { 
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.delete(entityReference, deleteMode);
	}

	public static ChangeValueManipulation changeValue(EntityProperty owner, Object newValue) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.changeValue(newValue, owner);
	}

	public static AddManipulation add(EntityProperty owner, Object keyForAdd, Object itemToAdd) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.add(keyForAdd, itemToAdd, owner);
	}

	public static AddManipulation add(EntityProperty owner, Map<Object, Object> itemsToAdd) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.add(itemsToAdd, owner);
	}

	public static RemoveManipulation remove(EntityProperty owner, Object keyForRemove, Object itemToRemove) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.remove(keyForRemove, itemToRemove, owner);
	}

	public static RemoveManipulation remove(EntityProperty owner, Map<Object, Object> itemsToRemove) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.remove(itemsToRemove, owner);
	}

	public static ClearCollectionManipulation clear(EntityProperty owner) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.clearManipulation(owner);
	}

	public static PreliminaryEntityReference preliminaryRef(String typeSignature) {
		PreliminaryEntityReference ref = PreliminaryEntityReference.T.create();
		return newReference(ref, typeSignature, Long.valueOf(System.identityHashCode(ref)));
	}

	public static PersistentEntityReference persistentRef(String typeSignature, Object id) {
		return newReference(PersistentEntityReference.T.create(), typeSignature, id);
	}

	private static <T extends EntityReference> T newReference(T ref, String typeSignature, Object id) {
		ref.setTypeSignature(typeSignature);
		ref.setRefId(id);

		return ref;
	}

	public static EntityProperty owner(EntityReference ref, GmProperty gmProperty) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.entityProperty(ref, gmProperty.getName());
	}

	public static EntityProperty owner(EntityReference ref, String propertyName) {
		return com.braintribe.model.generic.manipulation.util.ManipulationBuilder.entityProperty(ref, propertyName);
	}

}
