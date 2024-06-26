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
package tribefire.extension.tracing.integration.test.util;

import java.util.concurrent.TimeUnit;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import com.braintribe.logging.Logger;

public class CustomJUnitStopWatch extends Stopwatch {

	private final static Logger logger = Logger.getLogger(CustomJUnitStopWatch.class);

	private static void logInfo(Description description, String status, long nanos) {
		String testName = description.getMethodName();
		logger.info(String.format("Test '%s' '%s' took: '%d'ms", testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
	}

	@Override
	protected void succeeded(long nanos, Description description) {
		logInfo(description, "succeeded", nanos);
	}

	@Override
	protected void failed(long nanos, Throwable e, Description description) {
		logInfo(description, "failed", nanos);
	}

	@Override
	protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
		logInfo(description, "skipped", nanos);
	}
}
