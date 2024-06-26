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
package com.braintribe.gwt.gme.constellation.client;

import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.RootPathElement;
import com.braintribe.model.path.GmModelPath;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionHandler;
import com.braintribe.model.workbench.ModelLinkAction;

public class ModelLinkActionHandler implements WorkbenchActionHandler<ModelLinkAction> {
	
	private ExplorerConstellation explorerConstellation;
	
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}

	@Override
	public void perform(WorkbenchActionContext<ModelLinkAction> workbenchActionContext) {
		GmModelPath gmModelPath = workbenchActionContext.getWorkbenchAction().getPath();
		
		ModelPath modelPath = new ModelPath();
		modelPath.add(new RootPathElement(GMF.getTypeReflection().getType(gmModelPath.getElements().get(0).getTypeSignature()), gmModelPath.getElements().get(0).getValue()));
		explorerConstellation.showEntityVerticalTabElement(modelPath, false, false, false);
	}

}
