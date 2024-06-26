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
package com.braintribe.model.processing.vde.impl.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.braintribe.gm.model.svd.EvaluateRequest;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.service.common.ConfigurableDispatchingServiceProcessor;
import com.braintribe.model.processing.service.impl.ConfigurableServiceRequestEvaluator;
import com.braintribe.model.processing.vde.evaluator.api.aspects.RequestEvaluatorAspect;
import com.braintribe.model.processing.vde.impl.service.model.VdeTestRequest;
import com.braintribe.model.processing.vde.test.VdeTest;

/**
 * @author peter.gazdik
 */
public class EvaluateRequestVdeTest extends VdeTest {

	private static ConfigurableDispatchingServiceProcessor dispatcher = new ConfigurableDispatchingServiceProcessor();
	private static ConfigurableServiceRequestEvaluator evaluator = new ConfigurableServiceRequestEvaluator();

	static {
		dispatcher.register(VdeTestRequest.T, EvaluateRequestVdeTest::eval);

		evaluator.setServiceProcessor(dispatcher);
	}

	private static String eval(@SuppressWarnings("unused") ServiceRequestContext requestContext, VdeTestRequest request) {
		return request.getParameter().toUpperCase();
	}

	@Test
	public void testEval() throws Exception {
		VdeTestRequest request = VdeTestRequest.T.create();
		request.setParameter("hello");

		EvaluateRequest evalRequest = EvaluateRequest.T.create();
		evalRequest.setRequest(request);

		Object result = evaluateWith(RequestEvaluatorAspect.class, evaluator, evalRequest);
		assertThat(result).isEqualTo("HELLO");
	}

}
