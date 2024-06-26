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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.notification.Level;
import com.braintribe.model.notification.Notifications;
import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessor;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessors;

import tribefire.extension.demo.model.api.GetEmployeeByGenderResponse;
import tribefire.extension.demo.model.api.GetEmployeesByGender;
import tribefire.extension.demo.model.api.GetEmployeesByGenderAsNotifications;
import tribefire.extension.demo.model.api.GetEmployeesByGenderRequest;
import tribefire.extension.demo.model.data.Company;
import tribefire.extension.demo.model.data.Gender;
import tribefire.extension.demo.model.data.Person;
import tribefire.extension.demo.processing.tools.ServiceBase;

public class GetEmployeesByGenderProcessor implements AccessRequestProcessor<GetEmployeesByGenderRequest, Object>  {
	
	private AccessRequestProcessor<GetEmployeesByGenderRequest, Object> dispatcher = AccessRequestProcessors.dispatcher(config->{
		config.register(GetEmployeesByGender.T, this::getEmployeesByGender);
		config.register(GetEmployeesByGenderAsNotifications.T, this::getEmployeesByGenderAsNotifications);
	});
	
	@Override
	public Object process(AccessRequestContext<GetEmployeesByGenderRequest> context) {
		return dispatcher.process(context);
	}
	
	private List<Person> getEmployeesByGender(AccessRequestContext<GetEmployeesByGender> context) {
		GetEmployeesByGender request = context.getRequest();
		return new EmployeeSearcher(
				request.getCompany(), 
				request.getGender()).run();
	}
	
	private Notifications getEmployeesByGenderAsNotifications(AccessRequestContext<GetEmployeesByGenderAsNotifications> context) {
		GetEmployeesByGenderAsNotifications request = context.getRequest();
		return new EmployeeSearcher(
				request.getCompany(), 
				request.getGender()).runWithNotifications();
	}

	private class EmployeeSearcher extends ServiceBase {
		private Company company;
		private Gender gender;
		
		public EmployeeSearcher(Company company, Gender gender) {
			this.company = company;
			this.gender = gender;
		}
		
		private GetEmployeeByGenderResponse runWithNotifications() {
			if (company == null) {
				return createConfirmationResponse("Please provide a company.", Level.INFO, GetEmployeeByGenderResponse.T);
			}
			if (gender == null) {
				return createConfirmationResponse("Please provide a gender.", Level.INFO, GetEmployeeByGenderResponse.T);
			}
			
			List<Person> genderedPersons = run();

			addNotifications(
				com.braintribe.model.processing.notification.api.builder.Notifications.build()
					.add()
						.command()
						.gotoModelPath("List employees")
							.addElement(GMF.getTypeReflection().getType(genderedPersons).getTypeSignature(),genderedPersons)
						.close()
					.close()
					.list()
			);

			return createResponse("Found "+genderedPersons.size()+" "+gender+" employee(s) working at '"+company.getName()+"'.", GetEmployeeByGenderResponse.T);
			
		}
		
		private List<Person> run() {
			
			Objects.requireNonNull(company, "request must have a company parameter");
			Objects.requireNonNull(gender, "request must have a gender parameter");
			
			List<Person> genderedPersons = company.getEmployees().stream().filter(p -> p.getGender() == gender).collect(Collectors.toList());
			return genderedPersons;
		}
		
		
	}

}
