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

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;


public class SpringSwitch<T> implements FactoryBean<T>, BeanFactoryAware {
	
	private Class<T> type;
	private BeanFactory beanFactory;
	private List<SpringSwitchRule> rules;
	private String selectedBeanId;
	private boolean singleton = false;

	public SpringSwitch() {
	}
	
	public SpringSwitch(Class<T> type) {
		this.type = type;
	}
	
	public String getSelectedBeanId() {
		if (selectedBeanId == null) {
			for (SpringSwitchRule rule: rules) {
				try {
					if (Boolean.TRUE.equals(rule.getSelector().get())) {
						selectedBeanId = rule.getBeanId();
						break;
					}
				} catch (RuntimeException e) {
					throw new RuntimeException("error while checking rules for spring switch", e);
				}
			}
		}

		return selectedBeanId;
	}
	
	@Configurable
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}
	
	@Configurable @Required
	public void setRules(List<SpringSwitchRule> rules) {
		this.rules = rules;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		String selectedBeanId = getSelectedBeanId();
		if (selectedBeanId == null)
			throw new RuntimeException("no beanId selected");
		
		return (T)beanFactory.getBean(selectedBeanId);
	}

	@Override
	public Class<?> getObjectType() {
		return type;
	}

	@Override
	public boolean isSingleton() {
		return singleton;
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
}
