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

import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.List;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.meta.GmEntityType;

/**
 * 
 */
public class EntityHierarchyNode {

	private static final GenericModelTypeReflection typeReflection = GMF.getTypeReflection();

	private final GmEntityType gmEntityType;
	private final EntityType<?> entityType;
	private final EntityHierarchyNode superNode;

	private final List<EntityHierarchyNode> subNodes = newList();
	private final List<Property> additionalProperties = newList();

	public EntityHierarchyNode(GmEntityType gmEntityType, EntityHierarchyNode superNode) {
		this(gmEntityType, typeReflection.<EntityType<?>> getType(gmEntityType.getTypeSignature()), superNode);
	}

	public EntityHierarchyNode(EntityHierarchyNode node, EntityHierarchyNode superNode) {
		this(node.gmEntityType, node.entityType, superNode);
	}

	private EntityHierarchyNode(GmEntityType gmEntityType, EntityType<?> entityType, EntityHierarchyNode superNode) {
		this.gmEntityType = gmEntityType;
		this.entityType = entityType;
		this.superNode = superNode;

		indexAdditionalProperties();
	}

	private void indexAdditionalProperties() {
		EntityType<?> superType = superNode != null ? typeReflection.getEntityType(superNode.gmEntityType.getTypeSignature()) : null;

		for (Property p: entityType.getProperties()) {
			boolean isInherited = superType != null && superType.findProperty(p.getName()) != null;
			if (!isInherited)
				additionalProperties.add(p);
		}
	}

	public void appendSubNode(EntityHierarchyNode subNode) {
		subNodes.add(subNode);
	}

	public GmEntityType getGmEntityType() {
		return gmEntityType;
	}

	public EntityType<?> getEntityType() {
		return entityType;
	}

	public List<EntityHierarchyNode> getSubNodes() {
		return subNodes;
	}

	public List<Property> getAdditionalProperties() {
		return additionalProperties;
	}

	@Override
	public String toString() {
		return "EntityHierarchyNode [" + entityType.getTypeSignature() + "]";
	}

}
