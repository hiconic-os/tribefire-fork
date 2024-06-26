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

import java.util.function.Supplier;

import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.cache.model.deployment.service.cache2k.Mode;

public class RefreshAheadCacheValueHolder extends CacheValueHolder {

	private final long executionTime;

	// -----------------------------------------------------------------------
	// CONSTRUCTOR AND FACTORS
	// -----------------------------------------------------------------------

	public static RefreshAheadCacheValueHolder create(Object result, Mode mode, Supplier<Object> resultProvider, ServiceRequest request) {
		if (mode == Mode.PRODUCTION) {
			return new RefreshAheadCacheValueHolder(result);
		} else if (mode == Mode.DEBUG) {
			return new RefreshAheadCacheValueHolder(resultProvider, request);
		} else {
			throw new IllegalArgumentException("'mode': '" + mode + "' not supported");
		}
	}

	protected RefreshAheadCacheValueHolder(Object result) {
		super(result);
		this.executionTime = System.currentTimeMillis();
	}

	protected RefreshAheadCacheValueHolder(Object result, ServiceRequest request) {
		super(result, request);
		this.executionTime = System.currentTimeMillis();
	}

	@Override
	public boolean updateResult(long refreshTime) {
		if (System.currentTimeMillis() - executionTime < refreshTime) {
			return true;
		}
		return false;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	public long getExecutionTime() {
		return executionTime;
	}
}
