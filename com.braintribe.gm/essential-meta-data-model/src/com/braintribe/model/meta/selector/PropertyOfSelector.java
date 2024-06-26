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
package com.braintribe.model.meta.selector;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.data.prompt.Hidden;

/**
 * Evaluates to true iff the resolution context property is also a property of the configured {@link #getEntityType()
 * entityType}. This means we we check if the configured entity type has a property with same name and type. We do not
 * check if it is the exact same property, due to possible multiple inheritance ambiguity.
 * 
 * Example:
 * 
 * Say we have an abstract type X, and we want to make all the properties declared on it's sub-types hidden. We can
 * configure a {@link Hidden} MD on X directly, with {@link NegationSelector negation} of this selector for the type X.
 */
public interface PropertyOfSelector extends MetaDataSelector {

	EntityType<PropertyOfSelector> T = EntityTypes.T(PropertyOfSelector.class);

	GmEntityType getEntityType();
	void setEntityType(GmEntityType entityType);

	boolean getOnlyDeclared();
	void setOnlyDeclared(boolean OnlyDeclared);
	
}
