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

import com.braintribe.model.processing.accessrequest.api.AbstractStatefulAccessRequestProcessor;

import tribefire.extension.modelling.commons.ModellingConstants;
import tribefire.extension.modelling.model.api.data.ModelSelection;
import tribefire.extension.modelling.model.api.request.ModellingResponse;
import tribefire.extension.modelling.model.api.request.TransferModifiedModels;

public class TransferModifiedModelsProcessor
		extends AbstractStatefulAccessRequestProcessor<TransferModifiedModels, ModellingResponse>
		implements ModellingConstants {

	private ModellingProcessorConfig config;

	public TransferModifiedModelsProcessor(ModellingProcessorConfig config) {
		this.config = config;
	}
	
	@Override
	public ModellingResponse process() {
		
		ModelSelection models = request().getModels();
		String transferOperation = request().getTransferOperation();
		
		return null;
	}

}
