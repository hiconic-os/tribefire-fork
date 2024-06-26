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
package com.braintribe.model.processing.smart.query.planner.context;

import static com.braintribe.model.processing.query.planner.builder.ValueBuilder.staticValue;
import static com.braintribe.model.processing.smart.query.planner.core.builder.SmartValueBuilder.compositeDiscriminatorBasedSignature;
import static com.braintribe.model.processing.smart.query.planner.core.builder.SmartValueBuilder.simpleDiscriminatorBasedSignature;
import static com.braintribe.model.processing.smart.query.planner.core.builder.SmartValueBuilder.smartEntitySignature;
import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.List;
import java.util.Map;

import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.smart.query.planner.graph.EntitySourceNode;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.DiscriminatedHierarchy;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.DiscriminatedHierarchyNode;
import com.braintribe.model.queryplan.value.Value;

/**
 * @author peter.gazdik
 */
public class SmartEntitySignatureTools {

	public static Value smartEntitySignatureFor(EntitySourceNode sourceNode) {
		if (sourceNode.isPolymorphicHierarchy())
			return signatureForPolymorphicHierarchy(sourceNode);
		else
			return signatureForIsomorphicHierarchy(sourceNode);
	}

	private static Value signatureForPolymorphicHierarchy(EntitySourceNode sourceNode) {
		DiscriminatedHierarchy dh = sourceNode.getDiscriminatorHierarchy();

		List<DiscriminatedHierarchyNode> nodes = dh.getNodes();
		if (nodes.size() == 1)
			return staticValue(nodes.get(0).smartType.getTypeSignature());

		if (dh.isSingleDiscriminatorProperty()) {
			int discriminatorPosition = sourceNode.getSimpleDelegatePropertyPosition(dh.getSingleDiscriminatorProperty().getName());
			return simpleDiscriminatorBasedSignature(discriminatorPosition, dh);

		} else {
			List<Integer> discriminatorPositions = newList();
			for (GmProperty p : dh.getCompositeDiscriminatorProperties())
				discriminatorPositions.add(sourceNode.getSimpleDelegatePropertyPosition(p.getName()));

			return compositeDiscriminatorBasedSignature(discriminatorPositions, dh);
		}
	}

	private static Value signatureForIsomorphicHierarchy(EntitySourceNode sourceNode) {
		Map<String, String> typeRules = sourceNode.acquireTypeRules();

		if (typeRules.isEmpty())
			return staticValue(sourceNode.getSmartGmType().getTypeSignature());
		else
			return smartEntitySignature(sourceNode, typeRules);
	}

}
