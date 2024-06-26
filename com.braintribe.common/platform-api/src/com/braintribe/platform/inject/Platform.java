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
package com.braintribe.platform.inject;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public abstract class Platform {

	private static Properties platformProperties;

	static {

		initPlatformProperties();

	}

	public static void initPlatformProperties() {

		try {
			Enumeration<URL> resources = Platform.class.getClassLoader().getResources("jvmPlatformImplementations");

			platformProperties = new Properties();

			while (resources.hasMoreElements()) {

				URL url = resources.nextElement();

				Properties properties = new Properties();
				properties.load(new InputStreamReader(url.openStream(), "UTF-8"));

				platformProperties.putAll(properties);
			}

		} catch (Exception e) {
			throw new PlatformRuntimeException("Initialisation of platform failed", e);
		}
	}

	public static <T> T create(Class<T> clazz) {

		try {
			String className = platformProperties.getProperty(clazz.getName());
			Class<?> delegateClass;

			delegateClass = Class.forName(className);

			if (!clazz.isAssignableFrom(delegateClass)) {
				throw new PlatformRuntimeException(
						"Wrong Platform configuration. Class defined by '" + delegateClass + "' property must implement the '" + clazz + "'");
			}

			T newInstance = delegateClass.asSubclass(clazz).getDeclaredConstructor().newInstance();
			return newInstance;

		} catch (Exception e) {
			throw new PlatformRuntimeException("Class " + clazz + " could not be instantiated", e);
		}

	}

}
