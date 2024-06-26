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
package com.braintribe.model.processing.vde.test;

import com.braintribe.gwt.async.client.DeferredExecutor;
import com.braintribe.gwt.async.testing.Futures;
import com.braintribe.model.processing.vde.clone.async.DeferredExecutorAspect;
import com.braintribe.model.processing.vde.evaluator.VDE;
import com.braintribe.model.processing.vde.evaluator.api.VdeContextAspect;
import com.braintribe.model.processing.vde.evaluator.api.VdeEvaluationMode;
import com.braintribe.model.processing.vde.evaluator.api.VdeRuntimeException;
import com.braintribe.model.processing.vde.evaluator.api.builder.VdeContextBuilder;
import com.braintribe.model.processing.vde.impl.VDGenerator;
import com.braintribe.processing.async.impl.HubPromise;

/**
 * @author peter.gazdik
 */
public abstract class VdeTest {

	public static VDGenerator $ = new VDGenerator();

	protected DeferredExecutor deferredExecutor = Futures.singleThreadedDeferredExecutor;

	public VdeTest() {

	}

	protected Object evaluate(Object vdeOrValue) {
		HubPromise<Object> promise = new HubPromise<>();
		evaluate().forValue(vdeOrValue, promise);
		return promise.get();
	}

	protected <T, A extends VdeContextAspect<? super T>> Object evaluateWith(Class<A> aspect, T aspectValue, Object value)
			throws VdeRuntimeException {
		HubPromise<Object> promise = new HubPromise<>();
		evaluate().with(aspect, aspectValue).forValue(value, promise);
		return promise.get();
	}

	protected Object evaluateWithEvaluationMode(Object value, VdeEvaluationMode evalMode) {
		HubPromise<Object> promise = new HubPromise<>();
		evaluate().withEvaluationMode(evalMode).forValue(value, promise);
		return promise.get();
	}

	protected VdeContextBuilder evaluate() {
		return VDE.evaluate().with(DeferredExecutorAspect.class, deferredExecutor);
	}

}
