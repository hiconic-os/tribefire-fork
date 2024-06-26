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

import com.braintribe.exception.Exceptions;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.service.api.ServiceProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;

import tribefire.extension.cache.model.service.demo.CacheDemo;
import tribefire.extension.cache.model.service.demo.CacheDemoResult;

public class CacheDemoProcessor implements ServiceProcessor<CacheDemo, CacheDemoResult> {

	private static final Logger logger = Logger.getLogger(CacheDemoProcessor.class);

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	@Override
	public CacheDemoResult process(ServiceRequestContext requestContext, CacheDemo request) {
		logger.info(() -> "Executing '" + this.getClass().getSimpleName() + "' - " + request.type().getTypeName());

		CacheDemoResult result = CacheDemoResult.T.create();

		long durationInMs = request.getDurationInMs();
		boolean throwException = request.getThrowException();

		try {
			Thread.sleep(durationInMs);
		} catch (InterruptedException e) {
			throw Exceptions.unchecked(e, "Interrupted: '" + CacheDemoProcessor.class.getSimpleName() + "'");
		}

		if (throwException) {
			throw new RuntimeException("'" + CacheDemoProcessor.class.getSimpleName() + "' dummy exception");
		}

		String resultValue = request.getResultValue();
		if (resultValue != null) {
			result.setResultValue(resultValue);
		}

		return result;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

}
