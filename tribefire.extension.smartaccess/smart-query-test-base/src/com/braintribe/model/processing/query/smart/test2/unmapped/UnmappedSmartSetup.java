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
package com.braintribe.model.processing.query.smart.test2.unmapped;

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static java.util.Collections.emptyList;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetup;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetupBuilder;
import com.braintribe.model.processing.query.smart.test2._common.SmartTestSetupConstants;
import com.braintribe.model.processing.query.smart.test2.unmapped.model.accessA.SimpleUnmappedA;
import com.braintribe.model.processing.query.smart.test2.unmapped.model.smart.UnmappedPropertySmart;
import com.braintribe.model.processing.query.smart.test2.unmapped.model.smart.UnmappedSubTypeSmart;
import com.braintribe.model.processing.query.smart.test2.unmapped.model.smart.UnmappedTopLevelTypeSmart;
import com.braintribe.model.processing.smart.mapping.api.SmartMappingEditor;

/**
 * @author peter.gazdik
 */
public class UnmappedSmartSetup implements SmartTestSetupConstants {

	// @formatter:off
	private static final List<EntityType<?>> aEntities = asList(
			SimpleUnmappedA.T
	);

	private static final List<EntityType<?>> bEntities = emptyList();
	
	private static final List<EntityType<?>> smartEntities = asList(
			SimpleUnmappedA.T,

			UnmappedPropertySmart.T,
			UnmappedSubTypeSmart.T,
			UnmappedTopLevelTypeSmart.T
	);
	// @formatter:on

	public static final SmartModelTestSetup UNMAPPED_SETUP = get();

	private static SmartModelTestSetup get() {
		return SmartModelTestSetupBuilder.build( //
				aEntities, //
				bEntities, //
				smartEntities, //

				EntityType::create, UnmappedSmartSetup::configureMappings, "smart.unmapped");
	}

	private static void configureMappings(SmartMappingEditor mapper) {
		mapper.onEntityType(GenericEntity.T, "root") //
				.allAsIs();

		mapper.onEntityType(UnmappedPropertySmart.T) //
				.entityTo(SimpleUnmappedA.T) //
				.propertyTo(UnmappedPropertySmart.mappedSubName, SimpleUnmappedA.mappedName) //
				.propertiesUnmapped( //
						UnmappedPropertySmart.unmappedSubName, //
						UnmappedPropertySmart.unmappedEntity);

		mapper.onEntityType(UnmappedSubTypeSmart.T) //
				.entityUnmapped();

		mapper.onEntityType(UnmappedTopLevelTypeSmart.T) //
				.entityUnmapped();
	}
}
