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
package com.braintribe.model.processing.query.smart.test2.basic;

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static java.util.Collections.emptyList;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetup;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetupBuilder;
import com.braintribe.model.processing.query.smart.test2._common.SmartTestSetupConstants;
import com.braintribe.model.processing.query.smart.test2.basic.model.accessA.SimplePropertiesEntityA;
import com.braintribe.model.processing.query.smart.test2.basic.model.smart.SmartSimplePropertiesEntityA;
import com.braintribe.model.processing.smart.mapping.api.SmartMappingEditor;

/**
 * @author peter.gazdik
 */
public class BasicSmartSetup implements SmartTestSetupConstants {

	// @formatter:off
	private static final List<EntityType<?>> aEntities = asList(
			SimplePropertiesEntityA.T
	);

	private static final List<EntityType<?>> bEntities = emptyList();
	
	private static final List<EntityType<?>> smartEntities = asList(
			SmartSimplePropertiesEntityA.T
	);
	// @formatter:on

	public static final SmartModelTestSetup BASIC_SETUP = get();

	private static SmartModelTestSetup get() {
		return SmartModelTestSetupBuilder.build( //
				aEntities, //
				bEntities, //
				smartEntities, //

				EntityType::create, BasicSmartSetup::configureMappings, "smart.basic");
	}

	private static void configureMappings(SmartMappingEditor mapper) {
		mapper.onEntityType(GenericEntity.T, "root") //
				.allAsIs();

		mapper.onEntityType(SmartSimplePropertiesEntityA.T) //
				.forDelegate(SmartTestSetupConstants.accessIdA) //
				.entityTo(SimplePropertiesEntityA.T) //
				.propertyTo(SmartSimplePropertiesEntityA.smartString, SimplePropertiesEntityA.string) //
				.propertyTo(SmartSimplePropertiesEntityA.smartDate, SimplePropertiesEntityA.date);
	}
}
