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
package tribefire.extension.tracing.model.service.demo;

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.logging.LogLevel;
import com.braintribe.model.service.api.AuthorizedRequest;
import com.braintribe.model.service.api.ServiceRequest;

@Abstract
public interface DemoTracingRequest extends AuthorizedRequest {

	EntityType<DemoTracingRequest> T = EntityTypes.T(DemoTracingRequest.class);

	@Override
	EvalContext<? extends DemoTracingResponse> eval(Evaluator<ServiceRequest> evaluator);

	String sendNotifications = "sendNotifications";
	String message = "message";
	String logLevel = "logLevel";
	String throwException = "throwException";

	@Name("Send Notifications")
	@Description("If enabled the response returns notifications for the caller.")
	boolean getSendNotifications();
	void setSendNotifications(boolean sendNotifications);

	@Initializer("'Demo Message'")
	String getMessage();
	void setMessage(String message);

	@Mandatory
	@Initializer("enum(com.braintribe.model.logging.LogLevel,INFO)")
	LogLevel getLogLevel();
	void setLogLevel(LogLevel logLevel);

	@Mandatory
	@Initializer("false")
	boolean getThrowException();
	void setThrowException(boolean throwException);

}
