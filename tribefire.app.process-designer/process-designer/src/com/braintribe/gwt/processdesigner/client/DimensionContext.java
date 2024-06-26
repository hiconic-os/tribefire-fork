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

import com.braintribe.gwt.processdesigner.client.element.NodeDivElement;

public class DimensionContext {
	
	public double x;
	public double y;
	public double height;
	public double width;
	public NodeDivElement nodeSvgElement;
	
	@Override
	public int hashCode() {
		return 5;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DimensionContext){
			DimensionContext dimensionContext = (DimensionContext) obj;
			return ((x+width) >= (dimensionContext.x+dimensionContext.width) && (x+width) <= (dimensionContext.x+dimensionContext.width))
					|| ((y+height) >= (dimensionContext.y+dimensionContext.height) && (y+height) <= (dimensionContext.y+dimensionContext.height));
		}
		return super.equals(obj);
	}

}
