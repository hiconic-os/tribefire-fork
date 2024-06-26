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

import static com.braintribe.model.processing.smart.query.planner.tools.SmartQueryPlannerTools.isInstantiable;
import static com.braintribe.model.processing.smart.query.planner.tools.SmartQueryPlannerTools.newConcurrentMap;
import static com.braintribe.utils.lcd.CollectionTools2.acquireSet;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;
import static com.braintribe.utils.lcd.CollectionTools2.nullSafe;
import static java.util.Collections.emptySet;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.index.ConcurrentCachedIndex;
import com.braintribe.model.processing.meta.oracle.ModelOracle;

/**
 * 
 */
public class ModelHierarchyExpert {

	private final Map<GmEntityType, Set<GmEntityType>> directSubTypes = newMap();
	private final Map<GmEntityType, Set<GmEntityType>> allSuperTypes = newConcurrentMap();

	public ModelHierarchyExpert(ModelOracle modelOracle) {
		indexHierarchy(modelOracle);
	}

	// I know I could do it without the "visited" set, but just in case the configuration is not 100% OK.
	private void indexHierarchy(ModelOracle modelOracle) {
		Set<GmEntityType> visited = newSet();

		Stream<GmEntityType> gmEntityTypesStream = modelOracle.getTypes().onlyEntities().asGmTypes();
		for (GmEntityType gmEntityType : (Iterable<GmEntityType>) gmEntityTypesStream::iterator)
			indexHierarchy(gmEntityType, visited);
	}

	private void indexHierarchy(GmEntityType gmEntityType, Set<GmEntityType> visited) {
		if (visited.contains(gmEntityType))
			return;

		visited.add(gmEntityType);

		for (GmEntityType superType : nullSafe(gmEntityType.getSuperTypes())) {
			acquireSet(directSubTypes, superType).add(gmEntityType);

			indexHierarchy(superType, visited);
		}
	}

	/**
	 * @return sub-types, according to {@link GmMetaModel} provided via constructor. Note that an entity is not
	 *         considered it's own sub-type.
	 */
	public Set<GmEntityType> getDirectSubTypes(GmEntityType entityType) {
		return nullSafe(directSubTypes.get(entityType));
	}

	/** This works with any types, even if they are not in the meta-model which was used for initialization. */
	public boolean isFirstAssignableFromSecond(GmEntityType et1, GmEntityType et2) {
		return et1 == et2 || acquireSuperTypes(et2).contains(et1);
	}

	private Set<GmEntityType> acquireSuperTypes(GmEntityType et) {
		Set<GmEntityType> result = allSuperTypes.get(et);

		if (result == null) {
			result = newSet();

			for (GmEntityType superType : nullSafe(et.getSuperTypes())) {
				result.add(superType);
				result.addAll(acquireSuperTypes(superType));
			}

			if (result.isEmpty())
				result = emptySet();

			allSuperTypes.put(et, result);
		}

		return result;
	}

	private final RootedHierarchyIndex rootedHierarchyIndex = new RootedHierarchyIndex();

	/**
	 * Returns hierarchy rooted at given smart type, containing only it's instantiable sub-types. Non-instantiable
	 * sub-types are ignored, and it's instantiable sub-types are represented as sub-types of the abstract entity super
	 * types.
	 */
	public EntityHierarchyNode resolveHierarchyRootedAt(GmEntityType smartType) {
		return rootedHierarchyIndex.acquireFor(smartType);
	}

	class RootedHierarchyIndex extends ConcurrentCachedIndex<GmEntityType, EntityHierarchyNode> {

		@Override
		protected EntityHierarchyNode provideValueFor(GmEntityType rootType) {
			EntityHierarchyNode rootNode = new EntityHierarchyNode(rootType, null);
			processInstantiableSubTypes(rootNode, rootType);

			return rootNode;
		}

		private void processInstantiableSubTypes(EntityHierarchyNode entityNode, GmEntityType entityType) {
			for (GmEntityType subType : nullSafe(getDirectSubTypes(entityType))) {
				if (isInstantiable(subType)) {
					EntityHierarchyNode subNode = new EntityHierarchyNode(subType, entityNode);
					entityNode.appendSubNode(subNode);

					processInstantiableSubTypes(subNode, subType);

				} else {
					processInstantiableSubTypes(entityNode, subType);
				}
			}
		}
	}

}
