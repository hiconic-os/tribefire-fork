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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessor;
import com.braintribe.model.processing.core.expert.api.GmExpertRegistry;
import com.braintribe.model.processing.core.expert.impl.ConfigurableGmExpertRegistry;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.EntityQuery;

import tribefire.extension.demo.model.api.FindByText;
import tribefire.extension.demo.model.data.Company;
import tribefire.extension.demo.model.data.Department;
import tribefire.extension.demo.model.data.Person;

public class FindByTextProcessor implements AccessRequestProcessor<FindByText,  List<GenericEntity>>  {

	private GmExpertRegistry registry = null;
	
	public void setRegistry(GmExpertRegistry registry) {
		this.registry = registry;
	}
	
	public FindByTextProcessor() {
		if (this.registry == null) {
			ConfigurableGmExpertRegistry registry = new ConfigurableGmExpertRegistry();
			registry.add(FindByTextExpert.class, Person.class, new PersonFinder());
			registry.add(FindByTextExpert.class, Company.class, new CompanyFinder());
			registry.add(FindByTextExpert.class, Department.class, new DepartmentFinder());
			this.registry = registry;
		}
	}
	
	
	@Override
	public List<GenericEntity> process(AccessRequestContext<FindByText> context) {
		
		FindByText request = context.getOriginalRequest();
		String type = request.getType();
		String text = request.getText();
		PersistenceGmSession session = context.getSession();
		
		
		FindByTextExpert finder = registry.getExpert(FindByTextExpert.class).forType(type);
		return finder.query(text, session);
		
	}

	public interface FindByTextExpert {
		List<GenericEntity> query (String text, PersistenceGmSession session);
	}
	
	public class PersonFinder implements FindByTextExpert {
		@Override
		public List<GenericEntity> query(String text, PersistenceGmSession session) {
			
			EntityQuery query = 
					EntityQueryBuilder
						.from(Person.T)
						.where()
							.disjunction()
								.property(Person.firstName).like(text+"*")
								.property(Person.lastName).like(text+"*")
							.close()
						.done();
			
			return session.query().entities(query).list();
		}
	}
	public class CompanyFinder implements FindByTextExpert {
		@Override
		public List<GenericEntity> query(String text, PersistenceGmSession session) {
			
			EntityQuery query = 
					EntityQueryBuilder
						.from(Company.T)
						.where()
							.property(Company.name).like(text+"*")
						.done();
			
			return session.query().entities(query).list();

			
		}
	}
	public class DepartmentFinder implements FindByTextExpert {
		@Override
		public List<GenericEntity> query(String text, PersistenceGmSession session) {
			EntityQuery query = 
					EntityQueryBuilder
						.from(Department.T)
						.where()
							.property(Department.name).like(text+"*")
						.done();
			
			return session.query().entities(query).list();

		}
	}
	 
	
}
