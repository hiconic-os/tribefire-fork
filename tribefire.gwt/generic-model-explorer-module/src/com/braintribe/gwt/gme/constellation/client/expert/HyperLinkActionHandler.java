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
package com.braintribe.gwt.gme.constellation.client.expert;

import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gme.constellation.client.LocalizedText;
import com.braintribe.gwt.gme.constellation.client.MasterDetailConstellation;
import com.braintribe.gwt.gmresourceapi.client.GmImageResource;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.RootPathElement;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionHandler;
import com.braintribe.model.resource.AdaptiveIcon;
import com.braintribe.model.resource.Icon;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.SimpleIcon;
import com.braintribe.model.workbench.HyperlinkAction;
import com.braintribe.utils.i18n.I18nTools;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;

public class HyperLinkActionHandler implements WorkbenchActionHandler<HyperlinkAction>{
	
	//private static final String BLANK_TARGET = "_blank";
	private static final String INLINE = "inline";
	//private static final String INLINE_PP_TARGET = "inline_pp";
	//private static final String NEW_TARGET = "_new";
	
	private ExplorerConstellation explorerConstellation;
	private Supplier<MasterDetailConstellation> masterDetailConstellationProvider;
	private Supplier<? extends PersistenceGmSession> sessionSupplier;
	
	public void setSession(Supplier<? extends PersistenceGmSession> sessionSupplier) {
		this.sessionSupplier = sessionSupplier;
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
	public void perform(WorkbenchActionContext<HyperlinkAction> workbenchActionContext) {
		try {
			HyperlinkAction hyperlinkAction = workbenchActionContext.getWorkbenchAction();
			if (hyperlinkAction == null)
				return;
			
			String target = hyperlinkAction.getTarget();
			String url = hyperlinkAction.getUrl();
			if (target != null && !(target.equals(INLINE))){
				Window.open(url != null ? url : "#", target, "");
				return;
			}
			
			MasterDetailConstellation masterDetailConstellation = masterDetailConstellationProvider.get();
			masterDetailConstellation.configureGmSession(explorerConstellation.getGmSession());
			List<ModelPath> workbenchActionModelPaths = workbenchActionContext.getModelPaths();
			ModelPath modelPath = workbenchActionModelPaths != null && !workbenchActionModelPaths.isEmpty() ? workbenchActionModelPaths.get(0)
					: new ModelPath();
			modelPath.add(new RootPathElement(HyperlinkAction.T, workbenchActionContext.getWorkbenchAction()));
			masterDetailConstellation.setContent(modelPath);
			explorerConstellation.maybeCreateVerticalTabElement(workbenchActionContext, I18nTools.getDefault(hyperlinkAction.getDisplayName(), ""),
					url, () -> masterDetailConstellation, getIcon(workbenchActionContext.getFolder() != null ? workbenchActionContext.getFolder().getIcon() : null), null, false, false);
			
			if (target != null && target.equals(INLINE))
				masterDetailConstellation.collapseOrExpandDetailView(true);
		} catch (Exception ex) {
			ErrorDialog.show(LocalizedText.INSTANCE.errorOpeningExternalLink(), ex);
			ex.printStackTrace();
		}
	}
	
	public ImageResource getIcon(Icon icon) {
		if (icon == null)
			return null;
					
		PersistenceGmSession session = sessionSupplier.get();
		if (icon instanceof SimpleIcon) {
			SimpleIcon si = (SimpleIcon)icon;
			return new GmImageResource(si.getImage(), session.resources().url(si.getImage()).asString());
		} else if(icon instanceof AdaptiveIcon) {
			AdaptiveIcon ai = (AdaptiveIcon) icon;
			for (Resource r : ai.getRepresentations())
				return new GmImageResource(r, session.resources().url(r).asString());
		}
		
		return null;
	}

}
