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
package tribefire.extension.elasticsearch.model.api.request.doc;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.elasticsearch.model.api.ElasticsearchResponse;

public interface SearchAsYouType extends SearchRequest {

	EntityType<SearchAsYouType> T = EntityTypes.T(SearchAsYouType.class);

	String term = "term";

	@Priority(0.99d)
	@Mandatory
	@Name("Search term")
	@Description("Search for documents starting with this term.")
	String getTerm();
	void setTerm(String term);

	@Override
	EvalContext<? extends ElasticsearchResponse> eval(Evaluator<ServiceRequest> evaluator);
}