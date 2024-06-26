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
package tribefire.extension.model_browser.module.initializer;

import com.braintribe.model.processing.session.api.collaboration.DataInitializer;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;

import tribefire.extension.modelbrowser.model.deployment.ModelBrowser;

/**
 * Creation of a ModelBrowser. 
 * 
 * @author Dirk Scheffler
 *
 */

public class ModelBrowserInitializer implements DataInitializer {
	@Override
	public void initialize(PersistenceInitializationContext context) {
		ManagedGmSession session = context.getSession();
		
		ModelBrowser modelBrowser = session.create(ModelBrowser.T);
		modelBrowser.setExternalId("web-terminal.model-browser");
		modelBrowser.setGlobalId("199234f7-f4d3-477e-bacb-85e82a6dc8c7");
		modelBrowser.setPathIdentifier("model-browser");
		modelBrowser.setName("Model Browser");
	}
}
