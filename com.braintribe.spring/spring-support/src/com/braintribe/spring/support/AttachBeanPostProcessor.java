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

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;


public class AttachBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
	private BeanFactory beanFactory;
	private Map<String, List<String>> attachments = null; 
	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	public boolean postProcessAfterInstantiation(Object bean, String beanName)
			throws BeansException {
		return true;
	}


	public PropertyValues postProcessPropertyValues(PropertyValues pvs,
			PropertyDescriptor[] pds, Object bean, String beanName)
			throws BeansException {
		return pvs;
	}
	
	@SuppressWarnings("unchecked")
	public Object postProcessBeforeInstantiation(Class beanClass,
			String beanName) throws BeansException {
		return null;
	}

	@SuppressWarnings("unchecked")
	private Map<String, List<String>> getAttachments() {
		if (attachments == null) {
			attachments = new HashMap<String, List<String>>();
			
			DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)beanFactory;
			Map<String, Attach> attachInstances = defaultListableBeanFactory.getBeansOfType(Attach.class, false, false);
			
			for (Attach attach: attachInstances.values()) {
				String masterBeanName = attach.getMasterBeanName();
				
				List<String> beanAttachments = attachments.get(masterBeanName);
				if (beanAttachments == null) {
					beanAttachments = new ArrayList<String>();
					attachments.put(masterBeanName, beanAttachments);
				}
				
				beanAttachments.add(attach.getBeanName());
			}
		}

		return attachments;
	}
	
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		
		Map<String, List<String>> attachments = getAttachments();
		
		List<String> attachtedBeanNames = attachments.get(beanName);
		
		if (attachtedBeanNames != null) {
			for (String attachedBeanName: attachtedBeanNames) {
				beanFactory.getBean(attachedBeanName);
			}
		}
		
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
	

}
