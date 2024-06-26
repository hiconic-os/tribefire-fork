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
package tribefire.extension.process.api;

import java.util.Date;
import tribefire.extension.process.model.data.details.ProcessTraceDetails;

public interface TraceBuilder {
	TraceBuilder message(String message);
	TraceBuilder exception(Throwable throwable);
	TraceBuilder edge(Object leftState, Object enteredState);
	TraceBuilder loggerContext(Class<?> loggerContextClass);
	TraceBuilder date(Date date);
	TraceBuilder state(Object state);
	TraceBuilder details(ProcessTraceDetails details);
	
	void error(String event);
	void warn(String event);
	void info(String event);
	void trace(String event);
}
