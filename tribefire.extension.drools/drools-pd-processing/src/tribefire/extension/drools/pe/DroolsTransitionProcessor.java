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
package tribefire.extension.drools.pe;

import org.kie.api.runtime.StatelessKieSession;

import com.braintribe.model.process.Process;
import com.braintribe.model.processing.pmp.api.TransitionProcessor;
import com.braintribe.model.processing.pmp.api.TransitionProcessorContext;
import com.braintribe.model.processing.pmp.api.TransitionProcessorException;

public class DroolsTransitionProcessor extends AbstractDroolsProcessor implements TransitionProcessor<Process> {
	@Override
	public void process(TransitionProcessorContext<Process> context) throws TransitionProcessorException {
		StatelessKieSession session = droolsContainer.openSession();
		
		session.setGlobal("context", context);
		session.execute(context.getProcess());
	}
}
