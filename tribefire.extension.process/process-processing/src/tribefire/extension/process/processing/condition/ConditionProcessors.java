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
package tribefire.extension.process.processing.condition;

import tribefire.extension.process.api.ConditionProcessor;
import tribefire.extension.process.model.data.Process;

public abstract class ConditionProcessors {
	
	private static StaticConditionProcessor<Process> alwaysMatching = new StaticConditionProcessor<Process>(true);
	private static StaticConditionProcessor<Process> neverMatching = new StaticConditionProcessor<Process>(false);
	
	private ConditionProcessors() {
	}
	
	@SuppressWarnings("unchecked")
	public static <S extends Process> ConditionProcessor<S> alwaysMatching() {
		return (ConditionProcessor<S>)alwaysMatching;
	}
	@SuppressWarnings("unchecked")
	public static <S extends Process> ConditionProcessor<S> neverMatching() {
		return (ConditionProcessor<S>)neverMatching;
	}

}
