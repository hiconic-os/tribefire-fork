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
package com.braintribe.model.processing.session.api.managed;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.pr.criteria.TraversingCriterion;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.query.EntityQueryResult;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(namespace=GmCoreApiInteropNamespaces.session)
@SuppressWarnings("unusable-by-js")
public interface EntityQueryResultConvenience extends QueryResultConvenience {
	
	/**
	 * This method overrides {@link QueryResultConvenience#result()} and returns an {@link EntityQueryResult}.
	 */
	@Override
	@JsMethod (name = "entityQueryResult")
	//This overridden method name needed to be changed. When we use the same name as the parent interface, then GWT generates a bug
	//where the method is calling itself over and over again.
	EntityQueryResult result() throws GmSessionException;
	
	/**
	 * This method overrides {@link QueryResultConvenience#setVariable(String, Object)} and returns and returns itself
	 */
	@Override
	EntityQueryResultConvenience setVariable(String name, Object value);
	
	/**
	 * This method overrides {@link QueryResultConvenience#setTraversingCriterion(TraversingCriterion)} and returns itself
	 */
	@Override
	EntityQueryResultConvenience setTraversingCriterion(TraversingCriterion traversingCriterion);
}
