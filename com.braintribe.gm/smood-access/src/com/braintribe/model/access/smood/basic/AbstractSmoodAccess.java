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
package com.braintribe.model.access.smood.basic;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Supplier;

import com.braintribe.cfg.Configurable;
import com.braintribe.exception.Exceptions;
import com.braintribe.model.access.AbstractAccess;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.criteria.matching.Matcher;
import com.braintribe.model.generic.reflection.StrategyOnCriterionMatch;
import com.braintribe.model.processing.smood.Smood;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.EntityQueryResult;
import com.braintribe.model.query.PropertyQuery;
import com.braintribe.model.query.PropertyQueryResult;
import com.braintribe.model.query.Query;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.SelectQueryResult;

public abstract class AbstractSmoodAccess extends AbstractAccess {

	protected Supplier<String> localeProvider;

	protected ReadWriteLock readWriteLock;
	protected Lock readLock;
	protected Lock writeLock;

	@Configurable
	public void setReadWriteLock(ReadWriteLock readWriteLock) {
		this.readWriteLock = readWriteLock;
		this.readLock = readWriteLock.readLock();
		this.writeLock = readWriteLock.writeLock();
	}

	@Configurable
	public void setLocaleProvider(Supplier<String> localeProvider) {
		this.localeProvider = localeProvider;
	}

	@Override
	public SelectQueryResult query(SelectQuery query) {
		SmoodAccessLogging.selectQuery(query);

		readLock.lock();
		try {
			return r_query(query);

		} finally {
			readLock.unlock();
		}
	}

	protected SelectQueryResult r_query(SelectQuery query) {
		SelectQueryResult result = getDatabase().query(query);
		List<Object> clonedResults = cloneSelectQueryResult(result.getResults(), query, createStandardCloningContext(), cloningStrategy(query));
		result.setResults(clonedResults);

		SmoodAccessLogging.selectQueryEvaluationFinished();

		return result;
	}

	@Override
	public EntityQueryResult queryEntities(EntityQuery query) {
		readLock.lock();
		try {
			return r_queryEntities(query);

		} finally {
			readLock.unlock();
		}
	}

	protected EntityQueryResult r_queryEntities(EntityQuery query) {
		EntityQueryResult result = getDatabase().queryEntities(query);

		Matcher matcher = getMatcher(query);

		List<GenericEntity> cloned = cloneEntityQueryResult(result.getEntities(), matcher, createStandardCloningContext(), cloningStrategy(query));

		result.setEntities(cloned);

		return result;
	}

	@Override
	public PropertyQueryResult queryProperty(PropertyQuery query) {
		readLock.lock();
		try {
			return r_queryProperty(query);

		} catch (RuntimeException e) {
			throw Exceptions.contextualize(e, "Error while querying property '" + query.getPropertyName() + "' of: " + query.getEntityReference().getTypeSignature());

		} finally {
			readLock.unlock();
		}
	}

	protected PropertyQueryResult r_queryProperty(PropertyQuery query) {
		PropertyQueryResult result = getDatabase().queryProperty(query);

		Object propertyValue = result.getPropertyValue();

		if (propertyValue != null) {
			Object clonedPropertyValue = clonePropertyQueryResult(null, propertyValue, query, createStandardCloningContext(), cloningStrategy(query));

			result.setPropertyValue(clonedPropertyValue);
		}

		return result;
	}

	private StrategyOnCriterionMatch cloningStrategy(Query request) {
		return (request.getNoAbsenceInformation()) ? StrategyOnCriterionMatch.skip : StrategyOnCriterionMatch.partialize;
	}

	protected abstract Smood getDatabase();

}
