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
package tribefire.extension.tracing.model.service.status.local;

import java.util.Date;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.tracing.model.deployment.connector.TracingConnector;
import tribefire.extension.tracing.model.service.TracingResult;

public interface TracingStatusLocalResult extends TracingResult {

	EntityType<TracingStatusLocalResult> T = EntityTypes.T(TracingStatusLocalResult.class);

	String tracingEnabled = "tracingEnabled";
	String name = "name";
	String disableTracingAt = "disableTracingAt";
	String connectorConfiguration = "connectorConfiguration";

	boolean getTracingEnabled();
	void setTracingEnabled(boolean tracingEnabled);

	@Mandatory
	String getName();
	void setName(String name);

	Date getDisableTracingAt();
	void setDisableTracingAt(Date disableTracingAt);

	@Mandatory
	@Name("Connector Configuration")
	@Description("Tracing Connector configuration")
	TracingConnector getConnectorConfiguration();
	void setConnectorConfiguration(TracingConnector connectorConfiguration);
}
