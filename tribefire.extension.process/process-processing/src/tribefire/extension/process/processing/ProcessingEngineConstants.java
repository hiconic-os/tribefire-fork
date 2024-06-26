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
package tribefire.extension.process.processing;

public interface ProcessingEngineConstants {
	static final String EVENT_EDGE_TRANSITION = "edge-transition";
	static final String EVENT_CORRUPTED_PROCESS_STATE = "corrupted-process-state";
	static final String EVENT_ERROR_TRANSITION = "error-transition";
	static final String EVENT_OVERDUE_TRANSITION = "overdue-transition";
	static final String EVENT_RESTART_TRANSITION = "restart-transition";
	
	static final String EVENT_PROCESS_SUSPENDED = "process-suspended";
	static final String EVENT_PROCESS_RESUMED = "process-resumed";
	static final String EVENT_PROCESS_ENDED = "process-ended";
	
	static final String EVENT_RESTART= "restart";
	static final String EVENT_ADMINISTRATIVE_RESTART = "administrative-restart";
	
	static final String EVENT_CONTINUATION_DEMAND = "continuation-demand";
	
	static final String EVENT_INVALID_TRANSITION = "invalid-transition";
	
	static final String EVENT_MISSING_ERROR_NODE = "missing-error-node";
	static final String EVENT_MISSING_OVERDUE_NODE = "missing-overdue-node";
	static final String EVENT_CYCLIC_OVERDUE_NODE = "cyclic-overdue-node";
	static final String EVENT_CYCLIC_ERROR_NODE = "cyclic-error-node";
	
	static final String EVENT_PRECALL_TRANSITION_PROCESSOR = "precall-transition-processor";
	static final String EVENT_PRECALL_CONDITION_PROCESSOR = "precall-condition-processor";
	static final String EVENT_DEFAULT_CONDITION_MATCHED = "default-condition-matched";
	static final String EVENT_CONDITION_MATCHED = "condition-matched";
	
	static final String EVENT_POSTCALL_TRANSITION_PROCESSOR = "postcall-transition-processor";
	static final String EVENT_POSTCALL_CONDITION_PROCESSOR = "postcall-condition-processor";
	
	static final String EVENT_ERROR_IN_TRANSITION_PROCESSOR = "error-in-transition-processor";
	static final String EVENT_ERROR_IN_CONDITION_PROCESSOR = "error-in-condition-processor";
	static final String EVENT_ERROR_AMBIGUOUS_CONTINUATION_DEMAND = "error-ambiguous-continuation-demand";
	
	static final String EVENT_INTERNAL_ERROR = "internal-error";
	static final String EVENT_OVERDUE_IN_STATE = "overdue-in-state";
	
	static final String EVENT_MAX_RESTART = "max-restart-reached";
}
