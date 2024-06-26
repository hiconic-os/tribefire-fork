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
package com.braintribe.gwt.gme.notification.client.expert;

import com.braintribe.cfg.Required;
import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.logging.client.Logger;
import com.braintribe.model.processing.notification.api.CommandExpert;
import com.braintribe.model.query.Query;
import com.braintribe.model.uicommand.RunQuery;

public class RunQueryExpert implements CommandExpert<RunQuery> {

	private static Logger logger = new Logger(RunQueryExpert.class);
	private ExplorerConstellation explorerConstellation;
	
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}	
	
	@Override
	public void handleCommand(RunQuery command) {
		if (command == null)
			return;
		
		Query query = command.getQuery();
		String name = (command.getName() != null) ? command.getName() : "Query";
		doQuery(name, query);
	}

	public void doQuery(String name, Query query) {
		if (query == null) {
			logger.info("RunQuery - no Query defined!");
			return;
		}
		
		explorerConstellation.maybeCreateVerticalTabElement(null, name, name, explorerConstellation.provideBrowsingConstellation(name, query), null,
				null, false);
	}
}
