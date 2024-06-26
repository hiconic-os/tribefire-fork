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
package com.braintribe.spring.support.scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * 
 * @author dirk.scheffler
 *
 */
public class AbstractScope<S> implements Scope {
  private ThreadLocal<Stack<S>> tlScopeStack = new ThreadLocal<Stack<S>>();
  private Map<S, Map<String, ScopeObjectEntry>> scopes = new HashMap<S, Map<String,ScopeObjectEntry>>(); 
  
  private Stack<S> getScopeStack() {
    Stack<S> stack = tlScopeStack.get();
    if (stack == null) {
      stack = new Stack<S>();
      tlScopeStack.set(stack);
    }
    return stack;
  }
  
  public void pushScope(S scope) {
    getScopeStack().push(scope);
  }
  
  public void popScope() {
    getScopeStack().pop();
  }
  
  public S getCurrentScope() {
    Stack<S> stack = getScopeStack();
    if (stack.isEmpty()) return null;
    else return stack.peek();
  }
  
  public void endScope(S s) {
    Map<String, ScopeObjectEntry> instances = scopes.remove(s);
    if (instances != null) {
      for (ScopeObjectEntry entry: instances.values()) {
        Runnable destructionCallback = entry.getDestructionCallback();
        if (destructionCallback != null)
          destructionCallback.run();
      }
    }
  }
  
  private synchronized Map<String, ScopeObjectEntry> aquireInstances(S scope) {
    Map<String, ScopeObjectEntry> instances = scopes.get(scope);
    if (instances == null) {
      instances = new HashMap<String, ScopeObjectEntry>();
      scopes.put(scope, instances);
    }
    return instances;
  }
  
  private synchronized ScopeObjectEntry aquireEntry(S scope, String name) {
    Map<String, ScopeObjectEntry> instances = aquireInstances(scope);
    
    ScopeObjectEntry entry = instances.get(name);
    if (entry == null) {
      entry = new ScopeObjectEntry();
      instances.put(name, entry);
    }
    
    return entry;
  }
  
  @SuppressWarnings("rawtypes")
public Object get(String name, ObjectFactory factory) {
    ScopeObjectEntry entry = aquireEntry(getCurrentScope(), name);
    
    if (!entry.isObjectInitialized()) {
      entry.setObject(factory.getObject());
    }
    return entry.getObject();
  }

  public String getConversationId() {
	 return null;
  }

  public void registerDestructionCallback(String name, Runnable destructionCallback) {
    aquireEntry(getCurrentScope(), name).setDestructionCallback(destructionCallback);
  }

  public Object remove(String name) {
    throw new UnsupportedOperationException();
  }

	public Object resolveContextualObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}
  
  
  
}
