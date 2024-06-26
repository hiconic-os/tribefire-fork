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

import java.awt.Color;

import org.springframework.beans.factory.FactoryBean;

public class GradientColorFactoryBean implements FactoryBean {
	private Color startColor = Color.black;
	private Color endColor = Color.white;
	private float gradient = 0.5f;
	
	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}
	
	public void setEndColor(Color endColor) {
		this.endColor = endColor;
	}
	
	public void setGradient(float gradient) {
		this.gradient = gradient;
	}
	
	public Object getObject() throws Exception {
		float[] startValues = startColor.getRGBComponents(null); 
		float[] endValues = endColor.getRGBComponents(null);
		
		float[] gradientValues = new float[startValues.length];
		
		for (int i = 0; i < gradientValues.length; i++) {
			float s = startValues[i];
			float e = endValues[i];
			float g = s + (e - s) * gradient;
			gradientValues[i] = g;
		}
		
		return new Color(gradientValues[0], gradientValues[1], gradientValues[2], gradientValues[3]);
	}
	
	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return Color.class;
	}
	
	public boolean isSingleton() {
		return true;
	}
}
