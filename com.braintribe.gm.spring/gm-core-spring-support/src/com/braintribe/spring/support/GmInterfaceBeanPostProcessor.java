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

import java.lang.reflect.Constructor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;


public class GmInterfaceBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
	private final GenericModelTypeReflection TYPE_REFLECTION;

	public GmInterfaceBeanPostProcessor() {
		TYPE_REFLECTION = GMF.getTypeReflection();
	}
	
	/**
	 * @deprecated interfaces only is anyway the way we live therefore this method does nothing
	 */
	@Deprecated 
	public void setInterfacesOnly(@SuppressWarnings("unused") boolean interfacesOnly) {
		// noop
	}

	@Override
	@SuppressWarnings("unchecked")
	public Constructor<?>[] determineCandidateConstructors(@SuppressWarnings("rawtypes") Class beanClass,
			String beanName) throws BeansException {
		
		//creation of dummy instance just in order to known what is the plain entity class.
		//it would be desirable if the GM type reflection could tell us the type directly without creating an instance
		if (beanClass.isInterface() && GenericEntity.class.isAssignableFrom(beanClass)) {
			return TYPE_REFLECTION.getEntityType(beanClass).enhancedClass().getConstructors();
		}
		
		return super.determineCandidateConstructors(beanClass, beanName);
	}
}
