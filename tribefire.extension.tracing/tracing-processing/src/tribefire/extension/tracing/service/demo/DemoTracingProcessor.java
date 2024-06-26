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
package tribefire.extension.tracing.service.demo;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.exception.Exceptions;
import com.braintribe.logging.Logger;
import com.braintribe.model.logging.LogLevel;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.service.impl.AbstractDispatchingServiceProcessor;
import com.braintribe.model.processing.service.impl.DispatchConfiguration;
import com.braintribe.processing.async.api.AsyncCallback;
import com.braintribe.utils.logging.LogLevels;

import tribefire.extension.tracing.model.service.demo.DemoTracing;
import tribefire.extension.tracing.model.service.demo.DemoTracingRequest;
import tribefire.extension.tracing.model.service.demo.DemoTracingResponse;
import tribefire.extension.tracing.model.service.demo.DemoTracingResult;

/**
 * Processor to demo tracing capabilities
 * 
 *
 */
public class DemoTracingProcessor extends AbstractDispatchingServiceProcessor<DemoTracingRequest, DemoTracingResponse> {

	private static final Logger logger = Logger.getLogger(DemoTracingProcessor.class);

	private LogLevel logLevel;

	// -----------------------------------------------------------------------
	// DISPATCHING
	// -----------------------------------------------------------------------

	@Override
	protected void configureDispatching(DispatchConfiguration<DemoTracingRequest, DemoTracingResponse> dispatching) {
		dispatching.register(DemoTracing.T, this::demoTracing);
	}

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	public DemoTracingResult demoTracing(ServiceRequestContext requestContext, DemoTracing request) {
		logger.log(LogLevels.convert(logLevel),
				() -> "Executing '" + this.getClass().getSimpleName() + "' - '" + request.type().getTypeName() + "'...");

		waiting(request.getBeforeDuration());
		handleRequest(request);

		boolean waitToFinish = request.getWaitToFinish();
		boolean executeParallel = request.getExecuteParallel();
		List<DemoTracing> nestedTracings = request.getNestedTracings();
		CountDownLatch countDownLatch = new CountDownLatch(nestedTracings.size());
		for (DemoTracing nestedTracing : nestedTracings) {
			if (executeParallel) {
				nestedTracing.eval(requestContext).get(new AsyncCallback<DemoTracingResponse>() {

					@Override
					public void onSuccess(DemoTracingResponse future) {
						countDownLatch.countDown();
						logger.info("Successfully finished demo execution with response: '" + future + "'");
					}

					@Override
					public void onFailure(Throwable t) {
						countDownLatch.countDown();
						logger.info("Error while executing demo: '" + t + "'");
					}
				});
			} else {
				nestedTracing.eval(requestContext).get();
			}
		}

		waiting(request.getAfterDuration());

		if (waitToFinish) {
			try {
				countDownLatch.await(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		DemoTracingResult result = DemoTracingResult.T.create();
		logger.log(LogLevels.convert(logLevel),
				() -> "Finished executing '" + this.getClass().getSimpleName() + "' - '" + request.type().getTypeName() + "'");
		return result;
	}

	// -----------------------------------------------------------------------
	// HELPERS
	// -----------------------------------------------------------------------

	private void handleRequest(DemoTracingRequest request) {

		LogLevel logLevel = request.getLogLevel();
		String message = request.getMessage();

		if (message != null) {
			logger.log(LogLevels.convert(logLevel), message);
		}

		if (request.getThrowException()) {
			throw new IllegalStateException("THIS IS BY INTENTION! Reached demo service for request: '" + request + "' to throw an exception!");
		}
	}

	private void waiting(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			throw Exceptions.unchecked(e);
		}
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
