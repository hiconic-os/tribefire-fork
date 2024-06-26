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
package com.braintribe.model.processing.query.smart.test2.shared;

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.function.Consumer;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetup;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetupBuilder;
import com.braintribe.model.processing.query.smart.test2._common.SmartTestSetupConstants;
import com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedEntity;
import com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedFile;
import com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedFileDescriptor;
import com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedSource;
import com.braintribe.model.processing.smart.mapping.api.SmartMappingEditor;

/**
 * @author peter.gazdik
 */
/* package */ class _SharedSetupBase implements SmartTestSetupConstants {

	// @formatter:off
	private static final List<EntityType<?>> sharedEntities = asList(
			SharedEntity.T,
			SharedFile.T,
			SharedFileDescriptor.T,
			SharedSource.T
	);

	private static final List<EntityType<?>> aEntities = asList();
	
	private static final List<EntityType<?>> bEntities = emptyList();
	
	private static final List<EntityType<?>> smartEntities = asList(
			SharedEntity.T,
			SharedFile.T,
			SharedFileDescriptor.T,
			SharedSource.T
	);
	// @formatter:on

	/* package */ static SmartModelTestSetup get(Consumer<SmartMappingEditor> mappingConfigurer, String globalIdPrefix) {
		return SmartModelTestSetupBuilder.build( //
				sharedEntities, //
				aEntities, //
				bEntities, //
				smartEntities, //

				EntityType::create, mappingConfigurer, globalIdPrefix);
	}

}
