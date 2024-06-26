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
package com.braintribe.gwt.gmview.client.js.interop;

import java.util.List;

import com.braintribe.gwt.gmview.client.GmListView;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.reflection.GenericModelType;

import jsinterop.annotations.JsMethod;

/**
 * Interface used for exporting {@link GmListView} via JsInterop.
 */
@SuppressWarnings("unusable-by-js")
public interface GmListViewInterfaceInterop extends GmContentViewInterfaceInterop, GmListView {
	
	@Override
	@JsMethod
	public void configureTypeForCheck(GenericModelType typeForCheck);
	
	@Override
	@JsMethod
	public void addContent(ModelPath modelPath);
    
	@Override
	@JsMethod
    public List<ModelPath> getAddedModelPaths();

}
