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
package com.braintribe.utils;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * A class loader which tries to load classes from the child {@link URLClassLoader} first and only then from the parent.
 * See {@link #ChildFirstClassLoader(URL[], ClassLoader)}.
 */
public class ChildFirstClassLoader extends ClassLoader {

	/**
	 * The child class loader. It's needed because method <code>findClass</code> is not public in {@link URLClassLoader}
	 * .
	 */
	private final ChildURLClassLoader childUrlClassLoader;

	/**
	 * Classes are cached to avoid {@link LinkageError}.
	 */
	private final Map<String, Class<?>> cachedClasses = new HashMap<String, Class<?>>();

	/**
	 * Creates a new <code>ChildFirstClassLoader</code> instance.
	 *
	 * @param childUrlClassLoaderUrls
	 *            the urls for the child {@link URLClassLoader} to be created.
	 * @param parentClassLoader
	 *            the parent class loader.
	 */
	public ChildFirstClassLoader(final URL[] childUrlClassLoaderUrls, final ClassLoader parentClassLoader) {
		super(parentClassLoader);
		this.childUrlClassLoader = new ChildURLClassLoader(childUrlClassLoaderUrls, parentClassLoader);
	}

	@Override
	protected synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		Class<?> clazz = this.cachedClasses.get(name);
		if (clazz == null) {
			try {
				clazz = this.childUrlClassLoader.findClass(name);
			} catch (final ClassNotFoundException e) {
				clazz = super.loadClass(name, resolve);
			}
			this.cachedClasses.put(name, clazz);
		}
		return clazz;
	}

	/**
	 * A {@link URLClassLoader} which sets the parent to <code>null</code>. The only method which uses the parent is the
	 * overridden method {@link #findClass(String)}, which tries to load from child first and then falls back to parent.
	 * The visiblity is changed to <code>public</code> to be able to access it from other classes.
	 */
	private class ChildURLClassLoader extends URLClassLoader {
		private final ClassLoader realParent;

		public ChildURLClassLoader(final URL[] urls, final ClassLoader realParent) {
			super(urls, null);

			this.realParent = realParent;
		}

		@Override
		public Class<?> findClass(final String name) throws ClassNotFoundException {
			Class<?> clazz = ChildFirstClassLoader.this.cachedClasses.get(name);
			if (clazz == null) {
				try {
					// first try to use the URLClassLoader findClass
					clazz = super.findClass(name);
				} catch (final ClassNotFoundException e) {
					// if that fails, we ask our real parent classloader to load the class (we give up)
					clazz = this.realParent.loadClass(name);
				}
				ChildFirstClassLoader.this.cachedClasses.put(name, clazz);
			}
			return clazz;
		}
	}
}
