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
package com.braintribe.model.processing.query.smart.test.builder;

import static com.braintribe.utils.lcd.CollectionTools2.asMap;
import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.processing.query.smart.test.builder.repo.RepositoryDriver;
import com.braintribe.model.processing.query.smart.test.model.accessB.ItemB;
import com.braintribe.model.processing.query.smart.test.model.accessB.ItemTypeB;

/**
 * 
 */
public class ItemBBuilder extends AbstractBuilder<ItemB, ItemBBuilder> {

	public static ItemBBuilder newInstance(SmartDataBuilder dataBuilder) {
		return new ItemBBuilder(dataBuilder.repoDriver());
	}

	public ItemBBuilder(RepositoryDriver repoDriver) {
		super(ItemB.class, repoDriver);
	}

	public ItemBBuilder nameB(String value) {
		instance.setNameB(value);
		return this;
	}

	public ItemBBuilder type(ItemTypeB type) {
		instance.setItemType(type);
		return this;
	}

	public ItemBBuilder types(ItemTypeB... types) {
		instance.setItemTypes(asSet(types));
		return this;
	}

	public ItemBBuilder singleOwnerName(String value) {
		instance.setSingleOwnerName(value);
		return this;
	}

	public ItemBBuilder multiOwnerName(String value) {
		instance.setMultiOwnerName(value);
		return this;
	}

	public ItemBBuilder sharedOwnerNames(String... value) {
		instance.setSharedOwnerNames(asSet(value));
		return this;
	}

	public ItemBBuilder multiSharedOwnerNames(String... value) {
		instance.setMultiSharedOwnerNames(asSet(value));
		return this;
	}

	public ItemBBuilder localizedName(String... os) {
		RepositoryDriver lsDriver = repoDriver.newRepoDriver();

		LocalizedString ls = lsDriver.newInstance(LocalizedString.class);
		ls.setLocalizedValues(asMap((Object[]) os));
		lsDriver.commit();

		instance.setLocalizedNameB(ls);

		return this;
	}

}
