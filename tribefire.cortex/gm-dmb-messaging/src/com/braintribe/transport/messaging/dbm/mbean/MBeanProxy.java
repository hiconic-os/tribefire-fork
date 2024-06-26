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
package com.braintribe.transport.messaging.dbm.mbean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

/**
 * <p>
 * Generic {@link InvocationHandler} for MBeans.
 * <p>
 * 
 * Unlike {@link javax.management.JMX#newMBeanProxy(MBeanServerConnection, ObjectName, Class)}, the target MBean
 * instance does not have to implement the interface expected for the resulting proxy.
 */
public class MBeanProxy implements InvocationHandler {

	private ObjectName objectName;
	private MBeanServerConnection mbeanServer;

	private MBeanProxy(ObjectName objectName, MBeanServerConnection mbeanServer) {
		this.objectName = objectName;
		this.mbeanServer = mbeanServer;
	}

	public static <T> T create(Class<T> interfaceClass, ObjectName objectName, MBeanServerConnection mbeanServer) {
		return create(interfaceClass, objectName, mbeanServer, MBeanProxy.class.getClassLoader());
	}

	public static <T> T create(Class<T> interfaceClass, ObjectName objectName, MBeanServerConnection mbeanServer, ClassLoader cl) {
		MBeanProxy invocationHandler = new MBeanProxy(objectName, mbeanServer);
		@SuppressWarnings("unchecked")
		T service = (T) Proxy.newProxyInstance(cl, new Class<?>[] { interfaceClass }, invocationHandler);
		return service;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// shortcut delegation for object methods with local method of the proxy
		if (method.getDeclaringClass() == Object.class) {
			return method.invoke(this, args);
		}

		return mbeanServer.invoke(objectName, method.getName(), args, buildSignature(method));
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
