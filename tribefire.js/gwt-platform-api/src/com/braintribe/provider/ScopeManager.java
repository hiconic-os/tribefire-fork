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
package com.braintribe.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ScopeManager<T> implements BeanLifeCycleExpert {
	
	private Stack<T> scopeStack = new Stack<T>();
	private List<ScopeListener<T>> listeners = new ArrayList<ScopeListener<T>>();
	private BeanLifeCycleExpert beanLifeCycleExpert = null;
	
	public void setBeanLifeCycleExpert(
			BeanLifeCycleExpert beanLifeCycleExpert) {
		this.beanLifeCycleExpert = beanLifeCycleExpert;
	}
	
	public void pushScope(T instance) {
		scopeStack.push(instance);
	}
	
	public void popScope() {
		scopeStack.pop();
	}
	
	public T getCurrentScope() {
		return scopeStack.peek();
	}
	
	public void openScope(T scope) {
		try {
			pushScope(scope);
			fireScopeOpened(scope);
		}
		finally {
			popScope();
		}
	}
	
	public void closeScope(T scope) {
		try {
			pushScope(scope);
			fireScopeClosed(scope);
		}
		finally {
			popScope();
		}
	}
	
	public void openAndPushScope(T scope) {
		pushScope(scope);
		fireScopeOpened(scope);
	}
	
	public void closeAndPopScope() {
		T scope = getCurrentScope();
		try {
			fireScopeClosed(scope);
		}
		finally {
			popScope();
		}
	}
	
	public void fireScopeOpened(T scope) {
		for (ScopeListener<T> listener: listeners) {
			listener.scopeOpened(scope);
		}
	}
	
	public void fireScopeClosed(T scope) {
		for (ScopeListener<T> listener: listeners) {
			listener.scopeClosed(scope);
		}
	}
	
	public void addScopeListener(ScopeListener<T> listener) {
		listeners.add(listener);
	}
	
	public void removeScopeListener(ScopeListener<T> listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void intializeBean(Object object) throws Exception {
		if (beanLifeCycleExpert != null) beanLifeCycleExpert.intializeBean(object);
	}
	
	@Override
	public void disposeBean(Object object) throws Exception {
		if (beanLifeCycleExpert != null) beanLifeCycleExpert.disposeBean(object);
	}
}
