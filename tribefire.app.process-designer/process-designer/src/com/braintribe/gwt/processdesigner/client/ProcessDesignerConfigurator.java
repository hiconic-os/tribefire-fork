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
package com.braintribe.gwt.processdesigner.client;

import com.braintribe.gwt.processdesigner.client.vector.Point;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.tracking.ManipulationListener;

public class ProcessDesignerConfigurator implements ManipulationListener{
	
	private ProcessDesignerConfiguration pdc;
	//private ProcessDesignerRenderer processDesignerRenderer;
	
	/*public void setProcessDesignerRenderer(ProcessDesignerRenderer processDesignerRenderer) {
		this.processDesignerRenderer = processDesignerRenderer;
	}*/
	
	public void setProcessDesignerConfiguration(ProcessDesignerConfiguration processDesignerConfiguration) {
		this.pdc = processDesignerConfiguration;
	}
	
	@Override
	public void noticeManipulation(Manipulation manipulation) {
		configure();
	}
	
	public void configure(){
		Point defaultStartingPoint = Point.T.create();
		defaultStartingPoint.setX(0 + pdc.getDefaultOffset());
		defaultStartingPoint.setY(0 + pdc.getDefaultOffset());
//		pdc.setDefaultStartingPoint(defaultStartingPoint);
		
//		processDesignerRenderer.render();
	}

}
