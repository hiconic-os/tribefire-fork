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
package com.braintribe.gwt.processdesigner.client.display;

import java.util.List;

import org.vectomatic.dom.svg.OMSVGGElement;
import org.vectomatic.dom.svg.OMSVGRectElement;


public class ProcessDesignerStatusBar extends OMSVGGElement{
	
	private int width = 75;
	private int height = 75;
	private OMSVGRectElement rect = new OMSVGRectElement(0, 0, 0, height, 0, 0);
	private List<ProcessDesignerStatusBarElement> statusElements;
	
	public ProcessDesignerStatusBar() {
		setAttribute("id", "statusBar");
	}
	
	public void setStatusElements(List<ProcessDesignerStatusBarElement> statusElements) {
		this.statusElements = statusElements;
	}
	
	public void init(){
		rect.setAttribute("x", getX() + "");
		rect.setAttribute("y", getY() + "");
		rect.setAttribute("width", (statusElements.size() *  width) + "");
		rect.setAttribute("style", "fill:none;stroke:silver;stroke-width:1;stroke-dasharray:2,3;stroke-linecap:round");
		int i = 0;
		for(ProcessDesignerStatusBarElement element : statusElements){
			element.setAttribute("x", (getX() + (i * width)) + "");
			element.setAttribute("y", getY() + "");
			element.init();
			i++;
			appendChild(element);
		}
	}
	
	public double getX(){
		return Double.parseDouble(getAttribute("x"));
	}
	
	public double getY(){
		return Double.parseDouble(getAttribute("y"));
	}
	
	public void setX(double x){
		setAttribute("x", x+"");
	}
	
	public void setY(double y){
		setAttribute("y", y+"");
	}
}
