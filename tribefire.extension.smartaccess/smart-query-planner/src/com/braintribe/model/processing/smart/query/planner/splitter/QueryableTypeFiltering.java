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
package com.braintribe.model.processing.smart.query.planner.splitter;

import static com.braintribe.utils.lcd.CollectionTools2.asSet;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;
import static com.braintribe.utils.lcd.CollectionTools2.nullSafe;
import static java.util.Collections.emptySet;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.processing.smart.query.planner.structure.ModelExpert;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityMapping;
import com.braintribe.model.query.From;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.Restriction;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.conditions.Condition;
import com.braintribe.model.query.conditions.Conjunction;
import com.braintribe.model.query.conditions.ValueComparison;

/**
 * Filters possible queryable types for a given {@link From} based on the information from a query condition.
 * 
 * Based on the mappings, we can say which possible delegate accesses our From is mapped to. But the query might contain
 * a condition that further specifies which delegate to pick, thus allowing us to filter the mapped queryable types.
 * Note that the term queryable type is actually a combination of entity type and delegate access.
 * 
 * @author peter.gazdik
 */
/* package */ class QueryableTypeFiltering {

	private final SelectQuery query;
	private final ModelExpert modelExpert;

	public QueryableTypeFiltering(SelectQuery query, ModelExpert modelExpert) {
		this.query = query;
		this.modelExpert = modelExpert;
	}

	/* package */ List<EntityMapping> filterPlease(From from, List<EntityMapping> queryableTypes) {
		if (queryableTypes.size() < 2)
			return queryableTypes;

		Set<String> specifiedPartitions = findSpecifiedPartitions(from);
		if (specifiedPartitions.isEmpty())
			return queryableTypes;

		return queryableTypes.stream() //
				.filter(em -> containsAny(specifiedPartitions, partitionsOf(em))) //
				.collect(Collectors.toList());
	}

	private static <T> boolean containsAny(Set<T> set, Collection<?> collection) {
		for (Object o : nullSafe(collection))
			if (set.contains(o))
				return true;
		return false;
	}

	private Set<String> findSpecifiedPartitions(From from) {
		Restriction r = query.getRestriction();
		if (r == null)
			return emptySet();

		Condition c = r.getCondition();
		if (c == null)
			return emptySet();

		return findPartitionsIn(from, c);
	}

	private Set<String> findPartitionsIn(From from, Condition c) {
		switch (c.conditionType()) {
			case conjunction:
				return findPartitionsInConjunction(from, (Conjunction) c);
			case valueComparison:
				return findPartitionsInVc(from, (ValueComparison) c);
			default:
				return emptySet();
		}
	}

	private Set<String> findPartitionsInConjunction(From from, Conjunction conj) {
		for (Condition c : conj.getOperands()) {
			Set<String> partitions = findPartitionsIn(from, c);
			if (!partitions.isEmpty())
				return partitions;
		}
		return emptySet();
	}

	private Set<String> findPartitionsInVc(From from, ValueComparison vc) {
		switch (vc.getOperator()) {
			case contains:
				return findPartitions_In(from, vc.getRightOperand(), vc.getLeftOperand());
			case equal:
				return findPartitions_Eq(from, vc.getLeftOperand(), vc.getRightOperand());
			case in:
				return findPartitions_In(from, vc.getLeftOperand(), vc.getRightOperand());
			default:
				return emptySet();
		}
	}

	// entity = reference
	// entity.partition = 'part'
	// entity in Set<EntityReference>
	// entity.partitioni in Set<String>

	private Set<String> findPartitions_In(From from, Object memberOperand, Object collectionOperand) {
		if (!(collectionOperand instanceof Collection))
			return emptySet();

		if (isFrom(from, memberOperand))
			return collectPartitionsFromRefSet(collectionOperand);

		if (isFromPartition(from, memberOperand))
			return ensureSet(collectionOperand);
		else
			return emptySet();
	}

	private Set<String> collectPartitionsFromRefSet(Object refSet) {
		Set<String> result = newSet();

		for (Object o : (Collection<?>) refSet)
			// just in case, but nothing else makes sense inside a collection
			if (o instanceof PersistentEntityReference)
				result.add(((PersistentEntityReference) o).getRefPartition());

		return result;

	}

	private Set<String> ensureSet(Object o) {
		return o instanceof Set ? (Set<String>) o : newSet((Collection<String>) o);
	}

	private Set<String> findPartitions_Eq(From from, Object leftOperand, Object rightOperand) {
		if (isFrom(from, leftOperand)) {
			if (rightOperand instanceof PersistentEntityReference)
				return asSet(((PersistentEntityReference) rightOperand).getRefPartition());
			else
				return emptySet();
		}

		if (isFromPartition(from, leftOperand) && rightOperand instanceof String)
			return asSet((String) rightOperand);
		else
			return emptySet();
	}

	private boolean isFrom(From from, Object operand) {
		if (from == operand)
			return true;

		if (operand instanceof PropertyOperand) {
			PropertyOperand po = (PropertyOperand) operand;
			return po.getPropertyName() == null && from == po.getSource();
		}

		return false;
	}

	private boolean isFromPartition(From from, Object operand) {
		if (operand instanceof PropertyOperand) {
			PropertyOperand po = (PropertyOperand) operand;
			return from == po.getSource() && GenericEntity.partition.equals(po.getPropertyName());
		}

		return false;
	}

	private Set<String> partitionsOf(EntityMapping em) {
		return modelExpert.getPartitions(em.getAccess());
	}

}
