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
package tribefire.extension.modelling.processing.modelling;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.braintribe.model.cortexapi.access.collaboration.GetModifiedModelsForStage;
import com.braintribe.model.generic.reflection.BaseType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.notification.Notification;
import com.braintribe.model.path.GmRootPathElement;
import com.braintribe.model.processing.accessrequest.api.AbstractStatefulAccessRequestProcessor;
import com.braintribe.model.processing.notification.api.builder.Notifications;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.query.EntityQuery;

import tribefire.extension.modelling.commons.ModellingConstants;
import tribefire.extension.modelling.model.api.data.ModelSelection;
import tribefire.extension.modelling.model.api.request.GetModifiedModels;
import tribefire.extension.modelling.processing.tools.ModellingTools;

public class GetModifiedModelsProcessor
		extends AbstractStatefulAccessRequestProcessor<GetModifiedModels, ModelSelection>
		implements ModellingConstants {

	private ModellingProcessorConfig config;

	public GetModifiedModelsProcessor(ModellingProcessorConfig config) {
		this.config = config;
	}
	
	@Override
	public ModelSelection process() {
		String accessId = context().getSession().getAccessId();
		
		// Check if trunk stage exists
		boolean trunkExists = ModellingTools.trunkExists(accessId, session());
		
		ModelSelection selection = ModelSelection.T.create();
		if (!trunkExists) {
			List<Notification> notifications =
					Notifications.build()
					.add()
						.message()
							.confirmInfo("No modified models found!") 
						.close()
					.list();
			
			selection.setNotifications(notifications);
			return selection;
		}
		
		
		// Get modified models from trunk
		GetModifiedModelsForStage getModifiedModels = GetModifiedModelsForStage.T.create();
		getModifiedModels.setName("trunk");
		getModifiedModels.setServiceId(accessId);
		
		List<GmMetaModel> modifiedModels = getModifiedModels.eval(session()).get();
		Set<String> names = modifiedModels.stream().map(m -> m.getName()).collect(Collectors.toSet());
		
		
		// Query for models to have them available in our session
		EntityQuery query = EntityQueryBuilder.from(GmMetaModel.T) //
				.where() //
					.property(GmMetaModel.name).in(names) //
				.done();
		
		List<GmMetaModel> models = session().query().entities(query).list();
		
		
		// return response
		if (models.size() == 0) {
			String msg = "No modified models found!";
			List<Notification> notifications =
					Notifications.build()
						.add()
							.message()
								.confirmInfo(msg) 
							.close()
					.list();
			
			selection.setNotifications(notifications);
			return selection;
		}
		
		String msg = models.size() + " modified models found!\n\n" + models.stream().map( m -> {
			return m.getName();
		}).collect( Collectors.joining( "\n"));	
		
		List<Notification> notifications =
				Notifications.build()
					.add()
						.message()
							.confirmInfo(msg) 
						.close()
					.add()
						.command().gotoModelPath("Goto Model Selection").addElement(selection)
					.close()
				.close()
				.list();
		
		selection.setNotifications(notifications);
		
		selection.setModels(models);
		return selection;
	}

}
