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
package tribefire.extension.metrics.service;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.LifecycleAware;
import com.braintribe.cfg.Required;
import com.braintribe.exception.Exceptions;
import com.braintribe.logging.Logger;
import com.braintribe.model.logging.LogLevel;
import com.braintribe.model.processing.service.api.ServiceProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.utils.logging.LogLevels;

import tribefire.extension.metrics.model.service.test.MetricsDemoService;
import tribefire.extension.metrics.model.service.test.MetricsDemoServiceResult;

public class MetricsDemoProcessor implements ServiceProcessor<MetricsDemoService, MetricsDemoServiceResult>, LifecycleAware {

	private static final Logger logger = Logger.getLogger(MetricsDemoProcessor.class);

	private LogLevel logLevel;

	// -----------------------------------------------------------------------
	// LifecycleAware
	// -----------------------------------------------------------------------

	@Override
	public void postConstruct() {
		// TODO Auto-generated method stub

	}

	@Override
	public void preDestroy() {
		// TODO Auto-generated method stub

	}

	// -----------------------------------------------------------------------
	// Service
	// -----------------------------------------------------------------------

	@Override
	public MetricsDemoServiceResult process(ServiceRequestContext requestContext, MetricsDemoService request) {
		logger.log(LogLevels.convert(logLevel),
				() -> "Executing '" + this.getClass().getSimpleName() + "' - '" + request.type().getTypeName() + "'...");

		try {
			long minDuration = request.getMinDuration();
			long maxDuration = request.getMaxDuration();

			long duration = minDuration + (long) (Math.random() * (maxDuration - minDuration));

			Thread.sleep(duration);
		} catch (InterruptedException e) {
			throw Exceptions.unchecked(e);
		}

		LogLevel logLevel = request.getLogLevel();
		String message = request.getMessage();

		if (message != null) {
			logger.log(LogLevels.convert(logLevel), message);
		}

		if (request.getThrowException()) {
			throw new IllegalStateException("THIS IS BY INTENTION! Reached demo service for request: '" + request + "' to throw an exception!");
		}

		MetricsDemoServiceResult result = MetricsDemoServiceResult.T.create();
		logger.log(LogLevels.convert(logLevel),
				() -> "Finished executing '" + this.getClass().getSimpleName() + "' - '" + request.type().getTypeName() + "'");

		return result;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	@Required
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

}
