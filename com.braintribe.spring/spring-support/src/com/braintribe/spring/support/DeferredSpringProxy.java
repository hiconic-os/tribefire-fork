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
package com.braintribe.spring.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;

import com.braintribe.cfg.Configurable;

public class DeferredSpringProxy<T> implements FactoryBean<T>, BeanFactoryAware, InvocationHandler {

	private boolean singleton = true;
	private String beanId;
	private BeanFactory beanFactory;
	private Class<T> interfaceClass;
	private T proxy;
	private T delegate;
	
	@Required
	@Configurable
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	@Configurable
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}
	
	@Required
	@Configurable
	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized T getObject() throws Exception {
		if (proxy == null) {
			proxy = (T)Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{interfaceClass}, this);
		}
		return proxy;
	}
	
	@SuppressWarnings("unchecked")
	protected T getDelegate() {
		if (delegate == null) {
			delegate = (T) beanFactory.getBean(beanId);
		}
		
		return delegate;
	}

	@Override
	public Class<?> getObjectType() {
		return interfaceClass;
	}

	@Override
	public boolean isSingleton() {
		return singleton;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(getDelegate(), args);
	}
}
