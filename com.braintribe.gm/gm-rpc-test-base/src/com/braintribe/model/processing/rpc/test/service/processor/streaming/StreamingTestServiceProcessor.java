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
package com.braintribe.model.processing.rpc.test.service.processor.streaming;

import java.util.Date;

import com.braintribe.model.processing.rpc.test.commons.StreamingTools;
import com.braintribe.model.processing.rpc.test.commons.StreamingTools.RandomData;
import com.braintribe.model.processing.service.api.ServiceProcessor;
import com.braintribe.model.processing.service.api.ServiceProcessorException;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.resource.Resource;

public class StreamingTestServiceProcessor implements ServiceProcessor<StreamingTestServiceProcessorRequest, StreamingTestServiceProcessorResponse> {

	@Override
	public StreamingTestServiceProcessorResponse process(ServiceRequestContext requestContext, StreamingTestServiceProcessorRequest request)
			throws ServiceProcessorException {

		StreamingTestServiceProcessorResponse response = StreamingTestServiceProcessorResponse.T.create();

		Resource resource1 = request.getResource1();
		Resource resource2 = request.getResource2();

		try {

			StreamingTools.checkResource(resource1);
			StreamingTools.checkResource(resource2);

			response.setResource1(StreamingTools.createResource());
			response.setResource2(StreamingTools.createResource());

			response.setResponseDate(new Date());
			
			RandomData capture1Data = StreamingTools.createRandomData();
			RandomData capture2Data = StreamingTools.createRandomData();
			
			response.setCapture1Md5(capture1Data.md5);
			response.setCapture2Md5(capture2Data.md5);

			if (request.getRespondEagerly()) {
				response.setEager(true);
				requestContext.notifyResponse(response);
			}
				
			StreamingTools.serveCapture(capture1Data, request.getCapture1());
			StreamingTools.serveCapture(capture2Data, request.getCapture2());

		} catch (Exception e) {
			throw new ServiceProcessorException("Failed to process streaming request: " + e.getMessage(), e);
		}

		if (request.getRespondEagerly()) {
			return null;
		}

		return response;

	}

}
