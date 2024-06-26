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

import java.util.function.Supplier;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessor;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessors;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

import tribefire.extension.modelling.model.api.data.ModelsToPublish;
import tribefire.extension.modelling.model.api.request.GetModelsToPublish;
import tribefire.extension.modelling.model.api.request.GetModifiedModels;
import tribefire.extension.modelling.model.api.request.ModellingRequest;
import tribefire.extension.modelling.model.api.request.ModellingResponse;
import tribefire.extension.modelling.model.api.request.TransferModifiedModels;

public class ModellingProcessor<M extends ModellingRequest, R extends Object>
		implements AccessRequestProcessor<M, R>, ModellingProcessorConfig {

	private Supplier<PersistenceGmSession> cortexSessionProvider;
	
	@Configurable @Required
	public void setCortexSessionProvider(Supplier<PersistenceGmSession> cortexSessionProvider) {
		this.cortexSessionProvider = cortexSessionProvider;
	}
	
	//
	// Preparation: Access Request Processor
	//
	
	@Override
	public R process(AccessRequestContext<M> context) {
		return dispatcher.process(context);
	}
	
	private AccessRequestProcessor<M, R> dispatcher = AccessRequestProcessors.dispatcher(config->{

		config.register(GetModelsToPublish.T, this::getModelsToPublish);
		
		// Stateful
		config.register(TransferModifiedModels.T, () -> new TransferModifiedModelsProcessor(this));
		config.register(GetModifiedModels.T, () -> new GetModifiedModelsProcessor(this));
		
	});
	
	//
	// Config Overrides
	//
	
	//
	// Expert Implementations
	//
	
	private ModellingResponse transferModifiedModels(AccessRequestContext<TransferModifiedModels> context) {
		
		return null;
	}
	
	private ModelsToPublish getModelsToPublish(AccessRequestContext<GetModelsToPublish> context) {

		return null;
	}
	
}
