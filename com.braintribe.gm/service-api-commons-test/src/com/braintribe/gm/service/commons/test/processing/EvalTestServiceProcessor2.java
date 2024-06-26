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
package com.braintribe.gm.service.commons.test.processing;

import com.braintribe.gm.service.commons.test.model.EvalTestServiceRequest2;
import com.braintribe.model.processing.service.api.ServiceProcessor;
import com.braintribe.model.processing.service.api.ServiceProcessorException;
import com.braintribe.model.processing.service.api.ServiceRequestContext;

public class EvalTestServiceProcessor2 extends AuthorizedServiceProcessorBase implements ServiceProcessor<EvalTestServiceRequest2, Long> {

	@Override
	public Long process(ServiceRequestContext context, EvalTestServiceRequest2 parameter) throws ServiceProcessorException {
		preProcess(context, parameter);
		return Long.MAX_VALUE;
	}

}