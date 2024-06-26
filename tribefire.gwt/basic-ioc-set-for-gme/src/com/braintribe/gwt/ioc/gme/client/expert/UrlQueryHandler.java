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
package com.braintribe.gwt.ioc.gme.client.expert;

import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.gmview.client.ModelEnvironmentSetListener;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.logging.client.Logger;
import com.braintribe.gwt.notification.client.Notification;
import com.braintribe.gwt.notification.client.NotificationListener;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.Paging;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.Restriction;
import com.braintribe.model.query.conditions.Condition;
import com.braintribe.model.query.conditions.ValueComparison;
import com.google.gwt.core.client.Scheduler;

public class UrlQueryHandler implements NotificationListener<UrlQueryConfig>, ModelEnvironmentSetListener {
	
	private static Logger logger = new Logger(UrlQueryHandler.class);
	
	private ExplorerConstellation explorerConstellation;
	boolean modelEnvironmentSet = false;
	private EntityQuery entityQuery;
	
	/**
	 * Configures the required {@link ExplorerConstellation} used for opening a new tab with the query.
	 */
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}
	
	@Override
	public void onNotificationReceived(Notification<UrlQueryConfig> notification) {
		UrlQueryConfig urlQueryConfig = notification.getData();
		
		String typeSignature = urlQueryConfig.getTypeSignature();
		if (typeSignature == null)
			logger.error("The typeSignature is mandatory and it was not provided.");
		
		Condition condition = null;
		String propertyName = urlQueryConfig.getPropertyName();
		if (propertyName != null && !propertyName.isEmpty()) {
			condition = ValueComparison.T.create();
			ValueComparison valueComparison = (ValueComparison) condition;
			PropertyOperand propertyOperand = PropertyOperand.T.create();
			propertyOperand.setPropertyName(propertyName);
			valueComparison.setLeftOperand(propertyOperand);
			valueComparison.setOperator(Operator.equal);
			valueComparison.setRightOperand(urlQueryConfig.getPropertyValue());	
		}		
		
		Restriction restriction = Restriction.T.create();
		Paging paging = Paging.T.create();
		paging.setPageSize(25);
		paging.setStartIndex(0);
		restriction.setPaging(paging);
		
		if (condition != null)
			restriction.setCondition(condition);

		entityQuery = EntityQuery.T.create();
		entityQuery.setEntityTypeSignature(typeSignature);
		entityQuery.setRestriction(restriction);
		
		Scheduler.get().scheduleDeferred(this::openAndRunQuery);
	}

	@Override
	public void onModelEnvironmentSet() {
		modelEnvironmentSet = true;
		Scheduler.get().scheduleDeferred(this::openAndRunQuery);
	}
	
	private void openAndRunQuery() {
		if (entityQuery == null || !modelEnvironmentSet)
			return;
		
		GlobalState.mask("Querying");
		explorerConstellation.maybeCreateVerticalTabElement(null, "Query", "Query",
				explorerConstellation.provideBrowsingConstellation("Query", entityQuery), null, null, false);
		Scheduler.get().scheduleFixedDelay(() -> {
			GlobalState.unmask();
			return false;
		}, 500);
		
	}

}
