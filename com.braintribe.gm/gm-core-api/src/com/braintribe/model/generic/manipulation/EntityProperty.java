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
package com.braintribe.model.generic.manipulation;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.ForwardDeclaration;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.value.EntityReference;

@ForwardDeclaration("com.braintribe.gm:owner-model")
public interface EntityProperty extends Owner {

	final EntityType<EntityProperty> T = EntityTypes.T(EntityProperty.class);

	// @formatter:off
	EntityReference getReference();
	void setReference(EntityReference reference);
	// @formatter:on

	@Override
	default OwnerType ownerType() {
		return OwnerType.ENTITY_PROPERTY;
	}

	@Override
	default GenericEntity ownerEntity() {
		return getReference();
	}
	
	@Override
	default EntityType<?> ownerEntityType() {
		EntityReference reference = getReference();
		if (reference == null)
			return null;

		return GMF.getTypeReflection().getEntityType(reference.getTypeSignature());
	}

	@Override
	// PGA TODO OPTIMIZE
	default Property property() {
		Property property = null; // localEntityProperty.getProperty();

		// if (property != null)
		// return property;

		EntityType<?> entityType = ownerEntityType();

		if (entityType == null)
			return null;

		property = entityType.findProperty(getPropertyName());

		// localEntityProperty.setProperty(property);

		return property;
	}

}
