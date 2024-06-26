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
package com.braintribe.gwt.gme.propertypanel.client.field;

import java.util.function.Supplier;

import com.braintribe.gwt.gme.propertypanel.client.PropertyPanel;
import com.braintribe.model.generic.path.ModelPathElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Interface for special fields that are actually panels which should be rendered together with the properties.
 * It is displayed in a line line bellow the actual property, or inlined.
 * @author michel.docouto
 *
 */
public interface ExtendedInlineField {
	
	boolean isAvailable(ModelPathElement modelPath);
	boolean isAvailableInline(ModelPathElement modelPath);
	Supplier<Widget> getWidgetSupplier();
	void prepareInlineElement(Element element);
	void configurePropertyPanel(PropertyPanel propertyPanel);

}
