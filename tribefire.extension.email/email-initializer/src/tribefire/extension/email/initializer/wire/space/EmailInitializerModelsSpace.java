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
package tribefire.extension.email.initializer.wire.space;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.email.initializer.wire.contract.EmailInitializerModelsContract;
import tribefire.extension.email.initializer.wire.contract.ExistingInstancesContract;

@Managed
public class EmailInitializerModelsSpace extends AbstractInitializerSpace implements EmailInitializerModelsContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Override
	@Managed
	public GmMetaModel configuredEmailApiModel() {
		GmMetaModel bean = create(GmMetaModel.T);

		GmMetaModel emailApiModel = existingInstances.serviceModel();

		bean.setName(ExistingInstancesContract.GROUP_ID + ":configured-email-service-model");
		bean.setVersion(emailApiModel.getVersion());
		bean.getDependencies().add(emailApiModel);

		return bean;
	}
}
