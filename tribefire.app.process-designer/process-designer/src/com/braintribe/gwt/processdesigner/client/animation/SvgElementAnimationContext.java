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
package com.braintribe.gwt.processdesigner.client.animation;

import org.vectomatic.dom.svg.OMSVGElement;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;

public class SvgElementAnimationContext {
	
	public Object element;
	public String attribute;
	public double startValue;
	public double endValue;
	public double currentValue;
	public long startTime;
	public long durationTime;
	public long freqency;
	public Timer timer;
	
	public void adapt(double normalizedTime){
		double delta = endValue - startValue;
				
		currentValue = startValue + (delta * normalizedTime);
//		System.err.println("adapting " + currentValue);
		if(element instanceof FlowPanel)
			((FlowPanel)element).getElement().setAttribute(attribute, currentValue + "");
		else if(element instanceof OMSVGElement)
			((OMSVGElement)element).setAttribute(attribute, currentValue + "");
	}

}
