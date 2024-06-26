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
package com.braintribe.model.processing.query.fluent;

import java.util.function.Consumer;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.query.Join;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.functions.EntitySignature;
import com.braintribe.model.query.functions.ListIndex;
import com.braintribe.model.query.functions.Localize;
import com.braintribe.model.query.functions.MapKey;
import com.braintribe.model.query.functions.aggregate.AggregateFunction;
import com.braintribe.model.query.functions.aggregate.Average;
import com.braintribe.model.query.functions.aggregate.Count;
import com.braintribe.model.query.functions.aggregate.Max;
import com.braintribe.model.query.functions.aggregate.Min;
import com.braintribe.model.query.functions.aggregate.Sum;

/**
 * @author peter.gazdik
 */
public abstract class AbstractOperandBuilder<B, T, R> implements IOperandBuilder<T> {

	protected final SourceRegistry sourceRegistry;
	protected B backLink;
	protected Consumer<R> receiver;

	protected AbstractOperandBuilder(SourceRegistry sourceRegistry, B backLink, Consumer<R> receiver) {
		this.sourceRegistry = sourceRegistry;
		this.backLink = backLink;
		this.receiver = receiver;
	}

	protected AbstractOperandBuilder(SourceRegistry sourceRegistry) {
		this.sourceRegistry = sourceRegistry;
	}

	protected void setBackLink(B backLink) {
		this.backLink = backLink;
	}

	protected void setReceiver(Consumer<R> receiver) {
		this.receiver = receiver;
	}

	@Override
	public T entity(String alias) {
		return property(alias, null);
	}

	@Override
	public T entity(GenericEntity entity) {
		return operand(entity.reference());
	}

	@Override
	public T entityReference(PersistentEntityReference reference) {
		return operand(reference);
	}
	@Override
	public T property(String name) {
		PropertyOperand propertyOperand = PropertyOperand.T.create();
		propertyOperand.setPropertyName(name);
		propertyOperand.setSource(sourceRegistry.getFirstSource());
		return operand(propertyOperand);
	}

	@Override
	public T property(String alias, String name) {
		PropertyOperand propertyOperand = PropertyOperand.T.create();
		propertyOperand.setPropertyName(name);
		propertyOperand.setSource(sourceRegistry.acquireSource(alias));
		return operand(propertyOperand);
	}

	@Override
	public T listIndex(String joinAlias) {
		Join join = joinAlias != null ? sourceRegistry.acquireJoin(joinAlias) : (Join) sourceRegistry.getFirstSource();
		ListIndex listIndex = ListIndex.T.create();
		listIndex.setJoin(join);
		return operand(listIndex);
	}

	@Override
	public T mapKey(String joinAlias) {
		Join join = joinAlias != null ? sourceRegistry.acquireJoin(joinAlias) : (Join) sourceRegistry.getFirstSource();
		MapKey mapKey = MapKey.T.create();
		mapKey.setJoin(join);
		return operand(mapKey);
	}

	@Override
	public T localize(Object operand, String locale) {
		Localize localize = Localize.T.create();
		localize.setLocalizedStringOperand(operand);
		localize.setLocale(locale);
		return value(localize);
	}
	@Override
	public T value(Object object) {
		Object adaptedValue = AbstractQueryBuilder.adaptValue(object);
		return operand(adaptedValue);
	}


	@Override
	public OperandBuilder<T> localize(String locale) {
		Localize localize = Localize.T.create();
		localize.setLocale(locale);

		return new OperandBuilder<>(sourceRegistry, operand(localize), localize::setLocalizedStringOperand);
	}

	@Override
	public OperandBuilder<T> entitySignature() {
		EntitySignature entitySignature = EntitySignature.T.create();

		return new OperandBuilder<>(sourceRegistry, operand(entitySignature), entitySignature::setOperand);
	}

	@Override
	public abstract T operand(Object object);

	// ###############################################
	// ## . . . . . . . Aggregations . . . . . . . .##
	// ###############################################

	@Override
	public T count() {
		Count count = Count.T.create();
		count.setOperand(1);
		count.setDistinct(false);

		return operand(count);
	}

	@Override
	public T count(String alias) {
		return count(alias, null);
	}

	@Override
	public T count(String alias, String propertyName) {
		return count(alias, propertyName, false);
	}

	@Override
	public T count(String alias, String propertyName, boolean distinct) {
		PropertyOperand propertyOperand = PropertyOperand.T.create();
		propertyOperand.setPropertyName(propertyName);
		propertyOperand.setSource(alias == null ? sourceRegistry.getFirstSource() : sourceRegistry.acquireSource(alias));

		Count count = Count.T.create();
		count.setOperand(propertyOperand);
		count.setDistinct(distinct);

		return operand(count);
	}

	@Override
	public T max(String alias, String propertyName) {
		return aggregate(Max.T.create(), alias, propertyName);
	}

	@Override
	public T min(String alias, String propertyName) {
		return aggregate(Min.T.create(), alias, propertyName);
	}

	@Override
	public T sum(String alias, String propertyName) {
		return aggregate(Sum.T.create(), alias, propertyName);
	}

	@Override
	public T avg(String alias, String propertyName) {
		return aggregate(Average.T.create(), alias, propertyName);
	}

	private T aggregate(AggregateFunction aggregateFunction, String alias, String propertyName) {
		PropertyOperand propertyOperand = PropertyOperand.T.create();
		propertyOperand.setPropertyName(propertyName);
		propertyOperand.setSource(alias == null ? sourceRegistry.getFirstSource() : sourceRegistry.acquireSource(alias));

		aggregateFunction.setOperand(propertyOperand);

		return operand(aggregateFunction);
	}

}
