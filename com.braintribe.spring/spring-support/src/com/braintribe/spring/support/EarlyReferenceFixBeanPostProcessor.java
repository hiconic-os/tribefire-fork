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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;


public class EarlyReferenceFixBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware {

	private BeanFactory beanFactory;
	
	private static Map<BeanFactory, Map<String, Object>> earlyReferences = new HashMap<BeanFactory, Map<String,Object>>();

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	public static Map<String, Object> getEarlyReferences(BeanFactory beanFactory) {
		Map<String, Object> refs = earlyReferences.get(beanFactory);
		if (refs == null) {
			refs = new HashMap<String, Object>();
			earlyReferences.put(beanFactory, refs);
		}
		return refs;
	}
	
	public static Object getEarlyReference(BeanFactory beanFactory, String beanName) {
		return getEarlyReferences(beanFactory).get(beanName);
	}
	
	protected Map<String, Object> getEarlyReferences() {
		return getEarlyReferences(beanFactory);
	}
	
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName)
			throws BeansException {
		getEarlyReferences().put(beanName, bean);
		return super.postProcessAfterInstantiation(bean, beanName);
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		getEarlyReferences().remove(beanName);
		return super.postProcessAfterInitialization(bean, beanName);
	}
}
