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
package com.braintribe.model.processing.query.smart.eval.set.base;

import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdA;
import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdB;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Before;

import com.braintribe.common.lcd.EmptyReadWriteLock;
import com.braintribe.model.access.smood.basic.SmoodAccess;
import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.processing.query.eval.api.EvalTupleSet;
import com.braintribe.model.processing.query.eval.api.QueryEvaluationContext;
import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.processing.query.eval.set.base.AbstractEvalTupleSetTests;
import com.braintribe.model.processing.query.eval.tools.QueryEvaluationTools;
import com.braintribe.model.processing.query.eval.tuple.ArrayBasedTuple;
import com.braintribe.model.processing.query.fluent.SelectQueryBuilder;
import com.braintribe.model.processing.query.smart.test.base.DelegateAccessSetup;
import com.braintribe.model.processing.query.smart.test.builder.SmartDataBuilder;
import com.braintribe.model.processing.query.smart.test.setup.BasicSmartSetupProvider;
import com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup;
import com.braintribe.model.processing.query.smart.test.setup.base.SmartSetupProvider;
import com.braintribe.model.processing.query.support.QueryFunctionTools;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.impl.persistence.BasicPersistenceGmSession;
import com.braintribe.model.processing.smood.Smood;
import com.braintribe.model.query.smart.processing.eval.context.BasicSmartQueryEvaluationContext;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.smartqueryplan.SmartQueryPlan;
import com.braintribe.utils.junit.assertions.BtAssertions;
import com.braintribe.utils.lcd.MapTools;

/**
 * TODO extract common super-type of this and {@link AbstractEvalTupleSetTests}
 */
public abstract class AbstractSmartEvalTupleSetTests {

	protected SmartMappingSetup setup;
	
	protected SmoodAccess accessA;
	protected SmoodAccess accessB;

	private Smood smoodA;
	private Smood smoodB;

	private Map<IncrementalAccess, com.braintribe.model.access.IncrementalAccess> accessMapping;

	private QueryEvaluationContext context;
	private Set<Tuple> tuples;
	private Iterator<Tuple> tuplesIterator;
	protected SmartDataBuilder bA;
	protected SmartDataBuilder bB;
	protected SmartTupleSetBuilder builder;
	protected PersistenceGmSession session;

	@Before
	public void setup() throws Exception {
		setup = getSmartSetupProvider().setup();

		DelegateAccessSetup accessSetup = new DelegateAccessSetup(setup);

		accessA = accessSetup.getAccessA();
		accessB = accessSetup.getAccessB();

		builder = new SmartTupleSetBuilder();

		bA = new SmartDataBuilder(accessA.getDatabase(), accessIdA);
		bB = new SmartDataBuilder(accessB.getDatabase(), accessIdB);

		session = new BasicPersistenceGmSession();

		accessMapping = newMap();
		accessMapping.put(setup.accessA, accessA);
		accessMapping.put(setup.accessB, accessB);
	}

	protected SmartSetupProvider getSmartSetupProvider() {
		return BasicSmartSetupProvider.INSTANCE;
	}

	protected SelectQueryBuilder query() {
		return new SelectQueryBuilder();
	}

	protected Smood newSmood() {
		return new Smood(EmptyReadWriteLock.INSTANCE);
	}

	protected void registerAtSmoodA(GenericEntity entity) {
		registerAtSmood(smoodA, entity);
	}

	protected void registerAtSmoodB(GenericEntity entity) {
		registerAtSmood(smoodB, entity);
	}

	private void registerAtSmood(Smood smood, GenericEntity entity) {
		smood.registerEntity(entity, true);
	}

	protected void evaluate(TupleSet tupleSet) {
		context = new BasicSmartQueryEvaluationContext(toQueryPlan(tupleSet), session, accessMapping,
				QueryFunctionTools.functionExperts(null), null, null);
		tuplesIterator = resolveTupleset(tupleSet).iterator();
	}

	protected EvalTupleSet resolveTupleset(TupleSet tupleSet) {
		return context.resolveTupleSet(tupleSet);
	}

	private SmartQueryPlan toQueryPlan(TupleSet tupleSet) {
		SmartQueryPlan result = SmartQueryPlan.T.create();

		result.setTupleSet(tupleSet);
		result.setTotalComponentCount(builder.getIndex());

		return result;
	}

	protected void assertNoMoreTuples() {
		if (tuples == null) {
			BtAssertions.assertThat(tuplesIterator.hasNext()).as("No more tuples in the result set expected!").isFalse();

		} else {
			BtAssertions.assertThat(tuples).as("No more tuples in the result set expected!").isEmpty();
		}
	}

	protected void assertContainsTuple(Object... values) {
		BtAssertions.assertThat(values).as("Wrong number of values provided. Resulting tuples have different dimension!")
				.hasSize(context.resultComponentsCount());

		collectTuples();

		Tuple tuple = asTuple(values);

		if (!tuples.contains(tuple)) {
			throw new RuntimeException("Tuple not found in the result: " + Arrays.toString(values));
		}

		tuples.remove(tuple);
	}

	private void collectTuples() {
		if (tuples == null) {
			tuples = QueryEvaluationTools.tupleHashSet(context.resultComponentsCount());

			while (tuplesIterator.hasNext()) {
				Tuple tuple = tuplesIterator.next();
				tuples.add(tuple.detachedCopy());
			}
		}
	}

	protected void assertNextTuple(Object... values) {
		checkContainsWasNotCalledBefore();
		assertHasNextTuple();

		Tuple tuple = tuplesIterator.next();

		for (int i = 0; i < values.length; i++) {
			Object expected = values[i];
			Object evaluated = tuple.getValue(i);

			if (expected == null) {
				BtAssertions.assertThat(evaluated).isNull();

			} else if (expected instanceof GenericEntity) {
				BtAssertions.assertThat(evaluated).as("Evaluated is not same as expected!").isSameAs(expected);

			} else {
				BtAssertions.assertThat(evaluated).isEqualTo(expected);
			}
		}
	}

	private void checkContainsWasNotCalledBefore() {
		if (tuples != null)
			throw new RuntimeException("Illegal method called. Seems like 'assertContainsTuple' was already called!" +
					" There should never be a test that uses both that and 'assertNextTuple'");
	}

	private void assertHasNextTuple() {
		BtAssertions.assertThat(tuplesIterator.hasNext()).as("No more tuples in the result set!").isTrue();
	}

	protected Tuple asTuple(Object... values) {
		return new ArrayBasedTuple(values);
	}

	@SuppressWarnings("unchecked")
	protected <K, V> Map<K, V> asMap(Object... objs) {
		return (Map<K, V>) MapTools.getMap(objs);
	}

	protected EntityReference reference(GenericEntity ge) {
		return ge.reference();
	}

}
