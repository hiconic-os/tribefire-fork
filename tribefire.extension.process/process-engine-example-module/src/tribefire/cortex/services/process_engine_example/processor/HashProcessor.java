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
package tribefire.cortex.services.process_engine_example.processor;

import java.util.Date;

import com.braintribe.model.goofy.GoofyProcess;
import tribefire.extension.process.api.TransitionProcessor;
import tribefire.extension.process.api.TransitionProcessorContext;

import tribefire.cortex.services.process_engine_example.Tokens;

public class HashProcessor implements TransitionProcessor<GoofyProcess> {

	@Override
	public void process(TransitionProcessorContext<GoofyProcess> context) {
		GoofyProcess process = context.getProcess();

		Date date = process.getDate();
		Double number = process.getNumber();
		String name = process.getName();

		String hash = number + "-" + name + "-" + date.getTime();

		process.setHash(hash);

		context.continueWithState(Tokens.output);
	}

}
