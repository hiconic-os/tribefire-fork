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
import org.vectomatic.dom.svg.OMSVGLineElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.braintribe.model.processing.modellergraph.common.Complex;

public class NodeConnector {
	
	OMSVGGElement g;
	OMSVGCircleElement circle;
	OMSVGLineElement line;
	
	public void render(Complex from, Complex to) {
		getLine().getX1().getBaseVal().setValue((float) from.x);
		getLine().getX2().getBaseVal().setValue((float) to.x);
		getLine().getY1().getBaseVal().setValue((float) from.y);
		getLine().getY2().getBaseVal().setValue((float) to.y);
		
		getCircle().getCx().getBaseVal().setValue((float) to.x);
		getCircle().getCy().getBaseVal().setValue((float) to.y);
	}
	
	public OMSVGGElement getG() {
		if(g == null) {
			g = OMSVGParser.currentDocument().createSVGGElement();
			g.appendChild(getLine());
			g.appendChild(getCircle());
			g.setAttribute("id", "nodeConnector");
		}
		return g;
	}
	
	private OMSVGCircleElement getCircle() {
		if(circle == null) {
			circle = OMSVGParser.currentDocument().createSVGCircleElement();
			circle.setAttribute("style", "fill:white;stroke:grey;stroke-width:3");
			circle.setAttribute("r", "5");
		}
		return circle;
	}

	private OMSVGLineElement getLine() {
		if(line == null) {
			line = OMSVGParser.currentDocument().createSVGLineElement();
			line.setAttribute("style", "fill:none;stroke:grey;stroke-width:3;stroke-dasharray:5,5");
		}
		return line;
	}
	
}
