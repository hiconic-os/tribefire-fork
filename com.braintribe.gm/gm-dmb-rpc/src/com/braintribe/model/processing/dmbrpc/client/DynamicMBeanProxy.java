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
package com.braintribe.model.processing.dmbrpc.client;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class DynamicMBeanProxy implements InvocationHandler {
	private ObjectName objectName;
	private MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
	
	public static <T> T create(Class<T> interfaceClass, String objectName) throws MalformedObjectNameException {
		DynamicMBeanProxy invocationHandler = new DynamicMBeanProxy(new ObjectName(objectName));
		@SuppressWarnings("unchecked")
		T service = (T)Proxy.newProxyInstance(DynamicMBeanProxy.class.getClassLoader(), new Class<?>[]{interfaceClass}, invocationHandler);
		return service;
	}
	
	public static <T> T create(Class<T> interfaceClass, ObjectName objectName) {
		DynamicMBeanProxy invocationHandler = new DynamicMBeanProxy(objectName);
		@SuppressWarnings("unchecked")
		T service = (T)Proxy.newProxyInstance(DynamicMBeanProxy.class.getClassLoader(), new Class<?>[]{interfaceClass}, invocationHandler);
		return service;
	}
	
	private DynamicMBeanProxy(ObjectName objectName) {
		this.objectName = objectName;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// shortcut delegation for object methods with local method of the proxy
		if (method.getDeclaringClass() == Object.class) {
			try {
				return method.invoke(this, args);
			} catch (UndeclaredThrowableException | InvocationTargetException e) {
				throw (e.getCause() != null) ? e.getCause() : e;
			}
		}

		try {
			return mbeanServer.invoke(objectName, method.getName(), args, buildSignature(method));
		} catch (MBeanException | ReflectionException e) {
			throw (e.getCause() != null) ? e.getCause() : e;
		}
	}

	private static String[] buildSignature(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		String signatures[] = new String[parameterTypes.length];
		
		for (int i = 0; i < parameterTypes.length; i++) {
			signatures[i] = parameterTypes[i].getName();
		}
		
		return signatures;
	}
}
