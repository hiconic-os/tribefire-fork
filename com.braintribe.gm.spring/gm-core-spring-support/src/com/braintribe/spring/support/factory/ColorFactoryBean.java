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

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;

import com.braintribe.model.style.Color;

public class ColorFactoryBean implements FactoryBean<Color>{

	private Color color;
	
	@Required
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public Color getObject() throws Exception {
		return color;
	}
	
	@Override
	public Class<?> getObjectType() {
		return Color.class;
	}
	
	@Override
	public boolean isSingleton() {
		return true;
	}
}
