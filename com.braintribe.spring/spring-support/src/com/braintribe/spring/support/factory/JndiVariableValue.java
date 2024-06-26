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
package com.braintribe.spring.support.factory;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.beans.factory.FactoryBean;

import com.braintribe.cfg.Required;

public class JndiVariableValue<T> implements FactoryBean<T> {

	protected Class<T> clazz = null;
	protected String jndiPath = null;
	
	@SuppressWarnings("unchecked")
	public JndiVariableValue() {
		this.clazz = (Class<T>) String.class;
	}

	public JndiVariableValue(Class<T> cls) {
		this.clazz = cls;
	}
	
	@Override
	public T getObject() throws Exception {
		Context ctx = new InitialContext();
		Context envCtx = (Context) ctx.lookup("java:comp/env");
		@SuppressWarnings("unchecked")
		T value = (T) envCtx.lookup(this.jndiPath);
		return value;
	}

	@Override
	public Class<?> getObjectType() {
		return this.clazz;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Required
	public void setJndiPath(String jndiPath) {
		this.jndiPath = jndiPath;
	}

}
