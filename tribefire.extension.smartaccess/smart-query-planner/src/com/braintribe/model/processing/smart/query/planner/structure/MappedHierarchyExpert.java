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

import static com.braintribe.utils.lcd.CollectionTools2.acquireMap;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;

import java.util.Map;
import java.util.Set;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EmUseCase;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityMapping;

/**
 * This resolves the hierarchy for a single queryable source (see remark at
 * {@link QueryableHierarchyExpert#resolveRelevantQueryableTypesFor(GmEntityType)}). It is similar to
 * {@link ModelHierarchyExpert}, but the hierarchy is considered slightly different. A smart type <tt>s1</tt> is a
 * direct sub-type of smart type <tt>s2</tt> in this sense iff these conditions are fulfilled:
 * 
 * <ol>
 * <li>s2 sub s1</li>
 * <li>if s1 is mapped to d1 and s2 is mapped to d2, then d2 sub d1</li>
 * <li>if there is any type s3 such that s2 sub s3 sub s1, then if s1 is mapped to s1 and s3 to d3, then either d3 does
 * not exist, or not(d3 sub d1)</li>
 * </ol>
 *
 * This notation: <tt>x sub y</tt> means type x is a sub-type of y.
 */
public class MappedHierarchyExpert {

	private final ModelExpert modelExpert;

	private final Map<IncrementalAccess, Map<EmUseCase, Map<EntityHierarchyNode, EntityHierarchyNode>>> staticToMappingNodesCache = newMap();

	public MappedHierarchyExpert(ModelExpert modelExpert) {
		this.modelExpert = modelExpert;
	}

	public EntityHierarchyNode resolveMappedHierarchyRootedAt(EntityHierarchyNode staticNode, IncrementalAccess access, EmUseCase useCase) {
		Map<EmUseCase, Map<EntityHierarchyNode, EntityHierarchyNode>> cacheForAccess = acquireMap(staticToMappingNodesCache, access);
		Map<EntityHierarchyNode, EntityHierarchyNode> staticToMappingNodes = acquireMap(cacheForAccess, useCase);

		EntityHierarchyNode mappedNode = staticToMappingNodes.get(staticNode);

		if (mappedNode == null) {
			mappedNode = resolveMappedHierarchyHelper(staticNode, null, newSet(), access, useCase);
			staticToMappingNodes.put(staticNode, mappedNode);
		}

		return mappedNode;
	}

	private EntityHierarchyNode resolveMappedHierarchyHelper(EntityHierarchyNode staticNode, EntityHierarchyNode mappedParentNode,
			Set<GmEntityType> visitedNodes, IncrementalAccess access, EmUseCase useCase) {

		if (visitedNodes.contains(staticNode.getGmEntityType()))
			return null;

		visitedNodes.add(staticNode.getGmEntityType());

		EntityHierarchyNode mappedNode = new EntityHierarchyNode(staticNode, mappedParentNode);
		addSubNodes(mappedNode, staticNode, visitedNodes, access, useCase);

		return mappedNode;
	}

	private void addSubNodes(EntityHierarchyNode mappedNode, EntityHierarchyNode staticNode, Set<GmEntityType> visitedNodes,
			IncrementalAccess access, EmUseCase useCase) {

		for (EntityHierarchyNode staticSubNode: staticNode.getSubNodes()) {
			if (isMappedToSameHierarchy(mappedNode, staticSubNode, access, useCase)) {
				EntityHierarchyNode mappedSubNode = resolveMappedHierarchyHelper(staticSubNode, mappedNode, visitedNodes, access, useCase);
				if (mappedSubNode != null)
					mappedNode.appendSubNode(mappedSubNode);

			} else {
				addSubNodes(mappedNode, staticSubNode, visitedNodes, access, useCase);
			}
		}
	}

	private boolean isMappedToSameHierarchy(EntityHierarchyNode superNode, EntityHierarchyNode subNode, IncrementalAccess access,
			EmUseCase useCase) {

		EntityMapping superEm = modelExpert.resolveEntityMapping(superNode.getGmEntityType(), access, useCase);
		EntityMapping subEm = modelExpert.resolveEntityMappingIfPossible(subNode.getGmEntityType(), access, useCase); // sub-type might not be mapped at all

		return subEm != null && modelExpert.isFirstAssignableFromSecond(superEm.getDelegateEntityType(), subEm.getDelegateEntityType());
	}
}
