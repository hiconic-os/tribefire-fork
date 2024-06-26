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
package com.braintribe.model.processing.service.api;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.service.api.DispatchableRequest;
import com.braintribe.model.service.api.ServiceRequest;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(namespace = ServiceApiConstants.NAMESPACE_SERVICE)
public interface ProcessorRegistry {
	<I extends ServiceRequest> void bind(EntityType<I> type, ServiceProcessor<? super I, ?> processor);
	@JsMethod(name = "bindByServiceId")
	<I extends DispatchableRequest> void bind(EntityType<I> type, String serviceId, ServiceProcessor<? super I, ?> processor);
	<I extends DispatchableRequest> void unbind(EntityType<I> type, String serviceId, ServiceProcessor<? super I, ?> processor);
}
