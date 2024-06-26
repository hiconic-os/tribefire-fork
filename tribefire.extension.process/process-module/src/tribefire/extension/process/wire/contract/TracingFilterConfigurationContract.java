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
package tribefire.extension.process.wire.contract;

import java.util.Set;

import com.braintribe.wire.api.annotation.Name;

import tribefire.extension.process.processing.TracingFilterConfiguration;
import tribefire.module.wire.contract.PropertyLookupContract;

public interface TracingFilterConfigurationContract extends PropertyLookupContract, TracingFilterConfiguration {

	@Override
	@Name("TRIBEFIRE_TRACE_EVENT_EXCLUDES")
	Set<String> traceEventExcludes();
	
	@Override
	@Name("TRIBEFIRE_TRACE_EVENT_INCLUDES")
	Set<String> traceEventIncludes();
	
}
