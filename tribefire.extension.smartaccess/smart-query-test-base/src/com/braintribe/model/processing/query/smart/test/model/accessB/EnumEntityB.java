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
package com.braintribe.model.processing.query.smart.test.model.accessB;

import java.util.Map;
import java.util.Set;

import com.braintribe.model.accessdeployment.smart.meta.conversion.EnumToSimpleValue;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.smart.ItemType;
import com.braintribe.model.processing.query.smart.test.model.smart.ItemType_Identity;
import com.braintribe.model.processing.query.smart.test.model.smart.ItemType_String;

/**
 * 
 */

public interface EnumEntityB extends StandardIdentifiableB {

	final EntityType<EnumEntityB> T = EntityTypes.T(EnumEntityB.class);

	// @formatter:off
	String getName();
	void setName(String name);

	/** Mapped to {@link ItemType_String} */
	String getEnumAsString();
	void setEnumAsString(String enumAsString);

	/** Mapped to {@link ItemType} */
	ItemTypeB getEnumAsDelegate();
	void setEnumAsDelegate(ItemTypeB enumAsDelegate);

	/** Mapped to {@link ItemType_Identity} */
	ItemType_Identity getEnumIdentity();
	void setEnumIdentity(ItemType_Identity enumIdentity);

	/** Mapped to {@link ItemType} via custom {@link EnumToSimpleValue} conversion */
	Integer getEnumCustomConverted();
	void setEnumCustomConverted(Integer enumCustomConverted);

	Set<String> getEnumAsStringSet();
	void setEnumAsStringSet(Set<String> enumAsStringSet);

	Map<String, String> getEnumAsStringMap();
	void setEnumAsStringMap(Map<String, String> enumAsStringMap);

	Map<String, EnumEntityB> getEnumAsStringMapKey();
	void setEnumAsStringMapKey(Map<String, EnumEntityB> enumAsStringMapKey);

	Map<ItemTypeB, ItemTypeB> getEnumAsDelegateMap();
	void setEnumAsDelegateMap(Map<ItemTypeB, ItemTypeB> enumAsDelegateMap);

	Map<ItemTypeB, EnumEntityB> getEnumAsDelegateMapKey();
	void setEnumAsDelegateMapKey(Map<ItemTypeB, EnumEntityB> enumAsDelegateMapKey);
	// @formatter:on

}
