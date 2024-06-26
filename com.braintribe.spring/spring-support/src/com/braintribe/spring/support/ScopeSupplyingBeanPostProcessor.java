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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class ScopeSupplyingBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
	
	private DefaultListableBeanFactory beanFactory;
	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (DefaultListableBeanFactory)beanFactory;
	}
	
	@SuppressWarnings("unchecked")
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof ScopeAware) {
			ScopeAware<Object> scopeAware = (ScopeAware<Object>)bean;
			String scopeName = beanFactory.getBeanDefinition(beanName).getScope();
			Scope scope = beanFactory.getRegisteredScope(scopeName);
			if (scope instanceof BeanScope) {
				scopeAware.supplyScope(((BeanScope<Object>)scope).getScopeBean());
			}
			else {
				scopeAware.supplyScope(scopeName);
			}
			
		}
		return bean;
	}
	
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
