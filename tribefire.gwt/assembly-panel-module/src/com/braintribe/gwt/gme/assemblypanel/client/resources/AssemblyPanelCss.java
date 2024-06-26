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
package com.braintribe.gwt.gme.assemblypanel.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface AssemblyPanelCss extends CssResource {
	
	//External definitions (in the public assemblypanel.css can be added here if they are important in the code).
	public static String EXTERNAL_PROPERTY_VALUE = "assemblyPanelPropertyValue";
	public static String EXTERNAL_PROPERTY_ICON = "assemblyPanelPropertyIcon";
	public static String EXTERNAL_PROPERTY_ICON_GROUP = "assemblyPanelPropertyIconGroup";
	public static String EXTERNAL_PROPERTY_TEXT = "assemblyPanelPropertyText";
	
	String checkedReadOnlyValue();
	String checkedValue();
	String checkNullReadOnlyValue();
	String checkNullValue();
	String collectionElementStyle();
	String editableBox();
	String emphasisStyle();
	String inheritFont();
	String linkStyle();
	String mapKeyAndValueSeparator();
	String moreItemsInSetStyle();
	String pointerCursor();
	String propertyNameStyle();
	String propertyValueWithPadding();
	String tableFixedLayout();
	String tableForTreeWithFixedLayout();
	String textOverflowNoWrap();
	String uncheckedReadOnlyValue();
	String uncheckedValue();

}
