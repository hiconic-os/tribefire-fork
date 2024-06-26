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

import java.util.Date;
import java.util.function.Supplier;

import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.cache.model.deployment.service.cache2k.Mode;

public class CacheValueHolder {

	private final Object result; // TODO: rename to value?
	private final Date date;
	private final ServiceRequest request;

	// -----------------------------------------------------------------------
	// CONSTRUCTOR AND FACTORS
	// -----------------------------------------------------------------------

	public static CacheValueHolder create(Object result, Mode mode, Supplier<Object> resultProvider, ServiceRequest request) {
		if (mode == Mode.PRODUCTION) {
			return new CacheValueHolder(result);
		} else if (mode == Mode.DEBUG) {
			return new CacheValueHolder(resultProvider.get(), request);
		} else {
			throw new IllegalArgumentException("'mode': '" + mode + "' not supported");
		}
	}

	protected CacheValueHolder(Object result) {
		this.result = result;
		this.date = null;
		this.request = null;
	}

	protected CacheValueHolder(Object result, ServiceRequest request) {
		this.result = result;
		this.date = new Date();
		this.request = request;
	}

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	public boolean updateResult(@SuppressWarnings("unused") long refreshTime) {
		return false;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	public Object getResult() {
		return result;
	}

	public Date getDate() {
		return date;
	}

	public ServiceRequest getRequest() {
		return request;
	}

}
