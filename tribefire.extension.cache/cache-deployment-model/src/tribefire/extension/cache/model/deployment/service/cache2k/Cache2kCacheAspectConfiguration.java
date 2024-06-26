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
package tribefire.extension.cache.model.deployment.service.cache2k;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.logging.LogLevel;

import tribefire.extension.cache.model.deployment.service.InMemoryCacheAspectConfiguration;

public interface Cache2kCacheAspectConfiguration extends InMemoryCacheAspectConfiguration {

	final EntityType<? extends Cache2kCacheAspectConfiguration> T = EntityTypes.T(Cache2kCacheAspectConfiguration.class);

	String maxCacheEntries = "maxCacheEntries";
	String enableStatistics = "enableStatistics";
	String expiration = "expiration";
	String permitNullValues = "permitNullValues";
	String highPerformanceMode = "highPerformanceMode";
	String createEntryLogging = "createEntryLogging";
	String expireEntryLogging = "expireEntryLogging";
	String removeEntryLogging = "removeEntryLogging";
	String updateEntryLogging = "updateEntryLogging";
	String mode = "mode";
	String refreshAheadConfiguration = "refreshAheadConfiguration";

	@Initializer("100000l")
	@Mandatory
	long getMaxCacheEntries();
	void setMaxCacheEntries(long maxCacheEntries);

	@Initializer("true")
	@Mandatory
	boolean getEnableStatistics();
	void setEnableStatistics(boolean enableStatistics);

	@Mandatory
	Expiration getExpiration();
	void setExpiration(Expiration expiration);

	@Initializer("true")
	@Mandatory
	boolean getPermitNullValues();
	void setPermitNullValues(boolean permitNullValues);

	@Initializer("true")
	@Mandatory
	boolean getHighPerformanceMode();
	void setHighPerformanceMode(boolean highPerformanceMode);

	EntryLogging getCreateEntryLogging();
	void setCreateEntryLogging(EntryLogging createEntryLogging);

	LogLevel getExpireEntryLogging();
	void setExpireEntryLogging(LogLevel expireEntryLogging);

	EntryLogging getRemoveEntryLogging();
	void setRemoveEntryLogging(EntryLogging removeEntryLogging);

	EntryLogging getUpdateEntryLogging();
	void setUpdateEntryLogging(EntryLogging updateEntryLogging);

	@Mandatory
	@Initializer("enum(tribefire.extension.cache.model.deployment.service.cache2k.Mode,PRODUCTION)")
	Mode getMode();
	void setMode(Mode mode);

	RefreshAheadConfiguration getRefreshAheadConfiguration();
	void setRefreshAheadConfiguration(RefreshAheadConfiguration refreshAheadConfiguration);

}
