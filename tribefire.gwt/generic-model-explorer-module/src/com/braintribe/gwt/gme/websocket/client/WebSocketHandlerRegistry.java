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
package com.braintribe.gwt.gme.websocket.client;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.service.api.ServiceRequest;

public class WebSocketHandlerRegistry implements Function<EntityType<? extends ServiceRequest>, WebSocketHandler<?>> {
	
	private Map<EntityType<? extends ServiceRequest>, Supplier<? extends WebSocketHandler<?>>> webSocketHandlers;
	
	@Required
	public void setWebSocketHandlers(Map<EntityType<? extends ServiceRequest>, Supplier<? extends WebSocketHandler<?>>> webSocketHandlers) {
		this.webSocketHandlers = webSocketHandlers;
	}

	@Override
	public WebSocketHandler<?> apply(EntityType<? extends ServiceRequest> type) {
		Supplier<? extends WebSocketHandler<?>> supplier = webSocketHandlers.get(type);
		return supplier == null ? null : supplier.get();
	}

}
