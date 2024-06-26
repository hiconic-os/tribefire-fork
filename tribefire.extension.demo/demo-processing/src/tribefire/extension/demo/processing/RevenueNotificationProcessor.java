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

import java.math.BigDecimal;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.sp.api.AfterStateChangeContext;
import com.braintribe.model.processing.sp.api.StateChangeProcessor;
import com.braintribe.model.processing.sp.api.StateChangeProcessorException;
import com.braintribe.model.processing.sp.api.StateChangeProcessors;
import com.braintribe.model.stateprocessing.api.StateChangeProcessorCapabilities;

import tribefire.extension.demo.model.data.Company;
import tribefire.extension.demo.model.data.Person;

public class RevenueNotificationProcessor implements StateChangeProcessor<Company, GenericEntity> {
	
	private BigDecimal minRevenue = new BigDecimal(1000000);
	private BigDecimal maxRevenue = new BigDecimal(10000000);
	
	public void setMinRevenue(BigDecimal minRevenue) {
		this.minRevenue = minRevenue;
	}
	
	public void setMaxRevenue(BigDecimal maxRevenue) {
		this.maxRevenue = maxRevenue;
	}
	
	@Override
	public StateChangeProcessorCapabilities getCapabilities() {
		return StateChangeProcessors.afterOnlyCapabilities();
	}
	
	@Override
	public void onAfterStateChange(AfterStateChangeContext<Company> context, GenericEntity arg1)
			throws StateChangeProcessorException {
		Company company = context.getProcessEntity();
		BigDecimal currentRevenue = company.getAverageRevenue();
		Person ceo = company.getCeo();
		
		if (currentRevenue == null)
			// ignore the state change if the averageRevenue property of the company is set to null
			return;	
		
		if (currentRevenue.compareTo(minRevenue) < 0) {
			// We fall below the limit
			ceo.getComments().add("Dear CEO! Your Company's revenue fall to: "+currentRevenue);
		} else if (currentRevenue.compareTo(maxRevenue) > 0) {
			ceo.getComments().add("Dear CEO! Your Company's revenue increased to: "+currentRevenue);
		} 
	}

}
