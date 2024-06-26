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
package com.braintribe.model.processing.vde.evaluator.api;

import com.braintribe.processing.async.api.AsyncCallback;

/**
 * This is the context that handles all the VDE related actions.
 * 
 * It provides evaluation methods and aspect adjustment methods.
 * 
 */
public interface VdeContext {

	/**
	 * Evaluation method for value descriptor, invokes @
	 * {@link #evaluate(Object, boolean) } with a false
	 * 
	 * @param object
	 *            The value descriptor that will be evaluated.
	 * @return The evaluated object
	 * 
	 */
	<T> T evaluate(Object object) throws VdeRuntimeException;
	
	/**
	 * Evaluation method for value descriptor, invokes @
	 * {@link #evaluate(Object, boolean) } with a false
	 * 
	 * @param object
	 *            The value descriptor that will be evaluated.
	 * @param callback the callback to which either the result or an exception is being asynchronously notified
	 */
	<T> void evaluate(Object object, AsyncCallback<T> callback);

	/**
	 * The evaluation method which evaluates a value descriptor and stores the
	 * result in a cache when non-volatile. If a non value descriptor is
	 * provided it will be returned as is.
	 * 
	 * @param object
	 *            The value descriptor that will be evaluated.
	 * @param volatileEvaluation
	 *            boolean that indicates if this evaluation is volatile
	 * @param callback the callback to which either the result or an exception is being asynchronously notified
	 */
	<T> void evaluate(Object object, boolean volatileEvaluation, AsyncCallback<T> callback);
	/**
	 * The evaluation method which evaluates a value descriptor and stores the
	 * result in a cache when non-volatile. If a non value descriptor is
	 * provided it will be returned as is.
	 * 
	 * @param object
	 *            The value descriptor that will be evaluated.
	 * @param volatileEvaluation
	 *            boolean that indicates if this evaluation is volatile
	 * @return The evaluated object
	 */
	<T> T evaluate(Object object, boolean volatileEvaluation) throws VdeRuntimeException;

	/**
	 * Retrieves a value for given aspect. The value may be <tt>null</tt>.
	 * 
	 * @param aspect
	 *            The aspect itself.
	 * @return The value associated with the aspect
	 */
	<T, A extends VdeContextAspect<T>> T get(Class<A> aspect);

	/**
	 * Adds a value for an {@link VdeContextAspect}
	 */
	<T, A extends VdeContextAspect<? super T>> void put(Class<A> aspect, T value);

	void setVdeRegistry(VdeRegistry registry);

	VdeRegistry getVdeRegistry();

	void setEvaluationMode(VdeEvaluationMode mode);

	VdeEvaluationMode getEvaluationMode();
}
