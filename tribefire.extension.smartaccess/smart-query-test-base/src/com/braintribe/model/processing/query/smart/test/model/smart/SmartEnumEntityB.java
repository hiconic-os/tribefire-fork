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
package com.braintribe.model.processing.query.smart.test.model.smart;

import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessB.EnumEntityB;

/**
 * Mapped to {@link EnumEntityB}
 */
public interface SmartEnumEntityB extends StandardSmartIdentifiable {
	
	EntityType<SmartEnumEntityB> T = EntityTypes.T(SmartEnumEntityB.class);

	String getName();
	void setName(String name);

	ItemType_String getEnumAsString();
	void setEnumAsString(ItemType_String enumAsString);

	ItemType getEnumAsDelegate();
	void setEnumAsDelegate(ItemType enumAsDelegate);

	ItemType_Identity getEnumIdentity();
	void setEnumIdentity(ItemType_Identity enumIdentity);

	ItemType getEnumCustomConverted();
	void setEnumCustomConverted(ItemType enumCustomConverted);

	Set<ItemType_String> getEnumAsStringSet();
	void setEnumAsStringSet(Set<ItemType_String> enumAsStringSet);

	Map<ItemType_String, ItemType_String> getEnumAsStringMap();
	void setEnumAsStringMap(Map<ItemType_String, ItemType_String> enumAsStringMap);

	Map<ItemType_String, SmartEnumEntityB> getEnumAsStringMapKey();

	/* this is different than previous, uses EntitySourceNode, not SimpleValueNode */
	void setEnumAsStringMapKey(Map<ItemType_String, SmartEnumEntityB> enumAsStringMapKey);

	Map<ItemType, ItemType> getEnumAsDelegateMap();
	void setEnumAsDelegateMap(Map<ItemType, ItemType> enumAsDelegateMap);

	/* this is different than previous, uses EntitySourceNode, not SimpleValueNode */
	Map<ItemType, SmartEnumEntityB> getEnumAsDelegateMapKey();
	void setEnumAsDelegateMapKey(Map<ItemType, SmartEnumEntityB> enumAsDelegateMapKey);

}
