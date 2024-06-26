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
package com.braintribe.model.processing.query.smart.test.check;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.test.check.AbstractEntityAssemblyChecker;
import com.braintribe.model.processing.query.test.check.AbstractQueryPlanAssemblyChecker;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.SortCriterion;
import com.braintribe.model.queryplan.value.StaticValue;
import com.braintribe.model.smartqueryplan.ScalarMapping;
import com.braintribe.model.smartqueryplan.functions.AssembleEntity;
import com.braintribe.model.smartqueryplan.functions.DiscriminatorValue;
import com.braintribe.model.smartqueryplan.set.DelegateQueryAsIs;
import com.braintribe.model.smartqueryplan.set.DelegateQueryJoin;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;
import com.braintribe.model.smartqueryplan.set.StaticTuple;
import com.braintribe.model.smartqueryplan.set.StaticTuples;

/**
 * 
 */
public class SmartEntityAssemblyChecker extends AbstractQueryPlanAssemblyChecker<SmartEntityAssemblyChecker> {

	public SmartEntityAssemblyChecker(GenericEntity root) {
		super(root);
	}

	public SmartEntityAssemblyChecker isDelegateQueryAsIs(String externalId) {
		return hasType(DelegateQueryAsIs.T).whereProperty("delegateAccess").isDelegateAccess(externalId).close();
	}

	public SmartEntityAssemblyChecker isDelegateQuerySet(String externalId) {
		return hasType(DelegateQuerySet.T).whereProperty("delegateAccess").isDelegateAccess(externalId).close();
	}

	public SmartEntityAssemblyChecker isDelegateAccess(String externalId) {
		return hasType(IncrementalAccess.T).whereProperty("externalId").is_(externalId);
	}

	public DelegateQueryEntityAssemblyChecker<SmartEntityAssemblyChecker> whereDelegateQuery() {
		SelectQuery query = propertyValue("delegateQuery");
		return new DelegateQueryEntityAssemblyChecker<>(this, query);
	}

	public SmartEntityAssemblyChecker isAssembleEntity(EntityType<?> entityType) {
		return hasType(AssembleEntity.T).whereProperty("entitySignature").is_(entityType.getTypeSignature());
	}

	public SmartEntityAssemblyChecker isSortCriteriaAndValue(boolean descending) {
		return hasType(SortCriterion.T).whereProperty("descending").is_(descending).whereValue();
	}

	public SmartEntityAssemblyChecker isScalarMappingAndValue(int tupleComponentIndex) {
		return hasType(ScalarMapping.T) //
				.whereProperty("tupleComponentIndex").is_(tupleComponentIndex) //
				.whereProperty("sourceValue");
	}

	public AbstractEntityAssemblyChecker<SmartEntityAssemblyChecker> isStaticValueAndValue() {
		return hasType(StaticValue.T).whereProperty("value");
	}

	public SmartEntityAssemblyChecker isStaticTuples(int numberOfStaticTuples) {
		return hasType(StaticTuples.T).whereProperty("tuples").isListWithSize(numberOfStaticTuples);
	}

	public SmartEntityAssemblyChecker isStaticTupleWithScalarMappings(int numberOfScalarMappings) {
		return hasType(StaticTuple.T).whereProperty("scalarMappings").isListWithSize(numberOfScalarMappings);
	}

	public SmartEntityAssemblyChecker isDelegateQueryJoin_Left() {
		return isDelegateQueryJoin().whereProperty("isLeftJoin").isTrue_();
	}

	public SmartEntityAssemblyChecker isDelegateQueryJoin_Inner() {
		return isDelegateQueryJoin().whereProperty("isLeftJoin").isFalse_();
	}

	public SmartEntityAssemblyChecker isDelegateQueryJoin() {
		return hasType(DelegateQueryJoin.T);
	}

	public SmartEntityAssemblyChecker isDiscriminatorValueWithRules(int signaturePosition) {
		return hasType(DiscriminatorValue.T) //
				.whereProperty("signaturePosition").is_(signaturePosition) //
				.whereProperty("signatureToStaticValue");
	}
}
