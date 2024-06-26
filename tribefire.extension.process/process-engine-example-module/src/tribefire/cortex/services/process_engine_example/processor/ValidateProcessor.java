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

import java.util.Calendar;
import java.util.Date;

import com.braintribe.model.goofy.GoofyProcess;
import tribefire.extension.process.api.TransitionProcessor;
import tribefire.extension.process.api.TransitionProcessorContext;

import tribefire.cortex.services.process_engine_example.Tokens;

public class ValidateProcessor implements TransitionProcessor<GoofyProcess> {

	@Override
	public void process(TransitionProcessorContext<GoofyProcess> context) {
		GoofyProcess process = context.getProcess();

		// date..
		Date date = process.getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.YEAR, 2000);
		Date after = calendar.getTime();
		if (date == null || date.before(after)) {
			context.continueWithState(Tokens.validateError);
			return;
		}

		// number
		Double number = process.getNumber();
		if (number == null || number < 0 || number > 100000) {
			context.continueWithState(Tokens.validateError);
			return;
		}

		// name
		String name = process.getName();
		if (name == null || name.length() == 0 || name.equalsIgnoreCase("scrooge")) {
			context.continueWithState(Tokens.validateError);
			return;
		}
	}

}
