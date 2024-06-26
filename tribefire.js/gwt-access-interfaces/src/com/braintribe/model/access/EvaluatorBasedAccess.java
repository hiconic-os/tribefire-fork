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
package com.braintribe.model.access;

import java.util.Set;

import com.braintribe.model.accessapi.GetModel;
import com.braintribe.model.accessapi.GetPartitions;
import com.braintribe.model.accessapi.ManipulationRequest;
import com.braintribe.model.accessapi.ManipulationResponse;
import com.braintribe.model.accessapi.PersistenceRequest;
import com.braintribe.model.accessapi.QueryAndSelect;
import com.braintribe.model.accessapi.QueryEntities;
import com.braintribe.model.accessapi.QueryProperty;
import com.braintribe.model.accessapi.ReferencesRequest;
import com.braintribe.model.accessapi.ReferencesResponse;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.EntityQueryResult;
import com.braintribe.model.query.PropertyQuery;
import com.braintribe.model.query.PropertyQueryResult;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.SelectQueryResult;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * @author peter.gazdik
 */
public class EvaluatorBasedAccess implements IncrementalAccess {

	private final String accessId;
	private final Evaluator<ServiceRequest> evaluator;

	public EvaluatorBasedAccess(String accessId, Evaluator<ServiceRequest> evaluator) {
		this.accessId = accessId;
		this.evaluator = evaluator;
	}

	@Override
	public String getAccessId() {
		return accessId;
	}

	@Override
	public GmMetaModel getMetaModel() {
		GetModel request = request(GetModel.T);
		return request.eval(evaluator).get();
	}

	@Override
	public SelectQueryResult query(SelectQuery query) {
		QueryAndSelect request = request(QueryAndSelect.T);
		request.setQuery(query);

		return request.eval(evaluator).get();
	}

	@Override
	public EntityQueryResult queryEntities(EntityQuery query) {
		QueryEntities request = request(QueryEntities.T);
		request.setQuery(query);

		return request.eval(evaluator).get();
	}

	@Override
	public PropertyQueryResult queryProperty(PropertyQuery query) {
		QueryProperty request = request(QueryProperty.T);
		request.setQuery(query);

		return request.eval(evaluator).get();
	}

	@Override
	public ManipulationResponse applyManipulation(ManipulationRequest request) {
		return request.eval(evaluator).get();
	}

	@Override
	public ReferencesResponse getReferences(ReferencesRequest referencesRequest) {
		ReferencesRequest request = request(ReferencesRequest.T);
		return request.eval(evaluator).get();
	}

	@Override
	public Set<String> getPartitions() {
		GetPartitions request = request(GetPartitions.T);
		return request.eval(evaluator).get();
	}

	private <R extends PersistenceRequest> R request(EntityType<R> et) {
		R result = et.create();
		result.setServiceId(accessId);

		return result;
	}

}
