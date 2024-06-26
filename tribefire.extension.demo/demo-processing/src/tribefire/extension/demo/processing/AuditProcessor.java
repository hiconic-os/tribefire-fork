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

import java.util.Date;

import com.braintribe.model.generic.manipulation.DeleteManipulation;
import com.braintribe.model.generic.manipulation.PropertyManipulation;
import com.braintribe.model.processing.sp.api.AfterStateChangeContext;
import com.braintribe.model.processing.sp.api.BeforeStateChangeContext;
import com.braintribe.model.processing.sp.api.StateChangeProcessor;
import com.braintribe.model.processing.sp.api.StateChangeProcessorException;
import com.braintribe.model.processing.sp.api.StateChangeProcessors;
import com.braintribe.model.stateprocessing.api.StateChangeProcessorCapabilities;

import tribefire.extension.demo.model.data.AuditRecord;
import tribefire.extension.demo.model.data.Person;

public class AuditProcessor implements StateChangeProcessor<Person, AuditRecord> {

	@Override
	public AuditRecord onBeforeStateChange(BeforeStateChangeContext<Person> context) throws StateChangeProcessorException {
		if (context.getManipulation() instanceof DeleteManipulation) {
			AuditRecord auditRecord = AuditRecord.T.create();
			auditRecord.setInfo("deleted Person with id " + context.getProcessEntity().getId());
			return auditRecord;
		}
		return null;
	}

	@Override
	public void onAfterStateChange(AfterStateChangeContext<Person> context, AuditRecord customContext) throws StateChangeProcessorException {
		// record create and delete events
		switch (context.getManipulation().manipulationType()) {
			case ADD:
			case CHANGE_VALUE:
			case CLEAR_COLLECTION:
			case REMOVE:
				PropertyManipulation m = (PropertyManipulation) context.getManipulation();
				AuditRecord changeRecord = context.getSession().create(AuditRecord.T);
				changeRecord.setDate(new Date());
				changeRecord.setInfo("Value of property: "+m.getOwner().getPropertyName()+" of Person with id "+context.getProcessEntity().getId()+" changed.");
				break;
			case INSTANTIATION:
				AuditRecord createRecord = context.getSession().create(AuditRecord.T);
				createRecord.setDate(new Date());
				createRecord.setInfo("created Person with id " + context.getProcessEntity().getId());
				break;
			case DELETE:
				AuditRecord deleteRecord = context.getSession().create(AuditRecord.T);
				deleteRecord.setDate(new Date());
				deleteRecord.setInfo(customContext.getInfo());
				break;
			default:
				break;
		}
	}

	@Override
	public StateChangeProcessorCapabilities getCapabilities() {
		return StateChangeProcessors.capabilities(true, true, false);
	}
}
