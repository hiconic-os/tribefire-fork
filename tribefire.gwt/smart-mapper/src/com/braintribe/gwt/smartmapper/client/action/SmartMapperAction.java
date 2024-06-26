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
package com.braintribe.gwt.smartmapper.client.action;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.smartmapper.client.PropertyAssignmentContext;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;

public abstract class SmartMapperAction extends Action{
	
	protected ModelMetaDataEditor modelMetaDataEditor;	
	protected PropertyAssignmentContext propertyAssignmentContext;
	
	public void setModelMetaDataEditor(ModelMetaDataEditor modelMetaDataEditor) {
		this.modelMetaDataEditor = modelMetaDataEditor;
	}
	
	public void setPropertyAssignmentContext(PropertyAssignmentContext propertyAssignmentContext) {
		this.propertyAssignmentContext = propertyAssignmentContext;
	}
	
	public abstract boolean isVisible(PropertyAssignmentContext pac);

}
