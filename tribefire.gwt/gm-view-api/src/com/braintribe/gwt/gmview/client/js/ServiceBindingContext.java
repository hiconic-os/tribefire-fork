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
package com.braintribe.gwt.gmview.client.js;

import com.braintribe.gwt.gmview.client.WebSocketSupport;
import com.braintribe.gwt.gmview.client.js.interop.InteropConstants;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.processing.service.api.ProcessorRegistry;
import com.braintribe.model.service.api.ServiceRequest;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType (namespace = InteropConstants.MODULE_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public class ServiceBindingContext implements WebSocketSupport {
	
	private ProcessorRegistry processorRegistry;
	private Evaluator<ServiceRequest> localEvaluator;
	private Evaluator<ServiceRequest> remoteEvaluator;
	private String clientId;
	private String sessionId;
	private WebSocketSupport webSocketImpl;
	
	@JsConstructor
	public ServiceBindingContext() {
	}
	
	/**
	 * Configures the required implementation for the {@link WebSocketSupport}.
	 */
	@JsIgnore
	@Required
	public void setWebSocketImpl(WebSocketSupport webSocketImpl) {
		this.webSocketImpl = webSocketImpl;
	}
	
	@JsProperty
	public void setProcessorRegistry(ProcessorRegistry processorRegistry) {
		this.processorRegistry = processorRegistry;
	}
	
	@JsProperty
	public ProcessorRegistry getProcessorRegistry() {
		return processorRegistry;
	}
	
	@JsProperty
	public void setLocalEvaluator(Evaluator<ServiceRequest> localEvaluator) {
		this.localEvaluator = localEvaluator;
	}
	
	@JsProperty
	public Evaluator<ServiceRequest> getLocalEvaluator() {
		return localEvaluator;
	}
	
	@JsProperty
	public void setRemoteEvaluator(Evaluator<ServiceRequest> remoteEvaluator) {
		this.remoteEvaluator = remoteEvaluator;
	}
	
	@JsProperty
	public Evaluator<ServiceRequest> getRemoteEvaluator() {
		return remoteEvaluator;
	}
	
	@JsProperty
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@JsProperty
	public String getSessionId() {
		return sessionId;
	}
	
	@JsProperty
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@JsProperty
	public String getClientId() {
		return clientId;
	}
	
	@Override
	@JsMethod
	public void openNotificationChannel(String clientId) {
		webSocketImpl.openNotificationChannel(clientId);
	}
	
	@Override
	@JsMethod
	public void closeNotificationChannel(String clientId) {
		webSocketImpl.closeNotificationChannel(clientId);
	}

}
