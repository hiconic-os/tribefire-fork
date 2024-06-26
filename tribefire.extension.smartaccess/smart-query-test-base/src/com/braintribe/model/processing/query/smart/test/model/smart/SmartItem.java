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

import com.braintribe.model.generic.annotation.ToStringInformation;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessB.ItemB;

/**
 * Mapped to {@link ItemB}
 */
@ToStringInformation("SmartItem[${nameB}]")
public interface SmartItem extends StandardSmartIdentifiable, BasicSmartEntity {
	
	EntityType<SmartItem> T = EntityTypes.T(SmartItem.class);

	String getNameB();
	void setNameB(String nameB);

	LocalizedString getLocalizedNameB();
	void setLocalizedNameB(LocalizedString localizedNameB);

	SmartPersonB getOwnerB();
	void setOwnerB(SmartPersonB ownerB);

	ItemType getItemType();
	void setItemType(ItemType itemType);

	Set<ItemType> getItemTypes();
	void setItemTypes(Set<ItemType> itemTypes);

	Map<ItemType, Integer> getItemTypeWeights();
	void setItemTypeWeights(Map<ItemType, Integer> itemTypeWeights);

}
