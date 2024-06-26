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
import com.braintribe.model.generic.session.exception.GmSessionRuntimeException;
import com.braintribe.model.processing.notification.api.CommandExpert;
import com.braintribe.model.processing.query.api.shortening.SignatureExpert;
import com.braintribe.model.processing.query.expander.QueryTypeSignatureExpanderBuilder;
import com.braintribe.model.processing.query.parser.QueryParser;
import com.braintribe.model.processing.query.parser.api.GmqlParsingError;
import com.braintribe.model.processing.query.parser.api.ParsedQuery;
import com.braintribe.model.processing.query.shortening.SmartShortening;
import com.braintribe.model.query.Query;
import com.braintribe.model.uicommand.RunQueryString;

public class RunQueryStringExpert implements CommandExpert<RunQueryString> {

	private static Logger logger = new Logger(RunQueryStringExpert.class);
	private ExplorerConstellation explorerConstellation;
	private SignatureExpert shorteningMode = null;

	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}

	@Override
	public void handleCommand(RunQueryString command) {
		String queryString = command.getQuery();

		Query query = null;
		try {
			query = parseQuery(queryString);
		} catch (GmSessionRuntimeException ex) {
			logger.error("RunQueryString - the given query is not valid!", ex);
			return;
		}
		
		if (query == null) {
			logger.info("RunQueryString - the parsed query is not valid!");
			return;
		}
		
		String name = command.getName() != null ? command.getName() : "Query";
		explorerConstellation.maybeCreateVerticalTabElement(null, name, name, explorerConstellation.provideBrowsingConstellation(name, query), null,
				null, false);
	}
	
	private Query parseQuery (String queryString) {
		ParsedQuery parsedQuery = QueryParser.parse(queryString);
		if (parsedQuery.getErrorList().isEmpty() && parsedQuery.getIsValidQuery()) {
			try {
				// Expand type signatures of parsed query with defined shortening mode
				return QueryTypeSignatureExpanderBuilder.create(parsedQuery.getQuery(), getShorteningMode()).done();
			} catch (final Exception e) {
				StringBuilder msg = new StringBuilder();
				msg.append("The query: "+queryString+" could not be parsed to a valid query.");
				msg.append("\n").append(e.getMessage());
				throw new GmSessionRuntimeException(e);
			}
		}
		
		StringBuilder msg = new StringBuilder();
		msg.append("The query: "+queryString+" could not be parsed to a valid query.");
		for (GmqlParsingError error : parsedQuery.getErrorList())
			msg.append("\n").append(error.getMessage());
		throw new GmSessionRuntimeException(msg.toString());
	}
	
	private SignatureExpert getShorteningMode() {
		if (shorteningMode == null)
			shorteningMode = new SmartShortening(explorerConstellation.getGmSession().getModelAccessory().getOracle());
		
		return shorteningMode;
	}
}
