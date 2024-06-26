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
package tribefire.extension.tracing.model.deployment.connector;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.logging.LogLevel;

/**
 *
 */
public interface LoggingTracingConnector extends TracingConnector {

	final EntityType<LoggingTracingConnector> T = EntityTypes.T(LoggingTracingConnector.class);

	String logLevel = "logLevel";
	String logAttributes = "logAttributes";

	@Mandatory
	@Name("Log Level")
	@Description("Log Level for logging spans")
	@Initializer("enum(com.braintribe.model.logging.LogLevel,INFO)")
	LogLevel getLogLevel();
	void setLogLevel(LogLevel logLevel);
	
	@Name("Log Attributes")
	@Description("Indicates if attributes should be logged")
	@Initializer("true")
	boolean getLogAttributes();
	void setLogAttributes(boolean logAttributes);
}
