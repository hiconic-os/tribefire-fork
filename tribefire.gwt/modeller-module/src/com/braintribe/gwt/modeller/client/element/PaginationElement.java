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
package com.braintribe.gwt.modeller.client.element;

import org.vectomatic.dom.svg.OMSVGCircleElement;
import org.vectomatic.dom.svg.OMSVGGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.braintribe.gwt.modeller.client.GmModellerRenderer;
import com.braintribe.model.processing.modellergraph.ModelGraphConfigurationsNew;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class PaginationElement extends OMSVGGElement implements ClickHandler{
	
	private boolean next = false;
	private OMSVGCircleElement circle;
	private GmModellerRenderer renderer;
	private ModelGraphConfigurationsNew configuration;
	
	public PaginationElement(boolean next) {
		this.next = next;
		circle = OMSVGParser.currentDocument().createSVGCircleElement();
		circle.setAttribute("style", "fill:grey;stroke-width:2;stroke:silver;cursor:pointer");
		circle.setAttribute("r", "10");
		appendChild(circle);
		addClickHandler(this);
	}
	
	public void setConfiguration(ModelGraphConfigurationsNew configuration) {
		this.configuration = configuration;
		
		if(next) {			
			circle.setAttribute("cx", (configuration.viewPortDimension.x - 20) + "");
		}else {
			circle.setAttribute("cx", "20");
		}
		
		circle.setAttribute("cy", (configuration.viewPortDimension.y / 2) + "");
	}
	
	public void setRenderer(GmModellerRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if(next) {
			configuration.currentPage += configuration.modellerView.getSettings().getMaxElements();
			renderer.rerender();
		}else {
			configuration.currentPage -= configuration.modellerView.getSettings().getMaxElements();
			if(configuration.currentPage < 0)
				configuration.currentPage = 0;
			renderer.rerender();
		}
	}

}
