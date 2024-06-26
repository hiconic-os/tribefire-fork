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
import static com.braintribe.utils.lcd.CollectionTools2.acquireMap;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;
import static com.braintribe.utils.lcd.CollectionTools2.nullSafe;
import static java.util.Collections.emptyMap;

import java.util.Map;
import java.util.Set;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EmUseCase;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityMapping;

/**
 * Component of {@link ModelExpert} which for given {@link GmEntityType smart type} provides rules which say which
 * delegate is mapped to which smart type.
 * <p>
 * TODO there could just be one big map for all the types there are.
 */
class EntityTypeRuleExpert {

	private final ModelExpert modelExpert;
	private final EntityRuleIndex entityRuleIndex = new EntityRuleIndex();

	public EntityTypeRuleExpert(ModelExpert modelExpert) {
		this.modelExpert = modelExpert;
	}

	/**
	 * Returns a map which maps delegate signatures to smart signatures.
	 */
	public Map<String, String> acquireTypeRules(GmEntityType smartType, IncrementalAccess access, EmUseCase useCase) {
		return entityRuleIndex.acquireFor(smartType, access, useCase);
	}

	class EntityRuleIndex {
		Map<IncrementalAccess, Map<EmUseCase, Map<GmEntityType, Map<String, String>>>> cache = newMap();

		public Map<String, String> acquireFor(GmEntityType smartType, IncrementalAccess access, EmUseCase useCase) {
			Map<GmEntityType, Map<String, String>> rulesForType = acquireMap(acquireMap(cache, access), useCase);

			Map<String, String> rules = rulesForType.get(smartType);
			if (rules == null) {
				rules = provideValueFor(smartType, access, useCase);
				rulesForType.put(smartType, rules);
			}

			return rules;
		}

		protected Map<String, String> provideValueFor(GmEntityType smartType, IncrementalAccess access, EmUseCase useCase) {
			Set<GmEntityType> subTypes = modelExpert.getDirectSmartSubTypes(smartType);
			if (subTypes.isEmpty())
				return emptyMap();

			return rulesForHierarchy(smartType, access, useCase);
		}

		private Map<String, String> rulesForHierarchy(GmEntityType smartType, IncrementalAccess access, EmUseCase useCase) {
			EntityHierarchyNode rootNode = modelExpert.resolveMappedHierarchyRootedAt(smartType, access, useCase);
			if (rootNode.getSubNodes().isEmpty())
				return emptyMap();

			Map<String, String> result = newMap();
			if (isInstantiable(smartType))
				addRuleForType(smartType, result, access, useCase);

			addSubTypeRules(rootNode, result, access, useCase);

			return result;
		}

		private void addSubTypeRules(EntityHierarchyNode node, Map<String, String> result, IncrementalAccess access, EmUseCase useCase) {
			for (EntityHierarchyNode subHierarchyNode: nullSafe(node.getSubNodes())) {
				addRuleForType(subHierarchyNode.getGmEntityType(), result, access, useCase);

				addSubTypeRules(subHierarchyNode, result, access, useCase);
			}
		}

		private void addRuleForType(GmEntityType smartType, Map<String, String> result, IncrementalAccess access, EmUseCase useCase) {
			EntityMapping em = modelExpert.resolveEntityMapping(smartType, access, useCase);

			result.put(em.getDelegateEntityType().getTypeSignature(), smartType.getTypeSignature());
		}

	}
}
