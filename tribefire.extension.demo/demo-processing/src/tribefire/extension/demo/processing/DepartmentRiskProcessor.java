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
package tribefire.extension.demo.processing;

import com.braintribe.cfg.Configurable;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.sp.api.AfterStateChangeContext;
import com.braintribe.model.processing.sp.api.StateChangeProcessor;
import com.braintribe.model.processing.sp.api.StateChangeProcessorException;
import com.braintribe.model.processing.sp.api.StateChangeProcessors;
import com.braintribe.model.stateprocessing.api.StateChangeProcessorCapabilities;
import com.braintribe.utils.lcd.StringTools;

import tribefire.extension.demo.model.data.Company;
import tribefire.extension.demo.model.data.Department;
import tribefire.extension.demo.model.data.Person;

/**
 * This {@link StateChangeProcessor} implementation is intended to be triggered
 * when a {@link Department}'s profitable attribute changes. We want to inform
 * the CEO of the according {@link Company} about the change by adding a comment
 * to his list.
 */
public class DepartmentRiskProcessor implements StateChangeProcessor<Department, GenericEntity> {

	// Messages can be configured via according setters below (usually via Spring)
	private String riskMessage = "Dear CEO! The status of department '$department' has changed to NOT profitable! Sincerely your Risk processor.";
	private String okMessage = "Dear CEO! The status of department '$department' has changed to profitable! Sincerely your Risk processor.";

	/**
	 * The message that is commented for the CEO if a Department changes to not
	 * profitable.
	 */
	@Configurable
	public void setRiskMessage(String riskMessage) {
		if (riskMessage != null)
			this.riskMessage = riskMessage;
	}

	/**
	 * The message that is commented for the CEO if a Department changes to
	 * profitable.
	 */
	@Configurable
	public void setOkMessage(String okMessage) {
		if (okMessage != null)
			this.okMessage = okMessage;
	}

	/**
	 * Default Constructor
	 */
	public DepartmentRiskProcessor() {}

	
	@Override
	public StateChangeProcessorCapabilities getCapabilities() {
		return StateChangeProcessors.afterOnlyCapabilities();
	}
	
	/**
	 * This method is either called synchronously after the
	 * change is applied depending on the configuration. Here we implement our
	 * logic.<br /> All the changes done here are applied automatically by the
	 * framework after executing all StateChangeProcessors.
	 * 
	 */
	@Override
	public void onAfterStateChange(AfterStateChangeContext<Department> context, GenericEntity customContext)
			throws StateChangeProcessorException {

		// First we get the changed department from the passed context.
		Department department = context.getProcessEntity();
		
		// For completeness: check if the department belongs to a company
		// If not - ignore
		if (department.getCompany() == null)
			return;

		// Next we identify the message based on the profitable state.
		String message = (department.getProfitable()) ? okMessage : riskMessage;
		message = StringTools.replaceAllOccurences(message, "$department", department.getName());
		
		// Now we get the CEO of the company.
		Person ceo = department.getCompany().getCeo();

		// Finally we add the message to his comments.
		ceo.getComments().add(message);
	}

}
