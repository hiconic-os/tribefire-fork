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
package com.braintribe.swagger.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.braintribe.logging.Logger;
import com.braintribe.model.exchange.ExchangePackage;
import com.braintribe.model.exchange.ExchangePayload;
import com.braintribe.model.exchange.GenericExchangePayload;
import com.braintribe.model.exchangeapi.Import;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.notification.Level;
import com.braintribe.model.processing.assembly.sync.impl.AssemblyImporter;
import com.braintribe.model.processing.assembly.sync.impl.ExchangeImporterContext;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.impl.session.collection.EnhancedSet;
import com.braintribe.model.resource.Resource;

/**
 * Derived from {@link com.braintribe.model.processing.exchange.service.Importer}
 *
 */
public class ExchangePackageImporter {
	
	private static final Logger logger = Logger.getLogger(ExchangePackageImporter.class);
	
	private Import request;
	private ExchangePackage exchangePackage;
	private PersistenceGmSession session;
	private Set<ExchangePayload> syncedPayloads = new HashSet<>();
	private Set<ExchangePayload> failedPayloads = new HashSet<>();
	private Set<GenericEntity> missingExternalReferences = new HashSet<>();
	private Set<GenericEntity> externalReferences = new HashSet<>();
	private Map<String, Resource> callResourcesPerGid = new HashMap<>();
	private List<Resource> resources;
	
	public ExchangePackageImporter(Import request, PersistenceGmSession session, List<Resource> resources) {
		this.request = request;
		this.session = session;
		this.resources = resources;
		this.exchangePackage = request.getExchangePackage();
		fillCallResources();
	}
	
	public List<GmMetaModel> run() {
		
		if (exchangePackage == null) {
			return new ArrayList<>();
		}
		List<ExchangePayload> payloads = exchangePackage.getPayloads();
		
		if (payloads.isEmpty()) {
			return new ArrayList<>();
		}
		
		checkExternalReferences(payloads);
		if (!request.getCreateShallowInstanceForMissingReferences()) {
			if (!missingExternalReferences.isEmpty()) {
				return new ArrayList<>();
			}
		}
		
		final Set<ExchangePayload> exchangePayloads = syncPayloads(payloads);
		return exchangePayloads.stream()
			.peek(payload -> logger.info("Found exchange payload: " + payload))
			.filter(GenericExchangePayload.class::isInstance)
			.map(payload -> (GenericExchangePayload)payload)
			.map(payload -> (EnhancedSet<?>)payload.getAssembly())
			.flatMap(Set::stream)
			.peek(item -> logger.info("Found item: " + item))
			.filter(GmMetaModel.class::isInstance)
			.map(item -> (GmMetaModel)item)
			.peek(model -> logger.info("Found model: " + model))
			.collect(Collectors.toList());
	}
	
	private void checkExternalReferences(List<ExchangePayload> payloads) {
		for (ExchangePayload payload : payloads) {
			for (GenericEntity externalReference : payload.getExternalReferences()) {
				
				externalReferences.add(externalReference);

				GenericEntity entity = null;
				
				try {
					entity = session.query().findEntity(externalReference.getGlobalId());
				}
				catch(com.braintribe.model.generic.session.exception.GmSessionException e) {
					logger.error("Coulnd't resolve external reference: " + externalReference, e);
				}
				
				if (entity == null) {
					logger.error("Coulnd't resolve external reference: " + externalReference);
					missingExternalReferences.add(entity);
					Level level = request.getCreateShallowInstanceForMissingReferences() ? Level.INFO : Level.WARNING;
				}
			}
		}
	}

	private Set<ExchangePayload> syncPayloads(Collection<ExchangePayload> payloads) {		
		for (ExchangePayload payload : payloads) {
			
			ExchangeImporterContext importContext = 
					new ExchangeImporterContext(
							session, 
							payload, 
							request.getIncludeEnvelope(),
							request.getRequiresGlobalId(),
							session.getAccessId());
			
			try {
				ExchangePayload syncedPayload = AssemblyImporter.importAssembly(importContext);
				syncedPayloads.add(syncedPayload);
			} catch (Exception e) {
				notifyError(e);
				failedPayloads.add(payload);
			}
		}
		return syncedPayloads;
	}

	private void notifyError(Exception e) {
		logger.error("Error in Importer.",e);
		String errorMessage = e.getMessage();
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		if (rootCause != null) {
			errorMessage = rootCause.getMessage();
		}
	}

	private void fillCallResources() {
		if (resources != null) {
			resources.forEach((r) -> callResourcesPerGid.put(r.getGlobalId(),r));
		}
	}
}
