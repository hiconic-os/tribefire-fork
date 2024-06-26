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
package tribefire.extension.simple.deployables.service;

import com.braintribe.cfg.Required;
import com.braintribe.common.lcd.ConfigurationException;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessor;
import com.braintribe.utils.CommonTools;
import com.braintribe.utils.genericmodel.GMCoreTools;

import tribefire.extension.simple.model.service.SimpleEchoRequest;
import tribefire.extension.simple.model.service.SimpleEchoResponse;

/**
 * This is a simple, example service implementation which processes {@link SimpleEchoRequest}s.
 *
 * @author michael.lafite
 */
public class SimpleEchoServiceProcessor implements AccessRequestProcessor<SimpleEchoRequest, SimpleEchoResponse> {

	/** The <code>Logger</code> used by this class. */
	private static Logger logger = Logger.getLogger(SimpleEchoServiceProcessor.class);

	/** See {@link #setDelay(Long)}. */
	private long delay = 0l;

	/** See {@link #setEchoCount(Integer)}. */
	private int echoCount;

	/**
	 * Sets the delay (in milliseconds) before returning the response. This is an optional configuration setting.
	 */
	public void setDelay(Long delay) {
		if (delay != null && delay > 0) {
			this.delay = delay;
		} else {
			this.delay = 0;
		}
	}

	/**
	 * Sets the echo count, i.e. how many times to return the message. This configuration setting is {@link Required}.
	 */
	@Required
	public void setEchoCount(Integer echoCount) {
		if (echoCount == null) {
			throw new ConfigurationException("The echo count is mandatory and thus must not be null!");
		}
		if (echoCount < 0) {
			throw new ConfigurationException("Invalid echo count " + echoCount + "! (Negative numbers are not allowed.)");
		}
		this.echoCount = echoCount;
	}

	/**
	 * Processes the specified <code>request</code>. If a {@link #setDelay(Long) delay} has been configured, the
	 * processor firsts sleeps for the specified amount of milliseconds. Afterwards the {@link SimpleEchoResponse
	 * response} is created based on the passed {@link SimpleEchoRequest#getMessage() message} and the configured
	 * {@link #setEchoCount(Integer) echo count}.
	 *
	 * @throws IllegalArgumentException
	 *             if the {@link SimpleEchoRequest#getMessage() message} is <code>null</code>.
	 */
	@Override
	public SimpleEchoResponse process(AccessRequestContext<SimpleEchoRequest> requestContext) {
		SimpleEchoRequest request = requestContext.getOriginalRequest();

		// log detailed info on trace level
		// (instead of checking, if logger.isTraceEnabled, we "guard" using lambda expression)
		logger.trace(() -> "Processing request " + GMCoreTools.getDescription(request));

		if (request.getMessage() == null) {
			throw new IllegalArgumentException("No message specified in request " + request + "!");
		}

		// if there is a delay configured, sleep now
		CommonTools.sleep(delay);

		// create response instance
		SimpleEchoResponse response = SimpleEchoResponse.T.create();

		// build echo message
		if (echoCount > 0) {
			StringBuilder echoBuilder = new StringBuilder();
			for (int i = 1; i <= echoCount; i++) {
				echoBuilder.append("[ECHO] " + request.getMessage());
			}
			response.setEcho(echoBuilder.toString());
		} else {
			// no echo --> leave echo property null
		}

		// we're done -> just return the response
		return response;
	}
}
