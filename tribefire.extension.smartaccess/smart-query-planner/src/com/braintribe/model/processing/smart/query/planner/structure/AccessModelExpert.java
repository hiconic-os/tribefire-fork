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
package com.braintribe.model.processing.smart.query.planner.structure;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Collections;
import java.util.Map;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.index.ConcurrentCachedIndex;
import com.braintribe.model.processing.meta.oracle.BasicModelOracle;
import com.braintribe.model.processing.meta.oracle.ModelOracle;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlannerException;
import com.braintribe.utils.lcd.CollectionTools2;

/**
 * Part of StaticModelExpert
 */
public class AccessModelExpert {

	// ##################################################################
	// ## . . . . . GmEntityType for given IncrementalAccess . . . . . ##
	// ##################################################################

	/**
	 * @throws SmartQueryPlannerException
	 *             iff no entity is found for given access.
	 */
	public GmEntityType resolveEntityType(String signature, IncrementalAccess access) {
		return accessIndex.acquireFor(access).getEntityTypeOracle(signature).asGmEntityType();
	}

	public boolean containsEntityType(String signature, IncrementalAccess access) {
		return accessIndex.acquireFor(access).findEntityTypeOracle(signature) != null;
	}

	private final AccessIndex accessIndex = new AccessIndex();

	private static class AccessIndex extends ConcurrentCachedIndex<IncrementalAccess, ModelOracle> {
		@Override
		protected ModelOracle provideValueFor(IncrementalAccess access) {
			return new BasicModelOracle(access.getMetaModel());
		}
	}

	// #############################################################################################
	// ## . . . . . . . All GmProperties for given GmEntityType (including inherited) . . . . . . ##
	// #############################################################################################

	public Map<String, GmProperty> getAllProperties(GmEntityType entityType) {
		return allPropertiesIndex.acquireFor(entityType);
	}

	private final AllPropertiesIndex allPropertiesIndex = new AllPropertiesIndex();

	static class AllPropertiesIndex extends ConcurrentCachedIndex<GmEntityType, Map<String, GmProperty>> {
		@Override
		protected Map<String, GmProperty> provideValueFor(GmEntityType gmEntityType) {
			Map<String, GmProperty> result = newMap();
			mapProperties(result, gmEntityType);

			return Collections.unmodifiableMap(result);
		}

		private void mapProperties(Map<String, GmProperty> map, GmEntityType type) {
			for (GmEntityType superType : CollectionTools2.nullSafe(type.getSuperTypes()))
				mapProperties(map, superType);

			for (GmProperty property : CollectionTools2.nullSafe(type.getProperties()))
				map.put(property.getName(), property);
		}
	}
}
