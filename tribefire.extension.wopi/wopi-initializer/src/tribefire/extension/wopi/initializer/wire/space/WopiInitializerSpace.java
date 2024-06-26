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
package tribefire.extension.wopi.initializer.wire.space;

import com.braintribe.logging.Logger;
import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.model.wopi.service.WacHealthCheckProcessor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.cortex.model.check.CheckCoverage;
import tribefire.cortex.model.check.CheckWeight;
import tribefire.extension.wopi.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.wopi.initializer.wire.contract.RuntimePropertiesContract;
import tribefire.extension.wopi.initializer.wire.contract.WopiInitializerContract;
import tribefire.extension.wopi.processing.EntityStorageType;
import tribefire.extension.wopi.processing.StorageType;
import tribefire.extension.wopi.templates.api.WopiTemplateContext;
import tribefire.extension.wopi.templates.api.WopiTemplateDatabaseContext;
import tribefire.extension.wopi.templates.wire.contract.WopiTemplatesContract;

@Managed
public class WopiInitializerSpace extends AbstractInitializerSpace implements WopiInitializerContract {

	private static final Logger logger = Logger.getLogger(WopiInitializerSpace.class);

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private RuntimePropertiesContract properties;

	@Import
	private WopiTemplatesContract wopiTemplates;

	@Managed
	public WopiTemplateDatabaseContext defaultDatabaseContext() {

		//@formatter:off
		WopiTemplateDatabaseContext context = WopiTemplateDatabaseContext.builder()
				.setHibernateDialect(properties.WOPI_DB_HIBERNATEDIALECT())
				.setTablePrefix(properties.WOPI_TABLE_PREFIX())
				.setDatabaseDriver(properties.WOPI_DB_DRIVER())
				.setDatabaseUrl(properties.WOPI_DB_URL())
				.setDatabaseUsername(properties.WOPI_DB_USER())
				.setDatabasePassword(properties.WOPI_DB_PASSWORD())
				.setMinPoolSize(properties.WOPI_DB_MIN_POOL_SIZE())
				.setMaxPoolSize(properties.WOPI_DB_MAX_POOL_SIZE()
			).build();
		//@formatter:on
		return context;

	}

	@Managed
	public WopiTemplateContext defaultWopiTemplateContext() {

		StorageType storageType = properties.WOPI_STORAGE_TYPE();
		if (storageType == StorageType.external) {
			// Well, some other intializer has to take care of it
			logger.debug(() -> "External storage type configured. Another initializer will have to take care of that. Using fs for the time being.");
			storageType = StorageType.fs;
		}

		WopiTemplateDatabaseContext wopiTemplateDatabaseContext = null;
		EntityStorageType entityStorageType = properties.WOPI_ENTITY_STORAGE_TYPE();
		if (entityStorageType == EntityStorageType.db || storageType == StorageType.db) {
			wopiTemplateDatabaseContext = defaultDatabaseContext();
		}

		//@formatter:off
		WopiTemplateContext bean = WopiTemplateContext.builder(null)
				.setContext(properties.WOPI_CONTEXT())
				.setEntityFactory(super::create)
				.setWopiModule(existingInstances.module())
				.setLookupFunction(super::lookup)
				.setLookupExternalIdFunction(super::lookupExternalId)
				.setStorageType(storageType)
				.setDatabaseContext(wopiTemplateDatabaseContext)
				.setStorageFolder(properties.WOPI_STORAGE_FOLDER())
				.setWacDiscoveryEndpoint("http://wopi.agile-documents.com/hosting/discovery")
				.setCustomPublicServicesUrl(null)
				.build();
		//@formatter:on
		return bean;
	}

	@Override
	public void setupDefaultConfiguration() {
		wopiTemplates.setupWopi(defaultWopiTemplateContext());
	}

	@Override
	@Managed
	public WacHealthCheckProcessor healthCheckProcessor() {
		WacHealthCheckProcessor bean = create(WacHealthCheckProcessor.T);
		bean.setModule(existingInstances.module());
		bean.setName("WOPI Check Processor");
		bean.setExternalId("wopi.healthzProcessor");
		return bean;
	}

	@Override
	@Managed
	public CheckBundle functionalCheckBundle() {
		CheckBundle bean = create(CheckBundle.T);
		bean.setModule(existingInstances.module());
		bean.getChecks().add(healthCheckProcessor());
		bean.setName("WOPI Checks");
		bean.setWeight(CheckWeight.under1s);
		bean.setCoverage(CheckCoverage.functional);
		bean.setIsPlatformRelevant(false);

		return bean;
	}
}
