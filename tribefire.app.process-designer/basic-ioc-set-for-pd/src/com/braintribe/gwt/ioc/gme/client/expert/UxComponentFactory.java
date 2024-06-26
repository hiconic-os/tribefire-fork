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
package com.braintribe.gwt.ioc.gme.client.expert;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.ioc.gme.client.ProcessDesignerIoc;
import com.braintribe.gwt.ioc.gme.client.js.InteropConstants;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Factory which provides UiComponents for being used as external JS components.
 * 
 * @author michel.docouto
 */
@JsType(namespace = InteropConstants.MODULE_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public class UxComponentFactory {
	
	private Map<String, GmContentView> componentMap = new HashMap<>();
	private Map<String, Supplier<? extends GmContentView>> supplierMap = new HashMap<>();
	
	@JsConstructor
	public UxComponentFactory() {
		addComponentSupplier("JsProcessDesignerPanel", ProcessDesignerIoc.processDesigner);
		addComponentSupplier("JsProcessDesignerStandAlonePanel", ProcessDesignerIoc.standAloneProcessDesigner);
	}
	
	@JsMethod
	public void addComponentSupplier(String name, Supplier<? extends GmContentView> supplier) {
		supplierMap.put(name, supplier);
	}
	
	@JsMethod
	public void removeComponentSupplier(String name) {
		supplierMap.remove(name);
	}
	
	@JsMethod
	public void addComponent(String name, GmContentView gmContentView) {
		componentMap.put(name, gmContentView);
	}
	
	@JsMethod
	public void removeComponent(String name) {
		componentMap.remove(name);
	}

	@JsMethod
	public GmContentView provideComponent(String name) {
		Supplier<? extends GmContentView> supplier = supplierMap.get(name);
		if (supplier != null)
			return supplier.get();
		
		return componentMap.get(name);
	}

}
