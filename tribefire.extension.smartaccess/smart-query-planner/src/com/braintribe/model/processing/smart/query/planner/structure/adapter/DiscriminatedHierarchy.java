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
package com.braintribe.model.processing.smart.query.planner.structure.adapter;

import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.accessdeployment.smart.meta.PolymorphicBaseEntityAssignment;
import com.braintribe.model.accessdeployment.smart.meta.PolymorphicEntityAssignment;
import com.braintribe.model.accessdeployment.smart.meta.discriminator.CompositeDiscriminator;
import com.braintribe.model.accessdeployment.smart.meta.discriminator.Discriminator;
import com.braintribe.model.accessdeployment.smart.meta.discriminator.SimpleDiscriminator;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.record.ListRecord;
import com.braintribe.model.smartqueryplan.value.CompositeDiscriminatorSignatureRule;

/**
 * Descriptor for hierarchy rooted at a source where the corresponding entity is mapped via {@link PolymorphicEntityAssignment}.
 * 
 * @author peter.gazdik
 */
public class DiscriminatedHierarchy {

	private final GmEntityType smartType;
	private final PolymorphicBaseEntityAssignment base;
	private final Discriminator discriminator;
	private final List<DiscriminatedHierarchyNode> nodes;

	private final Map<Object, String> simpleSignatureMapping;
	private final List<CompositeDiscriminatorSignatureRule> compositeSignatureRules;

	private final Set<Object> allDiscriminatorValues; // we need this as set cause it ends up in the delegated query "in" condition
	private final Map<String, Object> smartSignatureToDiscriminatorValue;

	/**
	 * @param nodes
	 *            one node for each instantiable sub-type of given <tt>smartType</tt>
	 */
	public DiscriminatedHierarchy(GmEntityType smartType, PolymorphicBaseEntityAssignment base, List<DiscriminatedHierarchyNode> nodes) {
		this.smartType = smartType;
		this.base = base;
		this.discriminator = base.getDiscriminator();
		this.nodes = nodes;

		this.simpleSignatureMapping = simpleSignatureMapping();
		this.compositeSignatureRules = compositeSignatureRules();

		this.allDiscriminatorValues = allDiscriminatorValues();
		this.smartSignatureToDiscriminatorValue = smartSignatureToDiscriminatorValue();
	}

	private Map<Object, String> simpleSignatureMapping() {
		if (!isSingleDiscriminatorProperty()) {
			return null;
		}

		Map<Object, String> result = newMap();
		for (DiscriminatedHierarchyNode node: nodes) {
			result.put(node.assignment.getDiscriminatorValue(), node.smartType.getTypeSignature());
		}

		return unmodifiableMap(result);
	}

	private List<CompositeDiscriminatorSignatureRule> compositeSignatureRules() {
		if (isSingleDiscriminatorProperty()) {
			return null;
		}

		List<CompositeDiscriminatorSignatureRule> result = newList();
		for (DiscriminatedHierarchyNode node: nodes) {
			ListRecord record = (ListRecord) node.assignment.getDiscriminatorValue();

			CompositeDiscriminatorSignatureRule rule = CompositeDiscriminatorSignatureRule.T.createPlain();
			rule.setDiscriminatorValues(record.getValues());
			rule.setSignature(node.smartType.getTypeSignature());

			result.add(rule);
		}

		return unmodifiableList(result);
	}

	private Set<Object> allDiscriminatorValues() {
		Set<Object> result = newSet();
		for (DiscriminatedHierarchyNode node: nodes) {
			result.add(node.assignment.getDiscriminatorValue());
		}

		return unmodifiableSet(result);
	}

	private Map<String, Object> smartSignatureToDiscriminatorValue() {
		Map<String, Object> result = newMap();
		for (DiscriminatedHierarchyNode node: nodes) {
			result.put(node.smartType.getTypeSignature(), node.assignment.getDiscriminatorValue());
		}

		return unmodifiableMap(result);
	}

	public GmEntityType getSmartType() {
		return smartType;
	}

	public GmEntityType getDelegateEntityType() {
		return base.getEntityType();
	}

	/** Returns a list of nodes, one node for each instantiable sub-type of out {@link #getSmartType() smartType}. */
	public List<DiscriminatedHierarchyNode> getNodes() {
		return nodes;
	}

	public boolean isSingleDiscriminatorProperty() {
		return discriminator instanceof SimpleDiscriminator;
	}

	// both simple and composite
	public List<GmProperty> getDiscriminatorProperties() {
		return isSingleDiscriminatorProperty() ? Arrays.asList(getSingleDiscriminatorProperty()) : getCompositeDiscriminatorProperties();
	}

	public Object getDiscriminatorForSmartSignature(String typeSignature) {
		return smartSignatureToDiscriminatorValue.get(typeSignature);
	}

	// #########################################################
	// ## . . . . . . SimpleDiscriminator methods . . . . . . ##
	// #########################################################

	public Set<Object> getAllSimpleDiscriminatorValues() {
		return allDiscriminatorValues;
	}

	public GmProperty getSingleDiscriminatorProperty() {
		return ((SimpleDiscriminator) discriminator).getProperty();
	}

	public Map<Object, String> getSimpleSignatureMapping() {
		return simpleSignatureMapping;
	}

	// #########################################################
	// ## . . . . . CompositeDiscriminator methods . . . . . .##
	// #########################################################

	@SuppressWarnings("unchecked")
	public Set<ListRecord> getAllCompositeDiscriminatorValues() {
		return (Set<ListRecord>) (Object) allDiscriminatorValues;
	}

	public List<GmProperty> getCompositeDiscriminatorProperties() {
		return ((CompositeDiscriminator) discriminator).getProperties();
	}

	public List<CompositeDiscriminatorSignatureRule> getCompositeSignatureRules() {
		return compositeSignatureRules;
	}

}
