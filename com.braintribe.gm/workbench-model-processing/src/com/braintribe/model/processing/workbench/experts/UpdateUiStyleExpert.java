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
package com.braintribe.model.processing.workbench.experts;

import java.util.Collections;

import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.generic.synchronize.BasicEntitySynchronization;
import com.braintribe.model.processing.generic.synchronize.experts.ResourceIdentityManager;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.workbench.WorkbenchInstructionContext;
import com.braintribe.model.processing.workbench.WorkbenchInstructionExpert;
import com.braintribe.model.processing.workbench.WorkbenchInstructionProcessorException;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.style.Color;
import com.braintribe.model.workbench.WorkbenchConfiguration;
import com.braintribe.model.workbench.instruction.UpdateUiStyle;


/**
 * Updates (or creates) the WorkbenchConfiguration with the given stylesheet. 
 */
public class UpdateUiStyleExpert implements WorkbenchInstructionExpert<UpdateUiStyle>{

	private static final Logger logger = Logger.getLogger(UpdateFolderExpert.class);
	
	@Override
	public void process(UpdateUiStyle instruction, WorkbenchInstructionContext context) throws WorkbenchInstructionProcessorException {
		try {
			PersistenceGmSession session = context.getSession();
			
			EntityQuery query = EntityQueryBuilder.from(WorkbenchConfiguration.class).done();
			// First we ensure the WorkbenchConfiguration.
			WorkbenchConfiguration configuration = acquireWorkbenchConfiguration(instruction, query, session);
			if (configuration == null) {
				logger.warn("No WorkbenchConfiguration found. Ignore.");
				return;
			}
			
			Resource stylesheet = configuration.getStylesheet();
			if (instruction.getOverrideExisting() || stylesheet == null) {
				// No StyleSheet set or we should override it anyway. 

				Resource newStylesheet = instruction.getStylesheet();
				Resource newSynchronizedStylesheet = null;
				if (newStylesheet != null) {
					// Synchronize the newStylesheet with the session.
					newSynchronizedStylesheet = (Resource)
							BasicEntitySynchronization
								.newInstance(false)
								.session(session)
								.addIdentityManager(new ResourceIdentityManager())
								.addIdentityManager()
									.generic()
									.responsibleFor(Color.class)
									.addIdentityProperties("red","green","blue")
								.close()
								.addDefaultIdentityManagers()
								.addEntity(newStylesheet)
								.synchronize()
								.unique();
				}
				// Set the synchronized stylesheet on the Configuraiton.
				configuration.setStylesheet(newSynchronizedStylesheet);
			}
			

			BasicEntitySynchronization
				.newInstance(false)
				.session(session)
				.addIdentityManager()
					.generic()
					.responsibleFor(Color.class)
					.addIdentityProperties("red","green","blue")
				.close()
				.addDefaultIdentityManagers()
				.addEntities(instruction.getColorsToEnsure())
				.addEntities(instruction.getUiThemesToEnsure() != null ? instruction.getUiThemesToEnsure() : Collections.<GenericEntity>emptyList())
				.synchronize();

			
			
		} catch (GmSessionException e) {
			throw new WorkbenchInstructionProcessorException("An error occurred while updating UiStyle.",e);
		}
		
		
	}

	private WorkbenchConfiguration acquireWorkbenchConfiguration(UpdateUiStyle instruction, EntityQuery query, PersistenceGmSession session) throws GmSessionException {
		WorkbenchConfiguration configuration = session.queryCache().entities(query).unique();
		if (configuration == null) {
			configuration = session.query().entities(query).unique();
		}
		
		if (configuration == null) {
			if (!instruction.getEnsureWorkbenchConfiguration()) {
				return null;
			}
			configuration = session.create(WorkbenchConfiguration.T);
		}
		return configuration;
	}

}
