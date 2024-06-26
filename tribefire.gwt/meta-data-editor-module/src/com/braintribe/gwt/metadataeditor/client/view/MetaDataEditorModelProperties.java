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
package com.braintribe.gwt.metadataeditor.client.view;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.meta.selector.MetaDataSelector;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface MetaDataEditorModelProperties extends PropertyAccess<MetaDataEditorModel> {

	@Path("name")
	ModelKeyProvider<MetaDataEditorModel> key();

	ValueProvider<MetaDataEditorModel, MetaDataEditorModel> model();

	ValueProvider<MetaDataEditorModel, String> name();

	ValueProvider<MetaDataEditorModel, Object> value();

	ValueProvider<MetaDataEditorModel, MetaData> metaData();
	
	ValueProvider<MetaDataEditorModel, EntityType<?>> entityTypeValue();	

	ValueProvider<MetaDataEditorModel, MetaDataSelector> selector();

	ValueProvider<MetaDataEditorModel, Double> conflictPriority();

	ValueProvider<MetaDataEditorModel, Object> propertyValue();	

	ValueProvider<MetaDataEditorModel, MetaDataEditorModel> declaredModel();

	ValueProvider<MetaDataEditorModel, MetaDataEditorModel> declaredOwner();	
}
