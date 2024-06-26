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

import java.beans.PropertyEditorSupport;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class ProviderEditor extends PropertyEditorSupport implements BeanFactoryAware {
	private BeanFactory beanFactory;

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@SuppressWarnings("unchecked")
	public String getAsText() {
		BeanFactoryProvider<Object> provider = (BeanFactoryProvider<Object>)getValue();
		return provider != null? provider.getBeanName(): "";
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.trim().length() == 0) {
			setValue(null);
		} else {
			if (beanFactory.containsBean(text)) {
				BeanFactoryProvider<Object> provider = new BeanFactoryProvider<Object>();
				provider.setBeanFactory(beanFactory);
				provider.setBeanName(text);
				setValue(provider);
			} else
				throw new IllegalArgumentException("no bean with id " + text
						+ " found");
		}
	}
}
