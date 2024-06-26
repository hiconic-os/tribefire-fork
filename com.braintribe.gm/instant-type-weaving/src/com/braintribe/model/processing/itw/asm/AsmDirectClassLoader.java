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
package com.braintribe.model.processing.itw.asm;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Map;

/**
 * Registering new {@link AsmLoadableClass loadable classes} is not thread-safe!
 */
public class AsmDirectClassLoader extends ClassLoader implements AsmClassLoading {

	private final Map<String, AsmLoadableClass> asmClasses = newMap();

	public AsmDirectClassLoader() {
		super(AsmDirectClassLoader.class.getClassLoader());
	}

	@Override
	public ClassLoader getItwClassLoader() {
		return this;
	}
	
	@Override
	public void register(AsmLoadableClass asmClass) {
		asmClasses.put(asmClass.getName(), asmClass);
	}

	@Override
	public <T> Class<T> getJvmClass(String name) throws ClassNotFoundException {
		return (Class<T>) loadClass(name);
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		if (asmClasses.containsKey(name))
			return loadClass(asmClasses.get(name));
		else
			return super.loadClass(name, resolve);
	}

	private Class<?> loadClass(AsmLoadableClass asmClass) {
		byte[] bytes = asmClass.bytes;
		Class<?> result = defineClass(asmClass.getName(), bytes, 0, bytes.length);
		notifyClassLoaded(asmClass, result);
		return result;
	}

	private void notifyClassLoaded(AsmLoadableClass asmClass, Class<?> result) {
		asmClass.notifyLoaded(result);

		asmClasses.remove(asmClass.getName());
	}

}
