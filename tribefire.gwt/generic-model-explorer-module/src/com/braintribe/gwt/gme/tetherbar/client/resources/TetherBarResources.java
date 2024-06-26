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
package com.braintribe.gwt.gme.tetherbar.client.resources;

import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.ui.SVGResource.Validated;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface TetherBarResources extends ClientBundle {
	
	public static final TetherBarResources INSTANCE = GWT.create(TetherBarResources.class);
	
	@Source ("tetherbar.gss")
	public TetherBarCss css();
	
	public ImageResource moveUp();

	@Source("arrowTether.svg") @Validated(validated = false)
	SVGResource  arrowTetherSvg();	

}
