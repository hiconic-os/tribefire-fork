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
package com.braintribe.model.processing.service.weaving.impl.dispatch;

import static com.braintribe.model.generic.GMF.getTypeReflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.braintribe.exception.Exceptions;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.service.api.ServiceProcessorException;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.service.api.UnsupportedRequestTypeException;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * DispatchMap is the single point of access to acquire {@link MethodHandle} based dispatch handlers that bind to specific handler methods.
 * @author dirk.scheffler
 */
public class DispatchMap {
	private static final Map<Class<?>, DispatchMap> dispatchMaps = new ConcurrentHashMap<>();
	
	private Map<EntityType<? extends ServiceRequest>, ServiceRequestHandler> handlers = new IdentityHashMap<>();
	private final Class<?> serviceInterface;
	
	private DispatchMap(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
		try {
			for (Method method : serviceInterface.getMethods()) {
				Class<?>[] parameterTypes = method.getParameterTypes();

				if (parameterTypes.length == 2) {
					Class<?> contextParameterCandidate = parameterTypes[0];
					Class<?> requestParameterCandidate = parameterTypes[1];

					boolean contextParameterMatch = ServiceRequestContext.class.isAssignableFrom(contextParameterCandidate);
					boolean requestParameterMatch = ServiceRequest.class.isAssignableFrom(requestParameterCandidate);

					if (requestParameterCandidate == ServiceRequest.class && (method.isBridge() || method.getName().equals("process")))
						continue;

					if (contextParameterMatch && requestParameterMatch) {
						Class<? extends ServiceRequest> requestClass = (Class<? extends ServiceRequest>) requestParameterCandidate;
						EntityType<? extends ServiceRequest> requestType = getTypeReflection().getEntityType(requestClass);

						ServiceRequestHandler handler = buildHandler(method);

						handlers.put(requestType, handler);
					}
				}
			}
		} catch (Exception e) {
			throw new DispatchWeavingException("error while weaving dispatch handlers", e);
		}
	}
	
	public ServiceRequestHandler acquireHandler(EntityType<?> type) throws ServiceProcessorException {
		ServiceRequestHandler handler = handlers.get(type);
	
		if (handler != null)
			return handler;
		
		synchronized (handlers) {
			// check because another thread could have done that already while we were waiting to proceed
			handler = handlers.get(type);
			if (handler != null)
				return handler;
			
			Map<EntityType<? extends ServiceRequest>, ServiceRequestHandler> map = new IdentityHashMap<>(handlers);
			
			handler = acquireHandler(type, map);
			
			if (handler == null)
				throw new UnsupportedRequestTypeException("no suitable handler method found for request type " + type.getTypeSignature() + " in handler interface " + serviceInterface.getName());

			this.handlers = map;
		}
		
		return handler;
	}
		
	private static ServiceRequestHandler acquireHandler(EntityType<?> type, Map<EntityType<? extends ServiceRequest>, ServiceRequestHandler> handlers) {
		if (type == ServiceRequest.T)
			return null;
		
		for (EntityType<?> superType: type.getSuperTypes()) {
			ServiceRequestHandler handler = handlers.get(superType);
		
			if (handler == null) 
				handler = acquireHandler(superType, handlers);
			
			if (handler != null) {
				EntityType<? extends ServiceRequest> requestType = (EntityType<? extends ServiceRequest>)type;
				handlers.put(requestType, handler);
				return handler;
			}
		}
		
		return null;
	}
	
	public static DispatchMap mapFor(Class<?> serviceProcessorClass) {
		DispatchMap dispatchMap = dispatchMaps.computeIfAbsent(serviceProcessorClass, DispatchMap::new);
		return dispatchMap;
	}
	
	/**
	 * Actual ASM coder method
	 */
	public static ServiceRequestHandler buildHandler(Method method) throws Exception {
		
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		
		MethodHandle methodHandle = lookup.unreflect(method); // (ServiceRequestContext context, MyRequest request); 
		
		return (Object processor, ServiceRequestContext requestContext, ServiceRequest request) -> {
			try {
				return methodHandle.invoke(processor, requestContext, request);
			} 
			catch (RuntimeException | Error e) {
				throw e;
			}
			catch (Throwable e) {
				throw Exceptions.unchecked(e, "Exception while invoking ServiceRequest processing method: " + method);
			}
		};
	
	}

}
