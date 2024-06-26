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
package com.braintribe.gwt.gmrpc.api.client.transport;

import com.braintribe.gwt.gmrpc.api.client.exception.GmRpcException;
import com.braintribe.gwt.gmrpc.api.client.user.EmbeddedRequiredTypesExpert;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.service.api.result.ServiceResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GmRpcRequestSender {
	public ServiceResult sendRequest(ServiceRequest request, EmbeddedRequiredTypesExpert embeddedRequiredTypesExpert, boolean reasoned) throws GmRpcException;
	public void sendRequest(ServiceRequest request, EmbeddedRequiredTypesExpert embeddedRequiredTypesExpert, AsyncCallback<ServiceResult> asyncCallback, boolean reasoned);
}
