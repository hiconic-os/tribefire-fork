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
package tribefire.extension.modelling.processing.management;

import java.util.List;

import com.braintribe.model.accessdeployment.smood.CollaborativeSmoodAccess;
import com.braintribe.model.cortexapi.access.collaboration.ResetCollaborativePersistence;
import com.braintribe.model.deploymentapi.request.Undeploy;
import com.braintribe.model.notification.Notification;
import com.braintribe.model.processing.accessrequest.api.AbstractStatefulAccessRequestProcessor;
import com.braintribe.model.processing.notification.api.builder.Notifications;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.utils.lcd.CollectionTools2;

import tribefire.extension.modelling.commons.ModellingConstants;
import tribefire.extension.modelling.management.ModellingProject;
import tribefire.extension.modelling.management.api.DeleteProject;
import tribefire.extension.modelling.management.api.ModellingManagementResponse;

/**
 * <p>
 * Deletes the given {@link ModellingProject} from the management access.
 * Furthermore, the respective CSA instance is deleted from 'cortex'.
 * Before deletion, the access is reset via {@link ResetCollaborativePersistence}. This means, the physical access
 * folder will not be deleted.
 * 
 */
public class DeleteProjectProcessor
		extends AbstractStatefulAccessRequestProcessor<DeleteProject, ModellingManagementResponse>
		implements ModellingConstants {

	private ModellingManagementProcessorConfig config;

	public DeleteProjectProcessor(ModellingManagementProcessorConfig config) {
		this.config = config;
	}
	
	@Override
	public ModellingManagementResponse process() {

		// Delete modelling project in management access
		ModellingProject project = request().getProject();
		String pAccessId = project.getAccessId();

		session().deleteEntity(project);
		
		
		// Reset the project CSA to clear custom data
		ResetCollaborativePersistence reset = ResetCollaborativePersistence.T.create();
		reset.setServiceId(pAccessId);
		reset.eval(session()).get();
		
		
		// Undeploy the access
		Undeploy undeploy = Undeploy.T.create();
		undeploy.setExternalIds(CollectionTools2.asSet(pAccessId));
		undeploy.eval(session()).get();
		
		
		// Now we are safe to delete the CSA instance in cortex
		PersistenceGmSession cortexSession = config.sessionFactory().newSession(EXT_ID_ACCESS_CORTEX);
		
		EntityQuery query = EntityQueryBuilder.from(CollaborativeSmoodAccess.T) //
				.where() //
					.property(CollaborativeSmoodAccess.externalId).eq(pAccessId)
				.done();
		
		CollaborativeSmoodAccess projectAccess = cortexSession.query().entities(query).first();
		cortexSession.deleteEntity(projectAccess);
		
		cortexSession.commit();
		
		// return response
		ModellingManagementResponse response = ModellingManagementResponse.T.create();
		
		List<Notification> notifications =
				Notifications.build()
					.add()
						.message().info("Deleted project '" + project.getName() + "'") 
					.close()
					
					.add()
						.command()
							.reloadView("Reload View")
					.close()
				.list();
				
		response.setNotifications(notifications);
		return response;
	}
	
}
