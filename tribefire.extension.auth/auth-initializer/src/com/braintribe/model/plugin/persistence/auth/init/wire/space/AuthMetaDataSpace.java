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
package com.braintribe.model.plugin.persistence.auth.init.wire.space;

import com.braintribe.gm.persistence.initializer.support.integrity.wire.contract.CoreInstancesContract;
import com.braintribe.gm.persistence.initializer.support.wire.space.AbstractInitializerSpace;
import com.braintribe.logging.Logger;
import com.braintribe.model.plugin.persistence.auth.init.wire.contract.AuthMetaDataContract;
import com.braintribe.model.plugin.persistence.auth.init.wire.contract.AuthRuntimePropertyDefinitions;
import com.braintribe.model.plugin.persistence.auth.init.wire.contract.ExistingInstancesContract;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

@Managed
public class AuthMetaDataSpace extends AbstractInitializerSpace implements AuthMetaDataContract {

	private static final Logger logger = Logger.getLogger(AuthMetaDataSpace.class);

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private AuthRuntimePropertyDefinitions properties;

	@Import
	private CoreInstancesContract coreInstances;


	@Override
	public void metaData() {

		BasicModelMetaDataEditor deploymentModelEditor = new BasicModelMetaDataEditor(existingInstances.deploymentModel());


	}
}
