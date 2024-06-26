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
package tribefire.extension.wopi.templates.api;

import java.util.function.Function;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.deployment.Module;
import com.braintribe.model.descriptive.HasExternalId;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.resource.configuration.ExternalResourcesContext;

import tribefire.extension.wopi.processing.StorageType;

/**
 * Template Context Builder
 * 
 *
 */
public interface WopiTemplateContextBuilder {

	// -----------------------------------------------------------------------
	// Common
	// -----------------------------------------------------------------------

	/**
	 * Check {@link WopiTemplateContext#getWopiModule()}
	 */
	WopiTemplateContextBuilder setWopiModule(Module module);

	/**
	 * Check {@link WopiTemplateContext#getContext()}
	 */
	WopiTemplateContextBuilder setContext(String context);

	WopiTemplateContext build();

	// -----------------------------------------------------------------------
	// WopiAccess
	// -----------------------------------------------------------------------

	/**
	 * Check {@link WopiTemplateContext#getAccess()}
	 */
	WopiTemplateContextBuilder setAccess(IncrementalAccess access);

	/**
	 * Check {@link WopiTemplateContext#getStorageFolder()}
	 */
	WopiTemplateContextBuilder setStorageFolder(String storageFolder);

	/**
	 * Check {@link WopiTemplateContext#getDatabaseContext()}
	 */
	WopiTemplateContextBuilder setDatabaseContext(WopiTemplateDatabaseContext wopiDatabaseCon);

	/**
	 * Check {@link WopiTemplateContext#getExternalResourcesContext()}
	 */
	WopiTemplateContextBuilder setExternalResourcesContext(ExternalResourcesContext externalResourcesContext);

	/**
	 * Check {@link WopiTemplateContext#getStorageType()}
	 */
	WopiTemplateContextBuilder setStorageType(StorageType storageType);

	// -----------------------------------------------------------------------
	// WopiWacConnector
	// -----------------------------------------------------------------------

	/**
	 * Check {@link WopiTemplateContext#getWacDiscoveryEndpoint()}
	 */
	WopiTemplateContextBuilder setWacDiscoveryEndpoint(String wacDiscoveryEndpoint);

	WopiTemplateContextBuilder setCustomPublicServicesUrl(String customPublicServicesUrl);

	WopiTemplateContextImpl setConnectionRequestTimeoutInMs(Integer connectionRequestTimeoutInMs);

	WopiTemplateContextImpl setConnectTimeoutInMs(Integer connectTimeoutInMs);

	WopiTemplateContextImpl setSocketTimeoutInMs(Integer socketTimeoutInMs);

	WopiTemplateContextImpl setConnectionRetries(Integer connectionRetries);

	WopiTemplateContextImpl setDelayOnRetryInMs(Integer delayOnRetryInMs);

	// -----------------------------------------------------------------------
	// WopiServiceProcessor
	// -----------------------------------------------------------------------

	WopiTemplateContextImpl setWopiServiceProcessorLogWarningThresholdInMs(Long wopiServiceProcessorLogWarningThresholdInMs);

	WopiTemplateContextImpl setWopiServiceProcessorLogErrorThresholdInMs(Long wopiServiceProcessorLogErrorThresholdInMs);

	// -----------------------------------------------------------------------
	// ExpireWopiSessionWorker
	// -----------------------------------------------------------------------

	WopiTemplateContextBuilder setExpireWopiSessionIntervalInMs(Long expireWopiSessionIntervalInMs);

	// -----------------------------------------------------------------------
	// CleanupWopiSessionWorker
	// -----------------------------------------------------------------------

	WopiTemplateContextBuilder setCleanupWopiSessionIntervalInMs(Long cleanupWopiSessionIntervalInMs);

	// -----------------------------------------------------------------------
	// WopiApp
	// -----------------------------------------------------------------------

	WopiTemplateContextBuilder setLockExpirationInMs(Long lockExpirationInMs);

	WopiTemplateContextBuilder setWopiSessionExpirationInMs(Long wopiSessionExpirationInMs);

	WopiTemplateContextBuilder setWopiAppLogWarningThresholdInMs(Long logWarningThresholdInMs);

	WopiTemplateContextBuilder setWopiAppLogErrorThresholdInMs(Long logErrorThresholdInMs);

	// WOPI SESSION STANDARD VALUES
	WopiTemplateContextBuilder setMaxVersions(Integer maxVersions);

	WopiTemplateContextBuilder setTenant(String tenant);

	WopiTemplateContextBuilder setWopiLockExpirationInMs(Long wopiLockExpirationInMs);

	// UI CUSTOMIZATION
	WopiTemplateContextBuilder setShowUserFriendlyName(Boolean showUserFriendlyName);

	WopiTemplateContextBuilder setShowBreadcrumbBrandName(Boolean showBreadcrumbBrandName);

	WopiTemplateContextBuilder setShowBreadCrumbDocName(Boolean showBreadcrumbDocName);

	WopiTemplateContextBuilder setShowBreadcrumbFolderName(Boolean showBreadcrumbFolderName);

	WopiTemplateContextBuilder setDisablePrint(Boolean disablePrint);

	WopiTemplateContextBuilder setDisableTranslation(Boolean disableTranslation);

	// -----------------------------------------------------------------------
	// CONTEXT METHODS
	// -----------------------------------------------------------------------

	WopiTemplateContextBuilder setEntityFactory(Function<EntityType<?>, GenericEntity> entityFactory);

	WopiTemplateContextBuilder setLookupFunction(Function<String, ? extends GenericEntity> lookupFunction);

	WopiTemplateContextBuilder setLookupExternalIdFunction(Function<String, ? extends HasExternalId> lookupExternalIdFunction);
}