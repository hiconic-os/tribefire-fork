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

public class ColorFactoryBean implements FactoryBean {
	private int alpha = 255;
	private int red = 0;
	private int green = 0;
	private int blue = 0;
	
	public void setRed(int red) {
		this.red = red;
	}
	
	public void setGreen(int green) {
		this.green = green;
	}
	
	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	
	public void setRedFloat(float red) {
		this.red = (int)(red * 255 + 0.5);
	}
	
	public void setGreenFloat(float green) {
		this.green = (int)(green * 255 + 0.5);;
	}
	
	public void setBlueFloat(float blue) {
		this.blue = (int)(blue * 255 + 0.5);;
	}
	
	public void setAlphaFloat(float alpha) {
		this.alpha = (int)(alpha * 255 + 0.5);;
	}
	
	public void setValue(int value) {
		setValueWithAlpha(value | 0xff000000);
	}
	
	public void setValueWithAlpha(int value) {
		alpha = (value >> 24) & 0xFF;
		red = (value >> 16) & 0xFF;
		green = (value >> 8) & 0xFF;
		blue = (value >> 0) & 0xFF;
	}
	
	public Object getObject() throws Exception {
		return new Color(red, green, blue, alpha);
	}
	
	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return Color.class;
	}
	
	public boolean isSingleton() {
		return true;
	}
}
