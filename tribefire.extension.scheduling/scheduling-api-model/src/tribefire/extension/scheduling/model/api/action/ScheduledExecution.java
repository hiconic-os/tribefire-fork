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
package tribefire.extension.scheduling.model.api.action;

import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.DomainRequest;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.scheduling.model.context.Context;

public interface ScheduledExecution extends DomainRequest {

	EntityType<ScheduledExecution> T = EntityTypes.T(ScheduledExecution.class);

	String getScheduledId();
	void setScheduledId(String scheduledId);

	String getDescription();
	void setDescription(String description);

	Context getContext();
	void setContext(Context context);

	@Override
	EvalContext<? extends ScheduledExecutionResult> eval(Evaluator<ServiceRequest> evaluator);
}
