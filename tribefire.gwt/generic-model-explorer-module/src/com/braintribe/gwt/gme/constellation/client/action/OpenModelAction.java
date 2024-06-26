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
package com.braintribe.gwt.gme.constellation.client.action;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gme.constellation.client.LocalizedText;
import com.braintribe.gwt.gme.constellation.client.MasterDetailConstellation;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.RootPathElement;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.workbench.HyperlinkAction;

import com.google.gwt.http.client.URL;

public class OpenModelAction extends ModelAction {
	
	private String baseUrl;	
	private ExplorerConstellation explorerConstellation;
	private Supplier<MasterDetailConstellation> masterDetailConstellationProvider;	
	private GmMetaModel currentSelectedModel;
	
	public OpenModelAction() {
		setName(LocalizedText.INSTANCE.modelViewer());
		setIcon(ConstellationResources.INSTANCE.modelViewer());
		setHoverIcon(ConstellationResources.INSTANCE.modelViewerBig());
		setHidden(true);
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
	}
	
	@Required
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}
	
	@Required
	public void setMasterDetailConstellationProvider(Supplier<MasterDetailConstellation> masterDetailConstellationProvider) {
		this.masterDetailConstellationProvider = masterDetailConstellationProvider;
	}

	@Override
	protected void updateVisibility() {
		setHidden(true);
		currentSelectedModel = null;
		if (modelPaths == null || modelPaths.size() != 1)
			return;
		
		List<ModelPath> modelPathsList = modelPaths.get(0);
		for (ModelPath modelPath : modelPathsList) {
			if (!modelPath.isEmpty()) {
				Object value = modelPath.get(0).getValue();
				if (value instanceof GmMetaModel) {
					currentSelectedModel = (GmMetaModel) value;
					setHidden(false);
					return;
				}
			}
		}
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		if (currentSelectedModel == null)
			return;
		
		String modelName = currentSelectedModel.getName().replace(":",".").replace("#", "-");
		String url = baseUrl + URL.encodeQueryString(modelName) + ".html";
		MasterDetailConstellation masterDetailConstellation;
		try {
			masterDetailConstellation = masterDetailConstellationProvider.get();
			masterDetailConstellation.configureGmSession(explorerConstellation.getGmSession());
			ModelPath modelPath = new ModelPath();
			HyperlinkAction hyperlinkAction = HyperlinkAction.T.create();
			hyperlinkAction.setUrl(url);
			modelPath.add(new RootPathElement(HyperlinkAction.T, hyperlinkAction));
			masterDetailConstellation.setContent(modelPath);
			explorerConstellation.maybeCreateVerticalTabElement(null, modelName, modelName, () -> masterDetailConstellation, null, null, false);
			masterDetailConstellation.collapseOrExpandDetailView(true);
		} catch (RuntimeException e) {
			ErrorDialog.show("Error while open external link", e);
			e.printStackTrace();
		}
	}

}
