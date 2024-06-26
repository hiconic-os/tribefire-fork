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
package tribefire.extension.cache.model.service.demo;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.AuthorizedRequest;
import com.braintribe.model.service.api.ServiceRequest;

public interface CacheDemo extends AuthorizedRequest {

	EntityType<CacheDemo> T = EntityTypes.T(CacheDemo.class);

	@Override
	EvalContext<? extends CacheDemoResult> eval(Evaluator<ServiceRequest> evaluator);

	String sendNotifications = "sendNotifications";
	String durationInMs = "durationInMs";
	String throwException = "throwException";
	String resultValue = "resultValue";

	@Name("Send Notifications")
	@Description("If enabled the response returns notifications for the caller.")
	@Mandatory
	boolean getSendNotifications();
	void setSendNotifications(boolean sendNotifications);

	@Initializer("0l")
	@Mandatory
	long getDurationInMs();
	void setDurationInMs(long durationInMs);

	@Initializer("false")
	@Mandatory
	boolean getThrowException();
	void setThrowException(boolean throwException);

	String getResultValue();
	void setResultValue(String resultValue);

}
