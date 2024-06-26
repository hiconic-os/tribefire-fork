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
package tribefire.extension.appconfiguration.model.api;

import com.braintribe.model.accessapi.AccessRequest;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

public interface GetAppConfiguration extends AccessRequest {

	EntityType<GetAppConfiguration> T = EntityTypes.T(GetAppConfiguration.class);

	String getName();
	void setName(String name);

	@Initializer("true")
	Boolean getIncludeDescriptors();
	void setIncludeDescriptors(Boolean includeDescriptors);

	@Override
	EvalContext<? extends GetAppConfigurationResponse> eval(Evaluator<ServiceRequest> evaluator);

}