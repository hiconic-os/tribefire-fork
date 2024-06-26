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
package tribefire.extension.cache.service;

import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.cfg.LifecycleAware;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.cache.model.status.CacheAspectStatus;

public interface CacheAspectInterface<T extends CacheAspectStatus> extends LifecycleAware {

	T retriveCacheStatus();

	Object retrieveCacheResult(Supplier<Object> resultProvider, ServiceRequest request, String hash);

	void clearCache();

	boolean containsEntry(String hash);

	void removeEntry(String hash);

	CacheValueHolder getEntry(String hash);

	Map<String, CacheValueHolder> getAllEntries();

}
