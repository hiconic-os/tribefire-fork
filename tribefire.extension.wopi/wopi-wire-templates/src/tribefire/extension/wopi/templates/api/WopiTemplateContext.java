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

import com.braintribe.cfg.ScopeContext;
import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.deployment.Module;
import com.braintribe.model.descriptive.HasExternalId;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.resource.configuration.ExternalResourcesContext;
import com.braintribe.wire.api.scope.InstanceConfiguration;

import tribefire.extension.wopi.processing.StorageType;

/**
 * WOPI Template Context - contains information for configuring WOPI functionality
 * 
 *
 */
public interface WopiTemplateContext extends ScopeContext {

	// -----------------------------------------------------------------------
	// Common
	// -----------------------------------------------------------------------

	/**
	 * The WOPI {@link Module}
	 */
	Module getWopiModule();

	/**
	 * Unique context information to distinguish multiple WOPI deployments - based on this 'globalId, 'externalId',
	 * 'name' of Deployables gets calculated. In case {@link WopiTemplateContext#builder(IncrementalAccess)} has an
	 * {@link IncrementalAccess} attached 'globalId, 'externalId', 'name' of the access won't be reset of course.
	 * 
	 * This context should be written in 'camelCase'
	 */
	String getContext();

	/**
	 * Optional configuration for database usage - if not set smood access will be used; if
	 * {@link WopiTemplateContext#getStorageType()} is {@link StorageType#db} it must be set
	 */
	WopiTemplateDatabaseContext getDatabaseContext();

	/**
	 * Optional configuration for external resources usage - will be used if
	 * {@link WopiTemplateContext#getStorageType()} is {@link StorageType#external}
	 */
	ExternalResourcesContext getExternalResourcesContext();

	/**
	 * Storage type for Resources
	 */
	StorageType getStorageType();

	/**
	 * Builder
	 * 
	 * {@link IncrementalAccess} to be used for as WOPI access - all the configuration is used from there (data &
	 * Resource streaming)
	 */
	static WopiTemplateContextBuilder builder(IncrementalAccess access) {
		return new WopiTemplateContextImpl(access);
	}

	// -----------------------------------------------------------------------
	// WopiAccess
	// -----------------------------------------------------------------------

	/**
	 * An {@link IncrementalAccess} if set - otherwise null
	 */
	IncrementalAccess getAccess();

	/**
	 * Optional storage folder for storing in file system - if not set and {@link WopiTemplateContext#getStorageType()}
	 * is {@link StorageType#fs} default storage file system location will be used in case of file system usage
	 */
	String getStorageFolder();

	// -----------------------------------------------------------------------
	// WopiWacConnector
	// -----------------------------------------------------------------------

	String getWacDiscoveryEndpoint();

	String getCustomPublicServicesUrl();

	Integer getConnectionRequestTimeoutInMs();

	Integer getConnectTimeoutInMs();

	Integer getSocketTimeoutInMs();

	Integer getConnectionRetries();

	Integer getDelayOnRetryInMs();

	// -----------------------------------------------------------------------
	// WopiServiceProcessor
	// -----------------------------------------------------------------------

	Long getWopiServiceProcessorLogWarningThresholdInMs();

	Long getWopiServiceProcessorLogErrorThresholdInMs();

	// -----------------------------------------------------------------------
	// ExpireWopiSessionWorker
	// -----------------------------------------------------------------------

	Long getExpireWopiSessionIntervalInMs();

	// -----------------------------------------------------------------------
	// CleanupWopiSessionWorker
	// -----------------------------------------------------------------------

	Long getCleanupWopiSessionIntervalInMs();

	// -----------------------------------------------------------------------
	// WopiApp
	// -----------------------------------------------------------------------

	Long getLockExpirationInMs();

	Long getWopiSessionExpirationInMs();

	Long getWopiAppLogWarningThresholdInMs();

	Long getWopiAppLogErrorThresholdInMs();

	// WOPI SESSION STANDARD VALUES
	Integer getMaxVersions();

	String getTenant();

	Long getWopiLockExpirationInMs();

	// UI CUSTOMIZATION
	Boolean getShowUserFriendlyName();

	Boolean getShowBreadcrumbBrandName();

	Boolean getShowBreadCrumbDocName();

	Boolean getShowBreadcrumbFolderName();

	Boolean getDisablePrint();

	Boolean getDisableTranslation();

	// -----------------------------------------------------------------------
	// CONTEXT METHODS
	// -----------------------------------------------------------------------

	<T extends GenericEntity> T create(EntityType<T> entityType, InstanceConfiguration instanceConfiguration);

	<T extends GenericEntity> T lookup(String globalId);

	<T extends HasExternalId> T lookupExternalId(String externalId);

}