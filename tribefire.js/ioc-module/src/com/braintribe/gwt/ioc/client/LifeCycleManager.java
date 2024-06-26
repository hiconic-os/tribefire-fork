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
package com.braintribe.gwt.ioc.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.braintribe.provider.BeanLifeCycleExpert;

/**
 * The LifeCycleManager holds a set of objects that are stored to be released
 * if some scope calls {@link #disposeAllBeans()}
 * 
 * @author Dirk
 *
 */
public class LifeCycleManager {
	public static BeanLifeCycleExpert beanLifeCycleExpert = new IocBeanLifeCycleExpert();
	
	private Set<Object> objects;
	
	/**
	 * Intializes a given object with the {@link #beanLifeCycleExpert} and stores
	 * it for later disposing
	 * @return the intialized object
	 */
	public <T> T initBean(T object) {
		if (objects == null) objects = new HashSet<Object>();
		try {
			beanLifeCycleExpert.intializeBean(object);
			objects.add(object);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error while intializing a bean", e);
		}
	}
	
	/**
	 * Disposes the given object with the {@link #beanLifeCycleExpert} if the object
	 * is known to this LifeCycleManager and removes it from known objects.
	 * @return true if the object was known and could be disposed otherwise false
	 */
	public boolean disposeBean(Object object) {
		if (objects != null && objects.contains(object)) {
			try {
				beanLifeCycleExpert.disposeBean(object);
				objects.remove(object);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("error while disposing a bean", e);
			}
		}
		else return false;
	}
	
	/**
	 * disposes and removes all known beans
	 */
	public void disposeAllBeans() {
		if (objects != null) {
			try {
				Iterator<Object> it = objects.iterator();
				while (it.hasNext()) {
					Object object = it.next();
					beanLifeCycleExpert.disposeBean(object);
					it.remove();
				}
				objects.clear();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("error while disposing a bean", e);
			}
		}
	}
}
