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
package com.braintribe.gwt.gme.constellation.client.expert;

import java.util.List;

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.model.accessapi.ModelEnvironment;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;

/**
 * This expert is responsible for updating its list of sessions once the modelEnvironment is updated.
 * @author michel.docouto
 *
 */
public class ModelEnvironmentDrivenSessionUpdater {
	
	private List<? extends ModelEnvironmentDrivenGmSession> dataSessions;
	private List<? extends ModelEnvironmentDrivenGmSession> workbenchSessions;
	
	/**
	 * Configures the list of data sessions to be updated.
	 */
	@Configurable
	public void setDataSessions(List<? extends ModelEnvironmentDrivenGmSession> dataSessions) {
		this.dataSessions = dataSessions;
	}
	
	/**
	 * Configures the list of workbench sessions to be updated.
	 */
	@Configurable
	public void setWorkbenchSessions(List<? extends ModelEnvironmentDrivenGmSession> workbenchSessions) {
		this.workbenchSessions = workbenchSessions;
	}
	
	public void updateModelEnvironment(ModelEnvironment modelEnvironment) {
		if (dataSessions != null) {
			for (ModelEnvironmentDrivenGmSession session : dataSessions)
				session.configureModelEnvironment(modelEnvironment);
		}
		
		if (workbenchSessions != null) {
			ModelEnvironment workbenchModelEnvironment = ModelEnvironment.T.create();
			workbenchModelEnvironment.setDataModel(modelEnvironment.getWorkbenchModel());
			workbenchModelEnvironment.setDataAccessId(modelEnvironment.getWorkbenchModelAccessId());
			workbenchModelEnvironment.setMetaModelAccessId(modelEnvironment.getMetaModelAccessId());
			for (ModelEnvironmentDrivenGmSession session : workbenchSessions)
				session.configureModelEnvironment(workbenchModelEnvironment);
		}
	}

}
