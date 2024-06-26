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

import com.braintribe.model.processing.query.smart.test.builder.repo.RepositoryDriver;
import com.braintribe.model.processing.query.smart.test.model.accessB.EnumEntityB;
import com.braintribe.model.processing.query.smart.test.model.accessB.ItemTypeB;
import com.braintribe.model.processing.query.smart.test.model.smart.ItemType_Identity;

/**
 * 
 */
public class EnumEntityBBuilder extends AbstractBuilder<EnumEntityB, EnumEntityBBuilder> {

	public static EnumEntityBBuilder newInstance(SmartDataBuilder dataBuilder) {
		return new EnumEntityBBuilder(dataBuilder.repoDriver());
	}

	public EnumEntityBBuilder(RepositoryDriver repoDriver) {
		super(EnumEntityB.class, repoDriver);
	}

	public EnumEntityBBuilder name(String value) {
		instance.setName(value);
		return this;
	}

	public EnumEntityBBuilder enumIdentity(ItemType_Identity value) {
		instance.setEnumIdentity(value);
		return this;
	}

	public EnumEntityBBuilder enumAsString(String value) {
		instance.setEnumAsString(value);
		return this;
	}

	public EnumEntityBBuilder enumAsDelegate(ItemTypeB value) {
		instance.setEnumAsDelegate(value);
		return this;
	}

	public EnumEntityBBuilder enumCustomConverted(Integer value) {
		instance.setEnumCustomConverted(value);
		return this;
	}

	public EnumEntityBBuilder enumAsStringSet(String... values) {
		instance.setEnumAsStringSet(asSet(values));
		return this;
	}

	public EnumEntityBBuilder enumAsStringMap(String... values) {
		instance.setEnumAsStringMap(asMap((Object[]) values));
		return this;
	}

	public EnumEntityBBuilder enumAsStringMapKey(Object... values) {
		instance.setEnumAsStringMapKey(asMap(values));
		return this;
	}

	public EnumEntityBBuilder enumAsDelegateMap(ItemTypeB... values) {
		instance.setEnumAsDelegateMap(asMap((Object[]) values));
		return this;
	}

	public EnumEntityBBuilder enumAsDelegateMapKey(Object... values) {
		instance.setEnumAsDelegateMapKey(asMap(values));
		return this;
	}

}
