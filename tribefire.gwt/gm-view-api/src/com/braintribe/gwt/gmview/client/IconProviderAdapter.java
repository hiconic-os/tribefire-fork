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
package com.braintribe.gwt.gmview.client;

import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;


/**
 * Adapter for the {@link IconProvider} interface.
 * @author michel.docouto
 *
 */
public class IconProviderAdapter implements IconProvider {

	@Override
	public IconAndType apply(ModelPath index) throws RuntimeException {
		return null;
	}

	@Override
	public void configureGmSession(PersistenceGmSession gmSession) {
		//NOP
	}

	@Override
	public PersistenceGmSession getGmSession() {
		return null;
	}

	@Override
	public void configureUseCase(String useCase) {
		//NOP
	}

	@Override
	public String getUseCase() {
		return null;
	}

}
