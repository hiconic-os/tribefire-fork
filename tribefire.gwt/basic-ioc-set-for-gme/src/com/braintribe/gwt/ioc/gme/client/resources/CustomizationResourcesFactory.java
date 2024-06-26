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
package com.braintribe.gwt.ioc.gme.client.resources;

import com.braintribe.gwt.browserfeatures.client.GWTMetaPropertiesUtil;
import com.braintribe.gwt.browserfeatures.client.GWTMetaPropertiesUtil.IconSet;
import com.google.gwt.core.client.GWT;

public class CustomizationResourcesFactory {
	
	private static final CustomizationResources customizationResources = (CustomizationResources) (GWTMetaPropertiesUtil.getIconSet().equals(IconSet.coloured) ?
			GWT.create(ColouredCustomizationResources.class) : GWT.create(CustomizationResources.class));

	public CustomizationResources getResources() {
		return customizationResources;
	}

}
