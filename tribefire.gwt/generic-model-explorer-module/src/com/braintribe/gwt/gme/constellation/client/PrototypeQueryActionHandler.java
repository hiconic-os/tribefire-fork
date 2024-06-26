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

import com.braintribe.gwt.gmview.util.client.GMEIconUtil;
import com.braintribe.gwt.gmview.util.client.GMEMetadataUtil;
import com.braintribe.gwt.gmview.util.client.GMEUtil;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.StandardCloningContext;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.Query;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.workbench.PrototypeQueryAction;
import com.google.gwt.user.client.ui.Widget;

public class PrototypeQueryActionHandler extends AbstractQueryActionHandler<PrototypeQueryAction> {
	
	@Override
	public void perform(WorkbenchActionContext<PrototypeQueryAction> workbenchActionContext) {
		Widget parentPanel = getParentPanel(workbenchActionContext.getPanel());
		ExplorerConstellation explorerConstellation = null;
		SelectionConstellation selectionConstellation = null;
		
		if (parentPanel instanceof ExplorerConstellation)
			explorerConstellation = (ExplorerConstellation) parentPanel;
		else if (parentPanel instanceof SelectionConstellation)
			selectionConstellation = (SelectionConstellation) parentPanel;
		
		if (explorerConstellation == null && selectionConstellation == null)
			return;

		PrototypeQueryAction queryAction = workbenchActionContext.getWorkbenchAction();
		Query query = queryAction.getQuery();
		String entityTypeSignature = null;
		if (query instanceof EntityQuery) {
			EntityType<EntityQuery> entityQueryType = query.entityType();
			query = (EntityQuery) entityQueryType.clone(new StandardCloningContext(), query, null);
		} else if (query instanceof SelectQuery) {
			entityTypeSignature = GMEUtil.getSingleEntityTypeSignatureFromSelectQuery((SelectQuery) query);
			if (entityTypeSignature != null) { //TODO: should we handle cases where this is not true as well?
				EntityType<SelectQuery> selectQueryType = query.entityType();
				query = (SelectQuery) selectQueryType.clone(new StandardCloningContext(), query, null);
			}
		}
		
		String name = getName(workbenchActionContext);
		if (wasHandledInSameTab(workbenchActionContext, explorerConstellation, name, query))
			return;
		
		if (query instanceof EntityQuery) {
			EntityType<?> entityType = GMF.getTypeReflection().getEntityType(((EntityQuery) query).getEntityTypeSignature());
			prepareSingleEntityVerticalTabElement(name, query, entityType, explorerConstellation, selectionConstellation, workbenchActionContext);
		} else if (query instanceof SelectQuery && entityTypeSignature != null) { //TODO: should we handle cases where this is not true as well?
			EntityType<?> entityType = GMF.getTypeReflection().getEntityType(entityTypeSignature);
			prepareSingleEntityVerticalTabElement(name, query, entityType, explorerConstellation, selectionConstellation, workbenchActionContext);
		}
	}
	
	private void prepareSingleEntityVerticalTabElement(String name, Query query, EntityType<?> entityType, ExplorerConstellation explorerConstellation,
			SelectionConstellation selecConstellation, final WorkbenchActionContext<PrototypeQueryAction> workbenchActionContext) {
		ModelMdResolver modelMdResolver = explorerConstellation != null
				? explorerConstellation.getGmSession().getModelAccessory().getMetaData() : selecConstellation.getMetaDataResolver();
		String useCase = explorerConstellation != null ? explorerConstellation.getUseCase() : selecConstellation.getUseCase();
		
		String description = GMEMetadataUtil.getEntityNameMDOrShortName(entityType, modelMdResolver, useCase);
		
		if (explorerConstellation != null) {
			explorerConstellation.maybeCreateVerticalTabElement(workbenchActionContext, name, description,
					explorerConstellation.provideBrowsingConstellation(name, query), GMEIconUtil.getSmallIcon(workbenchActionContext), query, false)
					.andThen(result -> configureStorageHandle(result, workbenchActionContext));
		} else if (selecConstellation != null) {
			selecConstellation.maybeCreateVerticalTabElement(workbenchActionContext, name, name, description,
					GMEIconUtil.getSmallIcon(workbenchActionContext), selecConstellation.provideSelectionBrowsingConstellation(name, query, false),
					false, true, false);
		}
	}
	
	private boolean wasHandledInSameTab(WorkbenchActionContext<?> workbenchActionContext, ExplorerConstellation explorerConstellation, String name,
			Query query) {
		if (workbenchActionContext.isHandleInNewTab() || explorerConstellation == null)
			return false;
		
		BrowsingConstellation browsingConstellation = getParentBrowsingConstellation(workbenchActionContext.getPanel());
		if (browsingConstellation == null)
			return false;
		
		explorerConstellation.maybeCreateQueryTetherBarElement(name, query, null, browsingConstellation);
		
		return true;
	}
	
	private BrowsingConstellation getParentBrowsingConstellation(Object panel) {
		if (panel instanceof BrowsingConstellation)
			return (BrowsingConstellation) panel;
		
		if (panel instanceof Widget)
			return getParentBrowsingConstellation(((Widget) panel).getParent());
		
		return null;
	}

}
