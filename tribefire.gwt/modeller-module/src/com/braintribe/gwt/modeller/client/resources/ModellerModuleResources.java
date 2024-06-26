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
package com.braintribe.gwt.modeller.client.resources;

import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.ui.SVGResource.Validated;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ModellerModuleResources extends ClientBundle{
	
	public static final ModellerModuleResources INSTANCE = (ModellerModuleResources) GWT.create(ModellerModuleResources.class);
	
	public ImageResource add();
	public ImageResource addToCircle();
	public ImageResource addedToCircle();
	public ImageResource goTo();
	public ImageResource delete();
	public ImageResource remove();
	public ImageResource visibility();
	public ImageResource visible();
	public ImageResource hidden();
	public ImageResource pin();
	
	public ImageResource blank();
	
	public ImageResource next();
	public ImageResource previous();
	
	public ImageResource expanded();
	public ImageResource collapsed();
			
	@Validated(validated = false)
	public SVGResource arrow();
	@Validated(validated = false)
	public SVGResource circle();
	@Validated(validated = false)
	public SVGResource doubleCircle();
	@Validated(validated = false)
	public SVGResource doubleCircleArrow();
	@Validated(validated = false)
	public SVGResource keyCircle();
	@Validated(validated = false)
	public SVGResource valueCircle();
	
	public ImageResource modellerBig();
	public ImageResource modeller();
}
