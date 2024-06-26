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
package tribefire.extension.messaging.model.service.consume;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.AuthorizedRequest;
import com.braintribe.model.service.api.DispatchableRequest;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.messaging.model.Envelop;

/**
 * To be evaluated when consuming a message
 */
public interface ProcessConsumedMessage extends AuthorizedRequest, DispatchableRequest {

	EntityType<ProcessConsumedMessage> T = EntityTypes.T(ProcessConsumedMessage.class);

	@Override
	EvalContext<? extends ProcessConsumedMessageResult> eval(Evaluator<ServiceRequest> evaluator);

	String envelop = "envelop";

	@Mandatory
	@Name("Envelop")
	@Description("The envelop containing messages")
	Envelop getEnvelop();
	void setEnvelop(Envelop envelop);
}